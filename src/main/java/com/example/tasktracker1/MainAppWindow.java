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
    private LinkedList<RadioButton> radioButtons = new LinkedList<>();

    private ToggleGroup buttonGroup = new ToggleGroup();
    private LinkedList<RadioButton> radioButtonList = new LinkedList<>();
    private ArrayList<String> stringButtonList = new ArrayList<>();
    private int selectedButtonIndex;
    private DbOperator dbOperator = new DbOperator();


    @Override
    public void start(Stage stage) throws IOException, SQLException, ClassNotFoundException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainAppWindow.class.getResource("sample.fxml"));
        //ОТДЕЛЬНЫЙ МЕТОД
        pane = new AnchorPane();
        pane.getChildren().add(fxmlLoader.load());
        stringButtonList = dbOperator.valueGetter();

        for (int i = 0; i < stringButtonList.size(); i++){
            int actualPos = posOperator.getSelPos();
            String task = stringButtonList.get(i);
            s(task,actualPos);
            posOperator.selPosChanger();

        }

        TaskTransporter taskTransporter = new TaskTransporter();
        TaskHelper taskGetter = new TaskHelper();
        taskTransporter.setTaskListener(taskGetter);
        Scene scene = new Scene(pane, 600, 300);
        stage.setTitle("TaskTracker");
        stage.setScene(scene);
        stage.show();
        try {
            dbOperator.valueGetter();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        buttonGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle oldValue, Toggle newValue) {
                RadioButton selectedButton =  (RadioButton) newValue;
                String selectedButtonText  =  selectedButton.getText();
                int selectedButtonPosition = (int) selectedButton.getLayoutY();
                posOperator.setSelPos(selectedButtonPosition);
                selectedButton.setDisable(true);
                selectedButton.setVisible(false);
                try {
                    dbOperator.taskDeleter(selectedButtonText);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }


                for (int i = 0; i < radioButtonList.size(); i++){
                    String equalButtonText = radioButtonList.get(i).getText();
                    if (selectedButtonText.equals(equalButtonText)){
                        selectedButtonIndex = i;
                        radioButtonList.remove(i);
                        posOperator.setSelPos((int) selectedButton.getLayoutY());
                        break;
                    }
                }

                for (int i = selectedButtonIndex; i < radioButtonList.size(); i++){
                    RadioButton bt = radioButtonList.get(i);
                    int t = (int) bt.getLayoutY();
                    bt.setLayoutY(posOperator.getSelPos());
                    posOperator.setSelPos(t);
                }
                if (radioButtonList.isEmpty()) {
                    posOperator.changerPos(15);
                } else {
                    double s = radioButtons.getLast().getLayoutY();
                    System.out.println(s);
                    int res = (int) s;
                    posOperator.changerPos(res);
                }
            }
        });
    }

    private void s(String text, int pos) {
        button = TaskButton.buttonDesigner(text, pos);
        pane.getChildren().add(button);
        radioButtonList.add(button);
        button.setToggleGroup(buttonGroup);
        radioButtons.add(button);
    }

    public class TaskHelper implements TaskListener{
        private String task;
        @Override
        public void getTaskText(String taskText) {
            task = taskText;
            int actualPos = posOperator.getSelPos();
            s(task,actualPos);
            posOperator.selPosChanger();
        }
    }
    public static class posOperator {
        private static int selPos = 42;
        private static int pos = 15;

        public static void setSelPos(int pos){
            selPos = pos;
        }
        public static int getSelPos(){
            return selPos;
        }
        public static void selPosChanger(){
            selPos += 28;
        }

        public static void changerPos(int s){
            pos = s;
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
