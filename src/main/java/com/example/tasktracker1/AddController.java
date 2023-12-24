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
    public String h;

    public void initialize() {
        applyButton.setOnAction(actionEvent -> {
            DbOperator taskAdder = new DbOperator();
            task = textTask.getText();
            try {
                taskAdder.taskWriter(task);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            TaskTransporter.taskGetter(task);
            applyButton.getScene().getWindow().hide();

        });
    }
}
