package com.example.tasktracker1;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class AddListController {

    @FXML
    private Button applyListButton;

    @FXML
    private TextField textList;

    public void initialize() {
        applyListButton.setOnAction(actionEvent -> {
            String tasksListName = textList.getText().trim();
            StringOperator lineOperator = new StringOperator();
            if (lineOperator.checkStrExistance(tasksListName)) {
                DbOperator dbOperator = new DbOperator();
                try {
                    dbOperator.createList(tasksListName);
                    TaskAndListNameTransporter transporter = new TaskAndListNameTransporter();
                    transporter.getListName(tasksListName);
                } catch (SQLException | ClassNotFoundException e) {
                    applyListButton.getScene().getWindow().hide();
                }
            }
            applyListButton.getScene().getWindow().hide();
        });
        StyleHelper.styleButton(applyListButton);
    }
}
