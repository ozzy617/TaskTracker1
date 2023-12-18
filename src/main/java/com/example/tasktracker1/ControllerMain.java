package com.example.tasktracker1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerMain {
    @FXML
    private Button bt;

    @FXML
    //private RadioButton taskTwo;
    public void initialize(){

        bt.setOnAction(actionEvent -> {
            System.out.println("da");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("addWindow.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Parent root = loader.getRoot();//?
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });
    }
//    public static class TaskHelper implements TaskListener{
//        private String task;
//        @Override
//        public void getTaskText(String taskText) {
//
//            task = taskText;
//            System.out.println("chek + " + task);
//        }
//    }

}