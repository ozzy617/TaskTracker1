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
import java.util.LinkedList;

public class MainAppWindow extends Application  {
    private AnchorPane pane;
    private  RadioButton button;
    private LinkedList<RadioButton> radioButtons = new LinkedList<>();

    private ToggleGroup buttonGroup = new ToggleGroup();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainAppWindow.class.getResource("sample.fxml"));
        //ОТДЕЛЬНЫЙ МЕТОД
        pane = new AnchorPane();
        pane.getChildren().add(fxmlLoader.load());
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
                RadioButton selButton =  (RadioButton) newValue;
                String s  =  selButton.getText();
                selButton.setDisable(true);
                System.out.println("Selected button = " + s);
            }
        });

    }

    private void s(String text, int pos) {
        button = TaskButton.buttonDesigner(text, pos);
        pane.getChildren().add(button);
        button.setToggleGroup(buttonGroup);

        //ДОПИСАТЬ! ТУТ ДОЛЖЕН БЫТЬ КОД, НО ЛЕНЬ))

        radioButtons.add(button);
        System.out.println(radioButtons.get(0));
        String s = button.getText();
    }
    public class TaskHelper implements TaskListener{
        private String task;
        private int pos = 15;
        @Override
        public void getTaskText(String taskText) {
            task = taskText;
            pos += 28;
            s(task,pos);
//            System.out.println("chek + " + task);
        }
    }

    public static void main(String[] args) {
        launch();
    }

}
