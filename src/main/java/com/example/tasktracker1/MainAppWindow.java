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
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class MainAppWindow extends Application  {

    private AnchorPane pane;
    private final ToggleGroup buttonGroup = new ToggleGroup();
    private final LinkedList<RadioButton> radioButtonList = new LinkedList<>();
    private ArrayList<String> stringButtonList = new ArrayList<>();
    private int selectedButtonIndex;
    private final DbOperator dbOperator = new DbOperator();
    private final PositionOperator positionOperator = new PositionOperator();


    @Override
    public void start(Stage stage) throws IOException, SQLException, ClassNotFoundException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainAppWindow.class.getResource("sample.fxml"));
        pane = new AnchorPane();
        pane.getChildren().add(fxmlLoader.load());
        stringButtonList = dbOperator.loadValues();

        setupButtons();

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

                try {
                    dbOperator.taskDeleter(selectedButtonText);
                } catch (ClassNotFoundException  | SQLException e){
                    throw new RuntimeException(e);
                }

                deletePickedButton(selectedButtonText,selectedButton);
                shiftButtons();
            }
        });
    }

    private void constructButton(String text, int pos) {
//        Line line = new Line();
//        line.setStartX(150.0f);
//        line.setStartY(140.0f);
//        line.setEndX(450.0f);    <---- ЛИНИИИ!!!
//        line.setEndY(140.0f);
//        pane.getChildren().add(line);
        //ПОФИКСИТЬ БАГ БЛЯТЬ ОЧЕРЕДНОЙ С ПРОБЕЛАМИ
        RadioButton button = positionOperator.designButton(text, pos);
        pane.getChildren().add(button);
        radioButtonList.add(button);
        button.setToggleGroup(buttonGroup);
        button.isArmed();
    }

    private void deletePickedButton(String selectedButtonText, RadioButton selectedButton) {
        for (int i = 0; i < radioButtonList.size(); i++){
            String equalButtonText = radioButtonList.get(i).getText();
            if (selectedButtonText.equals(equalButtonText)){
                pane.getChildren().remove(radioButtonList.get(i));
                selectedButtonIndex = i;
                radioButtonList.remove(i);
                positionOperator.setActualPosition((int) selectedButton.getLayoutY());
                break;
            }
        }
    }
    private void setupButtons(){
        for (String s : stringButtonList) {
            int actualPosition = positionOperator.getActualPosition();
            constructButton(s,actualPosition);
            positionOperator.actualPositionChanger();
        }
    }

    private void shiftButtons() {
        //int c  = 0;
        for (int i = selectedButtonIndex; i < radioButtonList.size(); i++) {
            RadioButton button = radioButtonList.get(i);
            int nextButtonPosition = (int) button.getLayoutY();
            ChangePositionAnimation anim = new ChangePositionAnimation(button);
            System.out.println(button.getLayoutY());
            anim.playAnim();
            BackwardAnim an = new BackwardAnim(button,positionOperator.getActualPosition());
            TimerTask task2 = new TimerTask() {
                public void run() {
                    button.setLayoutY(positionOperator.getActualPosition());
                    positionOperator.setActualPosition(nextButtonPosition);

                }
            };

            TimerTask task1 = new TimerTask() {
                public void run() {
                    an.playAnim();
                    long delay1 = 1L;
                    Timer timer2 = new Timer();
                    timer2.schedule(task2,delay1); ///ВАЖНО - если вылезет башг с кнопкой - исправить

//                    button.setLayoutY(positionOperator.getActualPosition());
//                    positionOperator.setActualPosition(nextButtonPosition);

                }
            };
            //ПОЧИСТИТЬ КОД!!!!!

            Timer timer1 = new Timer();

            long delay = 200L;
            timer1.schedule(task1, delay);


            System.out.println(button.getLayoutY());
            //c += 28;


            //button.setLayoutY(positionOperator.getActualPosition());
            //positionOperator.setActualPosition(nextButtonPosition);
            System.out.println(button.getLayoutY());
        }
    }

    public class TaskHelper implements TaskListener {

        @Override
        public void getTaskText(String taskText) {
            String task = taskText;
            int actualPos = positionOperator.getActualPosition();
            constructButton(task,actualPos);
            positionOperator.actualPositionChanger();
        }
    }

}
