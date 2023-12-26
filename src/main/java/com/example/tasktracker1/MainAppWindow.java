package com.example.tasktracker1;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;


public class MainAppWindow extends Application  {
    private AnchorPane pane;
    private  RadioButton button;
    private ToggleGroup buttonGroup = new ToggleGroup();
    private LinkedList<RadioButton> radioButtonList = new LinkedList<>();
    private ArrayList<String> stringButtonList = new ArrayList<>();
    private int selectedButtonIndex;
    private DbOperator dbOperator = new DbOperator();


    @Override
    public void start(Stage stage) throws IOException, SQLException, ClassNotFoundException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainAppWindow.class.getResource("sample.fxml"));
        pane = new AnchorPane();
        pane.getChildren().add(fxmlLoader.load());
        stringButtonList = dbOperator.valueGetter();

        buttonSetup();

        TaskTransporter taskTransporter = new TaskTransporter();
        TaskHelper taskGetter = new TaskHelper();
        taskTransporter.setTaskListener(taskGetter);
        Scene scene = new Scene(pane, 600, 300);
        stage.setTitle("TaskTracker");
        stage.setScene(scene);
        stage.show();

        buttonGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle oldValue, Toggle newValue) {
                RadioButton selectedButton =  (RadioButton) newValue;
                String selectedButtonText  =  selectedButton.getText();
                selectedButton.setDisable(true);
                selectedButton.setVisible(false);
                try {
                    dbOperator.taskDeleter(selectedButtonText);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                pickedButtonDeleter(selectedButtonText,selectedButton);
                buttonsPosChanger();
            }
        });
    }

    private void rButtonConstrukter(String text, int pos) {
        button = PositionOperator.buttonDesigner(text, pos);
        pane.getChildren().add(button);
        radioButtonList.add(button);
        button.setToggleGroup(buttonGroup);
    }

    private void pickedButtonDeleter(String selectedButtonText, RadioButton selectedButton){
        for (int i = 0; i < radioButtonList.size(); i++){
            String equalButtonText = radioButtonList.get(i).getText();
            if (selectedButtonText.equals(equalButtonText)){
                selectedButtonIndex = i;
                radioButtonList.remove(i);
                PositionOperator.setActualPosition((int) selectedButton.getLayoutY());
                break;
            }
        }
    }
    private void buttonSetup(){
        for (int i = 0; i < stringButtonList.size(); i++){
            int actualPosition = PositionOperator.getActualPosition();
            String task = stringButtonList.get(i);
            rButtonConstrukter(task,actualPosition);
            PositionOperator.actualPositionChanger();

        }
    }

    private void buttonsPosChanger(){
        for (int i = selectedButtonIndex; i < radioButtonList.size(); i++){
            RadioButton button = radioButtonList.get(i);
            int nextButtonPosition = (int) button.getLayoutY();
            button.setLayoutY(PositionOperator.getActualPosition());
            PositionOperator.setActualPosition(nextButtonPosition);
        }
    }

    public class TaskHelper implements TaskListener{
        private String task;
        @Override
        public void getTaskText(String taskText) {
            task = taskText;
            int actualPos = PositionOperator.getActualPosition();
            rButtonConstrukter(task,actualPos);
            PositionOperator.actualPositionChanger();
        }
    }
}
