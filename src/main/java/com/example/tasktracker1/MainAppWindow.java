package com.example.tasktracker1;

import com.example.tasktracker1.anim.BackwardAnim;
import com.example.tasktracker1.anim.ChangePositionAnimation;
import com.example.tasktracker1.database.DbOperator;
import com.example.tasktracker1.operators.ListButtonOperator;
import com.example.tasktracker1.operators.TaskAndListListener;
import com.example.tasktracker1.operators.TaskAndListNameTransporter;
import com.example.tasktracker1.operators.TaskButtonOperator;
import com.example.tasktracker1.util.StringOperator;
import com.example.tasktracker1.util.StyleHelper;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainAppWindow extends Application  {
    private final LinkedList<Line> linesList = new LinkedList<>();
    private AnchorPane pane;
    private final String selectedButtonActive = "-fx-background-color: #B8860B";//#4169E1
    private final String selectedButtonActiveText = "#FFFFFF";//#4169E1
   // private final String selected
    private final String selectedButtonInactive = "-fx-background-color: #474C5F; -fx-border-color: #FFFFFF; -fx-border-width:2px;";//#20B2AA
    private final String selectedButtonInactiveText = "#FFFFFF";//#4169E1 474C5F
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
    private final LinkedList<Label> tasksAmountList = new LinkedList<>();
    private final ToggleGroup deleteListButtonsGroup = new ToggleGroup();
    private final ArrayList<ToggleButton> deleteListButtonsArray = new ArrayList<>();
    private Button addTaskButton;
    private RadioButton selectedRButton = new RadioButton();
    Timeline removeTime = new Timeline();
    private TextField searchField;
    private Button addListButton;
    private final ArrayList<Label> labelList = new ArrayList<>();
    private final AtomicBoolean isToggleSelected = new AtomicBoolean(false);
    private final AtomicBoolean isSearchFieldSelected = new AtomicBoolean(false);
    Timeline timeline;
    private final ToggleGroup optionGroup = new ToggleGroup();

    HashMap<String, LinkedList<RadioButton>> searchedButtons = new HashMap<>();
    @Override
    public void start(Stage stage) throws IOException, SQLException, ClassNotFoundException {

        FXMLLoader fxmlLoader = new FXMLLoader(MainAppWindow.class.getResource("sample.fxml"));
        pane = new AnchorPane();
        Parent root = fxmlLoader.load();
        pane.getChildren().add(root);
        addTaskButton = (Button) root.lookup("#bt");
        addListButton = (Button) root.lookup("#plus");
        searchField = (TextField) root.lookup("#searchField");
        Button completed = (Button) root.lookup("#completed");

        completed.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                deleteLabels();
                deleteRButtons();
                deleteAllLines();
                deleteListInfo();
                radioButtonList.clear();
                find("", true);
                selectedLButton.setStyle(selectedButtonInactive);
                selectedLButton.setTextFill(Paint.valueOf(selectedButtonInactiveText));
                selectedLButton.setDisable(false);
            }
        });

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            deleteLabels();
            deleteRButtons();
            deleteAllLines();
            radioButtonList.clear();
            if (!searchField.getText().isEmpty()) {
                find(newValue, false);

            }
        });

        searchField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                isSearchFieldSelected.set(true);
                selectedLButton.setDisable(false);
                selectedLButton.setStyle(selectedButtonInactive);
                selectedLButton.setTextFill(Paint.valueOf(selectedButtonInactiveText));
                addListButton.setVisible(false);
                addTaskButton.setVisible(false);
                deleteRButtons();
                deleteAllLines();
                deleteListInfo();
                System.out.println("Фокус установлен на текстовом поле :");
            } else {
                System.out.println("ELSE");
                timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> {
                    if (!isToggleSelected.get()) {
                        System.out.println("ENTERED");
                        deleteLabels();
                        selectedLButton.setDisable(true);
                        selectedLButton.setStyle(selectedButtonActive);
                        selectedLButton.setTextFill(Paint.valueOf(selectedButtonActiveText));
                        deleteRButtons();
                        searchField.clear();
                        try {
                            setupRButtons();
                            uploadListInfo();
                        } catch (SQLException | ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                        addListButton.setVisible(true);
                        addTaskButton.setVisible(true);
                        System.out.println("Фокус снят");
                        isSearchFieldSelected.set(false);
                    }
                    else {
                        selectedRButton.setVisible(false);
                    }
                }));
                isSearchFieldSelected.set(true);
                timeline.play();
                System.out.println("FALSE");
                isToggleSelected.set(false);
            }
        });


        setupLButtons();
        setPane();


        TaskAndListNameTransporter taskTransporter = new TaskAndListNameTransporter();
        TaskHelper taskGetter = new TaskHelper();
        taskTransporter.setTaskListener(taskGetter);

        TaskAndListNameTransporter listNameTransporter = new TaskAndListNameTransporter();
        ListNameHelper listNameGetter = new ListNameHelper();
        listNameTransporter.setListNameListener(listNameGetter);

        Scene scene = new Scene(pane, 750, 650);

        stage.setTitle("TaskTracker");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        rButtonGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle oldValue, Toggle newValue) {
                isToggleSelected.set(true);

                selectedRButton =  (RadioButton) newValue;
                setRButtonInactive();
                selectedRButton.setDisable(false);
                String selectedButtonText  =  selectedRButton.getText();
                selectedRButton.setTextFill(Color.LIGHTGREY);
                removeTime = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
                        try {
                            deletePickedButton(selectedButtonText, selectedRButton);
                            shiftButtons();
                            dbOperator.deleteTask(selectedButtonText, selectedLButton.getText());
                            setRButtonsActive();
                            constructTaskSumLabels();
                        } catch (SQLException | ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    selectedRButton = null;
                }));

                if (isSearchFieldSelected.get()) {
                    System.out.println("FOCUSED - " + isSearchFieldSelected.get());
                    try {
                        String lButtonValue = "";
                        for (String s : searchedButtons.keySet()) {
                            LinkedList<RadioButton> list = searchedButtons.get(s);
                            System.out.println(list.size());
                            for (int i = 0; i < list.size(); i++) {
                                System.out.println( s + ": " + list.get(i) + " - " + selectedRButton);
                                if (list.get(i) == selectedRButton) {
                                    lButtonValue = s;
                                }
                            }
                        }
                        System.out.println(lButtonValue + " - text");
                        deletePickedButton(selectedButtonText, selectedRButton);
                        dbOperator.deleteTask(selectedRButton.getText(), lButtonValue);
                        deleteRButtons();
                        setupRButtons();


                        constructTaskSumLabels();
                    } catch (SQLException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    isSearchFieldSelected.set(false);
                    String tx  = searchField.getText();
                    searchField.clear();
                    Platform.runLater(() -> {
                        searchField.requestFocus();
                        searchField.setText(tx);
                        searchField.positionCaret(searchField.getText().length());
                        searchField.end();
                    });
                } else {
                    System.out.println("SKIPPED");
                    removeTime.play();
                }
                selectedRButton.setOnMousePressed(actionEvent -> {
                    removeTime.stop();
                    selectedRButton = null;
                    deleteRButtons();
                    try {
                        setupRButtons();
                    } catch (SQLException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        });
        lButtonGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle oldValue, Toggle newValue) {

                //метод RefreshButtons
                completed.setDisable(false);
                completed.setStyle("-fx-background-color: #d0d0d0; -fx-background-radius: 15");
                ((FontAwesomeIconView) root.lookup("#completeIcon")).setFill(Paint.valueOf("#7C7979"));
                ((Label) root.lookup("#completeLabel")).setStyle("-fx-text-fill: #000000");
                completed.setCursor(Cursor.HAND);
                deleteAllLines();
                deleteRButtons();
                deleteLabels();
                //метод RefreshButtons

                deleteLabels();
                System.out.println("ToggleGroup сработала");
                listButtons.get(0).setDisable(false);
                listButtons.get(0).setStyle(selectedButtonInactive);
                listButtons.get(0).setTextFill(Paint.valueOf(selectedButtonInactiveText));
                try {
                    removeTime.stop();
                    if (selectedRButton != null) {
                        dbOperator.deleteTask(selectedRButton.getText(), selectedLButton.getText());
                        constructTaskSumLabels();
                        selectedRButton = null;
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                if (newValue != null) {
                    selectedLButton = (ToggleButton) newValue;
                    lastSelectedButton.setDisable(false);
                    selectedLButton.setDisable(true);
                    lastSelectedButton.setStyle(selectedButtonInactive);
                    lastSelectedButton.setTextFill(Paint.valueOf(selectedButtonInactiveText));
                    selectedLButton.setStyle(selectedButtonActive);
                    selectedLButton.setTextFill(Paint.valueOf(selectedButtonActiveText));
                    lastSelectedButton = selectedLButton;
                } else  {

                }
                selectedLButton.setDisable(true);
                selectedLButton.setStyle(selectedButtonActive);
                selectedLButton.setTextFill(Paint.valueOf(selectedButtonActiveText));

                selectedLButton.setOpacity(1);
                //lastSelectedButton = selectedLButton;

                try {
                    stringRButtonList = dbOperator.loadListValues(selectedLButton.getText());
                    deleteRButtons();
                    setupRButtons();
                } catch (ClassNotFoundException | SQLException e) {
                    throw new RuntimeException(e);
                }
                setTaskListName(selectedLButton.getText());
                uploadListInfo();
            }
        });

        deleteListButtonsGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle oldValue, Toggle newValue) {
                Platform.runLater(() -> pane.requestFocus());
                ToggleButton selectedDeleteButton = (ToggleButton) newValue;
                int selectedDeleteButtonPos = (int) selectedDeleteButton.getLayoutY();
                for (int i = 0; i < deleteListButtonsArray.size(); i++) {
                    int listButtonPos = (int) listButtons.get(i).getLayoutY();
                    listButtonPos = listButtonPos + 1;
                    System.out.println(selectedDeleteButtonPos + " == " + listButtonPos);
                    if (selectedDeleteButtonPos == listButtonPos) {
                        pane.getChildren().remove(selectedDeleteButton);
                        pane.getChildren().remove(listButtons.get(i));
                        pane.getChildren().remove(tasksAmountList.get(i));
                        tasksAmountList.remove(i);
                        listButtons.remove(i);
                        deleteListButtonsArray.remove(i);
                        try {
                            dbOperator.deleteTable(stringLButtonList.get(i));
                            deleteLButtons();
                            setupLButtons();
                        } catch (SQLException | RuntimeException | ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    }
                }
                if (stringLButtonList.size() == 0) {
                    addTaskButton.setDisable(true);
                    addTaskButton.setVisible(false);
                    deleteAllLines();
                    deleteListInfo();
                }
            }
        });
    }

    private void constructRButton(String text, int pos) {
        RadioButton rButton = rButtonPositionOperator.designButton(text, pos);
        pane.getChildren().add(rButton);
        radioButtonList.add(rButton);
        rButton.setToggleGroup(rButtonGroup);
    }

    private void constructLabels(String text) {
        int pos = rButtonPositionOperator.getActualPosition();
        rButtonPositionOperator.changeActualPosition();
        Label label = rButtonPositionOperator.designLabel(text, pos);
        labelList.add(label);
        pane.getChildren().add(label);
    }
    private void deleteLabels() {
        for (Label l : labelList) {
            pane.getChildren().remove(l);
        }
        labelList.clear();
    }
    //ПРИ УДАЛЕНИИ БУКВ ТАКЖЕ УДАЛЯТЬ ТО ЧТО НАШЛИ ДО ЭТОГО + ЛИНИЮ
    //ТОЛЬКО ОДНА - МЕЖДУ НЕЙМОМ И ТАСКОМ ЛИНИЮ НЕ НАДО


    private void constructLines(int linesCounter, int constant, int start) {
        int c = 66;
        Line separateLine = new Line();
        separateLine.setStroke(Paint.valueOf("#474C5F"));
        separateLine.setStartX(615);
        separateLine.setStartY(0);
        separateLine.setEndX(615);
        separateLine.setEndY(66);
        pane.getChildren().add(separateLine);
        Line firstLine = rButtonPositionOperator.designLine(66);
        pane.getChildren().add(firstLine);
        for (int  i = 0; i < linesCounter + 1; i++) {
            Line line = rButtonPositionOperator.designLine(start);
            pane.getChildren().add(line);
            linesList.add(line);
            start += constant;
        }
    }

    private void deleteAllLines() {

        for (int i = 0; i < linesList.size(); i++) {
            pane.getChildren().remove(linesList.get(i));
        }
    }

    private void setRButtonInactive() {
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
    private void deleteRButtons() {
        for (int i = 0; i < radioButtonList.size(); i++) {
            pane.getChildren().remove(radioButtonList.get(i));
        }
        rButtonPositionOperator.setActualPosition(StyleHelper.TASK_BUTTON_INITIAL_POSITION);
        radioButtonList.clear();
    }
    private ToggleButton constructLButton(String text, int pos, ListButtonOperator listButtonPositionOperator, ToggleGroup lButtonGroup, AnchorPane pane) {
        ToggleButton listButton = listButtonPositionOperator.designButton(text,pos);
        pane.getChildren().add(listButton);
        listButton.setToggleGroup(lButtonGroup);
        return listButton;
    }

    private void deletePickedButton(String selectedButtonText, RadioButton selectedButton) throws SQLException, ClassNotFoundException {
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

    private void deleteListInfo() {
        tasksAmountNumb.setVisible(false);
        taskListName.setVisible(false);
    }

    private void uploadListInfo() {
        tasksAmountNumb.setVisible(true);
        taskListName.setVisible(true);
    }

    private void setTaskCountLabel() {
        rButtonPositionOperator.countTasks(radioButtonList.size(), tasksAmountNumb);
    }

    private void setTaskListName(String text) {
        rButtonPositionOperator.writeListName(text, taskListName);
    }

    private void setupStartList() throws SQLException, ClassNotFoundException {
        if (!listButtons.isEmpty()) {
            selectedLButton = listButtons.get(0);
            System.out.println(selectedLButton.getText() + " fsdddddd");
            stringRButtonList = dbOperator.loadListValues(selectedLButton.getText());
            setTaskListName(listButtons.get(0).getText());
            setupRButtons();
            listButtons.get(0).setDisable(true);
            listButtons.get(0).setStyle(selectedButtonActive);
            listButtons.get(0).setTextFill(Paint.valueOf(selectedButtonActiveText));
            listButtons.get(0).setOpacity(1);
        }
    }

    private void setupRButtons() throws SQLException, ClassNotFoundException {
        deleteAllLines();
        rButtonPositionOperator.setActualPosition(StyleHelper.TASK_BUTTON_INITIAL_POSITION);
        stringRButtonList = dbOperator.loadListValues(selectedLButton.getText());
        for (String s : stringRButtonList) {
            int actualPosition = rButtonPositionOperator.getActualPosition();
            constructRButton(s, actualPosition);
            rButtonPositionOperator.changeActualPosition();
        }
        constructLines(radioButtonList.size(), 28,66);
        setTaskCountLabel();
    }
    private void constructTaskSumLabels() throws SQLException, ClassNotFoundException {
        ArrayList<String> stringCountTaskList = dbOperator.countRows(stringLButtonList);
        for (Label s : tasksAmountList) {
            pane.getChildren().remove(s);
        }
        tasksAmountList.clear();
        for (int i = 0; i < stringCountTaskList.size(); i++) {
            Label label = listButtonPositionOperator.designTasksAmount(stringCountTaskList.get(i), (int) listButtons.get(i).getLayoutY());
            tasksAmountList.add(label);
            pane.getChildren().add(label);
        }
    }
    private void setupDeleteListButtons() {
        for (ToggleButton s : deleteListButtonsArray) {
            pane.getChildren().remove(s);
        }
        deleteListButtonsArray.clear();
        for (int i = 0; i < stringLButtonList.size(); i++) {
            ToggleButton deleteButton = listButtonPositionOperator.designDeleteListButton((int) listButtons.get(i).getLayoutY());
            pane.getChildren().add(deleteButton);
            deleteButton.setToggleGroup(deleteListButtonsGroup);
            deleteListButtonsArray.add(deleteButton);
        }
    }
    private void deleteLButtons() {
        for (int i = 0; i < listButtons.size(); i++) {
            pane.getChildren().remove(listButtons.get(i));
            pane.getChildren().remove(deleteListButtonsArray.get(i));
        }
        deleteListButtonsArray.clear();
        listButtons.clear();
        stringLButtonList.clear();
        for (int i = 0; i < radioButtonList.size(); i++) {
            pane.getChildren().remove(radioButtonList.get(i));
        }
        radioButtonList.clear();
        stringRButtonList.clear();
        listButtonPositionOperator.setActualPosition(StyleHelper.LIST_BUTTON_INITIAL_POSITION);
    }
    private void setupLButtons() throws SQLException, ClassNotFoundException {
        stringLButtonList = dbOperator.loadListNames();
        for (String s : stringLButtonList) {
            System.out.println(s + " ddddd");
            int actualPosition = listButtonPositionOperator.getActualPosition();
            listButtons.add(constructLButton(s, actualPosition, listButtonPositionOperator, lButtonGroup, pane));
            listButtonPositionOperator.actualPositionChanger();
        }
        constructTaskSumLabels();
        setupStartList();
        setupDeleteListButtons();
        if (listButtons.isEmpty()) {
            addTaskButton.setDisable(true);
            addTaskButton.setVisible(false);
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
                    BackwardAnim backwardAnim = new BackwardAnim(button);
                    backwardAnim.playAnim();
                }
            }
        };
        Timer timer1 = new Timer();
        long delay1 = 202L;
        timer1.schedule(task1, delay1);
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
        long delay3 = 204L;
        timer2.schedule(task2,delay3);
        deleteAllLines();
        constructLines(radioButtonList.size(), 28,66);
    }

    //REFACTOR
    private void find(String newValue, boolean flag) {
            LinkedList<RadioButton> tempList = new LinkedList<>();
            int lastInd = 0;
            try {
                HashMap<String, ArrayList<String>> searchedLists = dbOperator.loadSearchedValues(newValue, flag);
                for (String s : searchedLists.keySet()) {
                    tempList.clear();
                    constructLabels(s);
                    int pos = rButtonPositionOperator.getActualPosition();
                    rButtonPositionOperator.changeActualPosition();
                    ArrayList<String> list = searchedLists.get(s);
                    for (int i = 0; i < list.size(); i++) {

                        constructRButton(list.get(i), pos);
                        pos = rButtonPositionOperator.getActualPosition();
                        rButtonPositionOperator.changeActualPosition();
                    }
                    for (int i = lastInd; i < radioButtonList.size(); i++) {
                        tempList.add(radioButtonList.get(i));
                        searchedButtons.computeIfAbsent(s, k -> new LinkedList<>()).add(radioButtonList.get(i));
                        System.out.println(radioButtonList.get(i).getLayoutY() + " layout");
                        lastInd++;
                    }
                    constructLines(tempList.size() - 1, 28, (int) tempList.get(0).getLayoutY()-4 + 28);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
    }

    public class TaskHelper implements TaskAndListListener {
        @Override
        public void getStringText(String text) {
            DbOperator taskAdder = new DbOperator();
            StringOperator lineOperator = new StringOperator();
            if (lineOperator.chekStrExistance(text)) {
                try {
                    taskAdder.writeTask(text, selectedLButton.getText());
                    String task = text;
                    int actualPos = rButtonPositionOperator.getActualPosition();
                    constructRButton(task, actualPos);
                    rButtonPositionOperator.changeActualPosition();
                    deleteAllLines();
                    constructLines(radioButtonList.size(), 28, (int) radioButtonList.get(0).getLayoutY()-4);
                } catch (ClassNotFoundException | SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            setTaskCountLabel();
            try {
                constructTaskSumLabels();
            } catch (SQLException | ClassNotFoundException e){
                throw new RuntimeException(e);
            }
        }
    }

    public class ListNameHelper implements TaskAndListListener {

        @Override
        public void getStringText(String text) {
            String listName = null;
            try {
                if (!stringLButtonList.contains(text)) {
                    listName = dbOperator.createList(text);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            if (listName != null) {
                int actualPos = listButtonPositionOperator.getActualPosition();
                listButtons.add(constructLButton(listName, actualPos, listButtonPositionOperator, lButtonGroup, pane));
                listButtonPositionOperator.actualPositionChanger();
                setTaskCountLabel();
                System.out.println(listName + " - listname");
                stringLButtonList.add(listName);

                try {
                    constructTaskSumLabels();
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                if (listButtons.size() == 1) {
                    selectedLButton = listButtons.get(0);
                    setTaskListName(text);
                    setTaskCountLabel();
                    uploadListInfo();
                }
                addTaskButton.setDisable(false);
                addTaskButton.setVisible(true);
                setupDeleteListButtons();
                listButtons.get(listButtons.size() - 1).fire();
            }
        }
    }
}