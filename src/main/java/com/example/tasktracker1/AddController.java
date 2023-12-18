package com.example.tasktracker1;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class AddController {

    @FXML
    private AnchorPane anc;

    @FXML
    private Button applyButton;
    @FXML
    private TextField textTask;

    public String task;
    private boolean flag = false;

    public void initialize() {
        applyButton.setOnAction(actionEvent -> {
            flag = true;
            task = textTask.getText();
            TaskTransporter.taskGetter(task);
            applyButton.getScene().getWindow().hide();

        });
    }
    public void s(){
        //System.out.println(listener);
    }



}
