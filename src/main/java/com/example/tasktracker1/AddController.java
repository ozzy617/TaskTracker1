package com.example.tasktracker1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AddController {

    @FXML
    private AnchorPane anc;

    @FXML
    private Button applyButton;
    @FXML
    private TextField textTask;

    private TaskListener listener;
    public void setTaskListener(TaskListener inputListener) {
        listener = inputListener;
    }

    public void initialize() {
        applyButton.setOnAction(actionEvent -> {
            String task = textTask.getText();
            System.out.println(task);
            listener.getTaskText(task);
            applyButton.getScene().getWindow().hide();
        });

    }
}
