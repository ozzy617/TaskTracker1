package com.example.tasktracker1;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;

public class AddController {

    @FXML
    private Button applyButton;
    @FXML
    private TextField textTask;

    public String task;

    public void initialize() {
        applyButton.setOnAction(actionEvent -> {
            DbOperator taskAdder = new DbOperator();
            task = textTask.getText().trim();
            if (chekStrexistance(task)) {
                try {
                    taskAdder.taskWriter(task);
                } catch (ClassNotFoundException | SQLException e) {
                    throw new RuntimeException(e);
                }
                TaskTransporter.getTask(task);
            }
            applyButton.getScene().getWindow().hide();
        });
    }
    private boolean chekStrexistance(String taskStr) {
        String string = taskStr.replaceAll(" ", "");
        if (string.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
