package com.example.tasktracker1;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
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
    private final String selectedButtonActive = "-fx-background-color: #0000CD";
    private final String selectedButtonInactive = "-fx-background-color: #4682B4";
    private final ToggleGroup rButtonGroup = new ToggleGroup();
    private final ToggleGroup lButtonGroup = new ToggleGroup();
    private final LinkedList<ToggleButton> listButtons = new LinkedList<>();
    private final LinkedList<RadioButton> radioButtonList = new LinkedList<>();
    private ArrayList<String> stringRButtonList = new ArrayList<>();

    private ToggleButton selectedLButton = new ToggleButton();

    private ArrayList<String> stringLButtonList = new ArrayList<>();
    private int selectedButtonIndex;
    private final DbOperator dbOperator = new DbOperator();
    private final TaskButtonOperator rButtonPositionOperator = new TaskButtonOperator();
    private final ListButtonOperator listButtonPositionOperator = new ListButtonOperator();
    private ToggleButton lastSelectedButton = new ToggleButton();

    @Override
    public void start(Stage stage) throws IOException, SQLException, ClassNotFoundException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainAppWindow.class.getResource("sample.fxml"));
        pane = new AnchorPane();
        pane.getChildren().add(fxmlLoader.load());

        setupLButtons();

        TaskAndListNameTransporter taskTransporter = new TaskAndListNameTransporter();
        TaskHelper taskGetter = new TaskHelper();
        taskTransporter.setTaskListener(taskGetter);

        TaskAndListNameTransporter listNameTransporter = new TaskAndListNameTransporter();
        ListNameHelper listNameGetter = new ListNameHelper();
        listNameTransporter.setListNameListener(listNameGetter);

        Scene scene = new Scene(pane, 750, 650);
        stage.setTitle("TaskTracker");
        stage.setScene(scene);
        stage.show();

        rButtonGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle oldValue, Toggle newValue) {
                RadioButton selectedButton =  (RadioButton) newValue;
                String selectedButtonText  =  selectedButton.getText();

                deletePickedButton(selectedButtonText,selectedButton);
                shiftButtons();

                try {
                    dbOperator.taskDeleter(selectedButtonText,selectedLButton.getText());
                } catch (ClassNotFoundException  | SQLException e){
                    throw new RuntimeException(e);
                }
            }
        });
        lButtonGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle oldValue, Toggle newValue) {
                selectedLButton = (ToggleButton) newValue;
                //System.out.println(lastSelectedButton.getText() + " G5");
                // stringRButtonList = dbOperator.loadValues();
                lastSelectedButton.setDisable(false);
                lastSelectedButton.setStyle(selectedButtonInactive);
                selectedLButton.setStyle(selectedButtonActive);
                selectedLButton.setDisable(true);
                lastSelectedButton = selectedLButton;

                try {
                    stringRButtonList = dbOperator.loadListValues(selectedLButton.getText());
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                deleteRButtons();
                setupRButtons();
            }
        });
    }

    private void constructRButton(String text, int pos) {
//        Line line = new Line();
//        line.setStartX(150.0f);
//        line.setStartY(140.0f);
//        line.setEndX(450.0f);    <---- ЛИНИИИ!!!
//        line.setEndY(140.0f);
//        pane.getChildren().add(line);
        //ПОФИКСИТЬ БАГ БЛЯТЬ ОЧЕРЕДНОЙ С ПРОБЕЛАМИ
        RadioButton rButton = rButtonPositionOperator.designButton(text, pos);
        pane.getChildren().add(rButton);
        radioButtonList.add(rButton);
        rButton.setToggleGroup(rButtonGroup);
        //rButton.isArmed();
    }

    private void deleteRButtons(){
        for (int i = 0; i < radioButtonList.size(); i++) {
            RadioButton button = radioButtonList.get(i);
            pane.getChildren().remove(radioButtonList.get(i));
        }
        rButtonPositionOperator.setActualPosition(42);
        radioButtonList.clear();
    }

    private void constructLButton(String text, int pos) {
        ToggleButton listButton = listButtonPositionOperator.designButton(text,pos);
        pane.getChildren().add(listButton);
        listButtons.add(listButton);
        listButton.setToggleGroup(lButtonGroup);

    }

    private void deletePickedButton(String selectedButtonText, RadioButton selectedButton) {
        for (int i = 0; i < radioButtonList.size(); i++){
            String equalButtonText = radioButtonList.get(i).getText();
            if (selectedButtonText.equals(equalButtonText)){
                pane.getChildren().remove(radioButtonList.get(i));
                selectedButtonIndex = i;
                radioButtonList.remove(i);
                rButtonPositionOperator.setActualPosition((int) selectedButton.getLayoutY());
                break;
            }
        }
    }
    private void setupRButtons(){
        for (String s : stringRButtonList) {
            int actualPosition = rButtonPositionOperator.getActualPosition();
            constructRButton(s,actualPosition);
            rButtonPositionOperator.actualPositionChanger();
        }
    }
    private void setupLButtons() throws SQLException, ClassNotFoundException {
        stringLButtonList = dbOperator.loadListNames();
        for (String s : stringLButtonList) {
            System.out.println(s);
            s = s.replaceAll("_", " ");
            int actualPosition = listButtonPositionOperator.getActualPosition();
            constructLButton(s,actualPosition);
            listButtonPositionOperator.actualPositionChanger();
        }
    }

    private void shiftButtons() {

        for (int i = selectedButtonIndex; i < radioButtonList.size(); i++) {
            RadioButton button = radioButtonList.get(i);
            ChangePositionAnimation anim = new ChangePositionAnimation(button);
            anim.playAnim();
        }

        TimerTask task1 = new TimerTask() {
            public void run() {
                for (int i = selectedButtonIndex; i < radioButtonList.size(); i++){
                    RadioButton button = radioButtonList.get(i);
                    BackwardAnim backwardAniman = new BackwardAnim(button,rButtonPositionOperator.getActualPosition());
                    backwardAniman.playAnim();
                }
            }
        };
        Timer timer1 = new Timer();
        long delay = 203L;
        timer1.schedule(task1, delay);
        TimerTask task2 = new TimerTask() {
            public void run() {
                for (int i = selectedButtonIndex; i < radioButtonList.size(); i++) {
                    RadioButton button = radioButtonList.get(i);
                    int nextButtonPosition = (int) button.getLayoutY();
                    button.setLayoutY(rButtonPositionOperator.getActualPosition());
                    rButtonPositionOperator.setActualPosition(nextButtonPosition);

                }
            }
        };
        Timer timer2 = new Timer();
        long delay3 = 210L;
        timer2.schedule(task2,delay3);
    }

    public class TaskHelper implements TaskAndListListener {

        @Override
        public void getStringText(String text) {
            DbOperator taskAdder = new DbOperator();
            StringOperator lineOperator = new StringOperator();
            if (lineOperator.chekStrexistance(text)) {
                try {
                    taskAdder.taskWriter(text, selectedLButton.getText());
                    String task = text;
                    int actualPos = rButtonPositionOperator.getActualPosition();
                    constructRButton(task,actualPos);
                    rButtonPositionOperator.actualPositionChanger();
                } catch (ClassNotFoundException | SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public class ListNameHelper implements TaskAndListListener {

        @Override
        public void getStringText(String text) {
            String listName = text;
            System.out.println(listName);
            int actualPos = listButtonPositionOperator.getActualPosition();
            constructLButton(listName,actualPos);
            listButtonPositionOperator.actualPositionChanger();

        }
    }

    public class AnimListener implements TaskAndListListener {


        @Override
        public void getStringText(String text) {
            rButtonPositionOperator.setActualPosition(42);
            setupRButtons();
        }
    }

}
