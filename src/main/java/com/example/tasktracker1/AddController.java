package com.example.tasktracker1;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class AddController {

    @FXML
    private Button applyButton;

    @FXML
    private TextField textTask;

    private String task;

    public void initialize() {
        applyButton.setOnAction(actionEvent -> {
            DbOperator taskAdder = new DbOperator();
            task = textTask.getText();
            try {
                taskAdder.insertTask(task);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            TaskTransporter.getTask(task);
            applyButton.getScene().getWindow().hide();
        });
    }
}
