package com.example.tasktracker1;

import javafx.fxml.FXML;
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
            System.out.println(textList + " + 5");
            String tasksListName = textList.getText().trim();
            System.out.println(tasksListName + " +6");
            StringOperator lineOperator = new StringOperator();
            if (lineOperator.chekStrexistance(tasksListName)){
                DbOperator dbOperator = new DbOperator();
                try {
                    dbOperator.createList(tasksListName);
                    TaskAndListNameTransporter transporter = new TaskAndListNameTransporter();
                    transporter.getListName(tasksListName);
                } catch (SQLException e) {
                    applyListButton.getScene().getWindow().hide();
                } catch (ClassNotFoundException e) {
                    applyListButton.getScene().getWindow().hide();
                }
            }
            applyListButton.getScene().getWindow().hide();
        });
    }
}
