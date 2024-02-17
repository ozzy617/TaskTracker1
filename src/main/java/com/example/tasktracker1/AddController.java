package com.example.tasktracker1;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class AddController {

    @FXML
    private Button applyButton;

    @FXML
    private TextField textTask;

    public void initialize() {
        Platform.runLater(() -> {
            textTask.requestFocus();
        });
        textTask.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                applyButton.fire();
            }
        });
        applyButton.setOnAction(actionEvent -> {
            String task = textTask.getText().trim();
            TaskAndListNameTransporter transporter = new TaskAndListNameTransporter();
            transporter.getTask(task);
            applyButton.getScene().getWindow().hide();
        });
        StyleHelper.styleButton(applyButton);
    }

}
