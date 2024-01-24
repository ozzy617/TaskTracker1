package com.example.tasktracker1;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class AddController {

    @FXML
    private Button applyButton;
    @FXML
    private TextField textTask;

    public void initialize() {
        applyButton.setOnAction(actionEvent -> {
            String task = textTask.getText().trim();
            TaskAndListNameTransporter transporter = new TaskAndListNameTransporter();
            transporter.getTask(task);
            applyButton.getScene().getWindow().hide();
        });
        applyButton.setOnMouseEntered(e -> {
            applyButton.setCursor(Cursor.HAND);
            applyButton.setStyle("-fx-background-color: #4682B4;");
        });
        applyButton.setOnMouseExited(e -> {
            applyButton.setCursor(Cursor.DEFAULT);
            applyButton.setStyle("-fx-background-color: #008080;");
        });
    }
}
