package com.example.tasktracker1;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class MainAppWindow extends Application  {

    private final LinkedList<Line> linesList = new LinkedList<>();
    private AnchorPane pane;
    private final String selectedButtonActive = "-fx-background-color: #4169E1";
    private final String selectedButtonInactive = "-fx-background-color: #20B2AA";
    private final ToggleGroup rButtonGroup = new ToggleGroup();
    private final ToggleGroup lButtonGroup = new ToggleGroup();
    private final LinkedList<ToggleButton> listButtons = new LinkedList<>();
    private final LinkedList<RadioButton> radioButtonList = new LinkedList<>();

    private ToggleButton selectedLButton = new ToggleButton();

    private ArrayList<String> stringLButtonList = new ArrayList<>();
    private ArrayList<String> stringRButtonList = new ArrayList<>();
    private int selectedButtonIndex;
    private final DbOperator dbOperator = new DbOperator();
    private final TaskButtonOperator rButtonPositionOperator = new TaskButtonOperator();
    private final ListButtonOperator listButtonPositionOperator = new ListButtonOperator();
    private ToggleButton lastSelectedButton = new ToggleButton();
    private final Label tasksAmountNumb = new Label();
    private final Label taskListName = new Label();
    @Override
    public void start(Stage stage) throws IOException, SQLException, ClassNotFoundException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainAppWindow.class.getResource("sample.fxml"));
        pane = new AnchorPane();
        pane.getChildren().add(fxmlLoader.load());

        setupLButtons();
        setPane();
        setupStartList();

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
                setRButtonsNotActive();
                selectedButton.setDisable(false);

                String selectedButtonText  =  selectedButton.getText();

                selectedButton.setTextFill(Color.GRAY);

                Timeline removeTime = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
                    deletePickedButton(selectedButtonText, selectedButton);
                    shiftButtons();

                    try {
                        System.out.println(selectedLButton.getText() + " _ " + selectedButton.getText());
                        dbOperator.taskDeleter(selectedButtonText, selectedLButton.getText());
                    } catch (ClassNotFoundException | SQLException exception) {
                        throw new RuntimeException(exception);
                    }
                    setRButtonsActive();
                }));
                removeTime.play();
                selectedButton.setOnMousePressed(actionEvent -> {
                    removeTime.stop();
                    deleteRButtons();
                    try {
                        setupRButtons();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        });
        lButtonGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle oldValue, Toggle newValue) {
                listButtons.get(0).setDisable(false);
                listButtons.get(0).setStyle(selectedButtonInactive);

                selectedLButton = (ToggleButton) newValue;
                lastSelectedButton.setDisable(false);
                selectedLButton.setDisable(true);
                lastSelectedButton.setStyle(selectedButtonInactive);
                selectedLButton.setStyle(selectedButtonActive);
                selectedLButton.setOpacity(1);
                lastSelectedButton = selectedLButton;

                try {
                    stringRButtonList = dbOperator.loadListValues(selectedLButton.getText());
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                deleteRButtons();
                try {
                    setupRButtons();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                setTaskListName(selectedLButton.getText());
            }
        });
    }

    private void constructRButton(String text, int pos) {
        RadioButton rButton = rButtonPositionOperator.designButton(text, pos);
        pane.getChildren().add(rButton);
        radioButtonList.add(rButton);
        rButton.setToggleGroup(rButtonGroup);
        deleteAllLines();
        constructLines(radioButtonList.size());
    }

    private void constructLines(int linesCounter) {
        int c = 66;
        Line separateLine = new Line();
        separateLine.setStroke(Color.LIGHTGREY);
        separateLine.setStartX(615);
        separateLine.setStartY(0);
        separateLine.setEndX(615);
        separateLine.setEndY(c);
        pane.getChildren().add(separateLine);
        for (int  i = 0; i < linesCounter + 1; i++) {
            Line line = rButtonPositionOperator.designLine(c);
            pane.getChildren().add(line);
            linesList.add(line);
            c += 28;
        }
    }

    private void deleteAllLines() {
        for (int i = 0; i < linesList.size(); i++) {
            pane.getChildren().remove(linesList.get(i));
        }
    }

    private void setRButtonsNotActive() {
        for (RadioButton s : radioButtonList) {
            s.setDisable(true);
            s.setOpacity(1);
        }
    }
    private void setRButtonsActive() {
        for (RadioButton s : radioButtonList) {
            s.setDisable(false);
        }
    }
    private void deleteRButtons(){
        for (int i = 0; i < radioButtonList.size(); i++) {
            pane.getChildren().remove(radioButtonList.get(i));
        }
        rButtonPositionOperator.setActualPosition(70);
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
        setTaskCountLabel();
    }
    private void setPane() {
        pane.getChildren().add(tasksAmountNumb);
        pane.getChildren().add(taskListName);
    }
    private void setTaskCountLabel() {
        rButtonPositionOperator.countTasks(radioButtonList.size(), tasksAmountNumb);
    }
    private void setTaskListName(String text) {
        rButtonPositionOperator.writeListName(text, taskListName);
    }

    private void setupStartList() throws SQLException, ClassNotFoundException {
        selectedLButton = listButtons.get(0);
        stringRButtonList = dbOperator.loadListValues(stringLButtonList.get(0));
        setTaskListName(stringLButtonList.get(0));
        setupRButtons();
        listButtons.get(0).setDisable(true);
        listButtons.get(0).setStyle(selectedButtonActive);
        listButtons.get(0).setOpacity(1);
    }

    private void setupRButtons() throws SQLException, ClassNotFoundException {
        deleteAllLines();
        stringRButtonList = dbOperator.loadListValues(selectedLButton.getText());
        for (String s : stringRButtonList) {
            int actualPosition = rButtonPositionOperator.getActualPosition();
            constructRButton(s,actualPosition);
            rButtonPositionOperator.actualPositionChanger();
        }
        constructLines(radioButtonList.size());
        setTaskCountLabel();
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
        long delay = 202L;
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
        long delay3 = 205L;
        timer2.schedule(task2,delay3);
        deleteAllLines();
        constructLines(radioButtonList.size());
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
            setTaskCountLabel();
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
            setTaskCountLabel();
        }
    }
}