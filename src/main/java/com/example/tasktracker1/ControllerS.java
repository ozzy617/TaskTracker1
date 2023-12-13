package com.example.tasktracker1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerS {
    @FXML
    private Button bt;

    @FXML
    private RadioButton taskTwo;

    public void initialize(){
        AddController taskOperator = new AddController();
        TaskHelper get = new TaskHelper();
        taskOperator.setTaskListener(get);
        bt.setOnAction(actionEvent -> {
            System.out.println("da");

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("addWindow.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });
    }
    public static class TaskHelper implements TaskListener{
        @Override
        public void getTaskText(String taskText) {
            String task = taskText;
            System.out.println("1 + " + taskText);
        }
    }
}