package com.example.tasktracker1;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.sql.SQLException;

public class AddListController {

    @FXML
    private Button applyListButton;

    @FXML
    private TextField textList;

    public void initialize() {
        textList.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                applyListButton.fire();
            }
        });
        Platform.runLater(() -> {
            textList.requestFocus();
        });
        applyListButton.setOnAction(actionEvent -> {
            System.out.println(textList + " + 5");
            String tasksListName = textList.getText().trim();
            System.out.println(tasksListName + " +6");
            StringOperator lineOperator = new StringOperator();
            if (lineOperator.chekStrExistance(tasksListName)){
                DbOperator dbOperator = new DbOperator();


                TaskAndListNameTransporter transporter = new TaskAndListNameTransporter();
                transporter.getListName(tasksListName);
//                try {
//                    dbOperator.createList(tasksListName);
//                    TaskAndListNameTransporter transporter = new TaskAndListNameTransporter();
//                    transporter.getListName(tasksListName);
//                } catch (SQLException | ClassNotFoundException e) {
//                    applyListButton.getScene().getWindow().hide();
//                }
            }
            applyListButton.getScene().getWindow().hide();
        });
        StyleHelper.styleButton(applyListButton);
    }
}
