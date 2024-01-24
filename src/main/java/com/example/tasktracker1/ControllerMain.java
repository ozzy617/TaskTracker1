package com.example.tasktracker1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerMain {

    @FXML
    private Button bt;

    @FXML
    private Button plus;

    public void initialize() {
        bt.fire();
        bt.setOnAction(actionEvent -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("addWindow.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Parent root = loader.getRoot();//?
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });
        bt.setOnMouseEntered(e -> {
            bt.setCursor(Cursor.HAND);
            bt.setStyle("-fx-background-color: #4682B4;");
        });
        bt.setOnMouseExited(e -> {
            bt.setCursor(Cursor.DEFAULT);
            bt.setStyle("-fx-background-color: #008080;");
        });
        plus.setOnAction(actionEvent -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("AddListWindow.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });
        plus.setOnMouseEntered(e -> {
            plus.setCursor(Cursor.HAND);
            plus.setStyle("-fx-background-color: #DCDCDC;");
        });
        plus.setOnMouseExited(e -> {
            plus.setCursor(Cursor.DEFAULT);
            plus.setStyle("-fx-background-color: transparent;");
        });
    }
    public Button activateButton() {
        return bt;
    }
}