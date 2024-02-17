package com.example.tasktracker1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerMain {

    @FXML
    private Button bt = new Button();

    @FXML
    private Button plus;

    @FXML
    AnchorPane anc;

    public void initialize() {
        bt.fire();
        bt.setOnAction(actionEvent -> setOnAction("addWindow.fxml"));
        StyleHelper.styleButton(bt);

        plus.setOnAction(actionEvent -> setOnAction("AddListWindow.fxml"));
        plus.setOnMouseEntered(e -> {
            plus.setCursor(Cursor.HAND);
            plus.setStyle("-fx-background-color: #DCDCDC;");
        });
        plus.setOnMouseExited(e -> {
            plus.setCursor(Cursor.DEFAULT);
            plus.setStyle(StyleHelper.TRANSPARENT_STYLE);
        });

    }
    private void setOnAction(String resourceName) {
//        plus.setDisable(true);
//        bt.setDisable(true);
        anc.getChildren().remove(plus);
        anc.getChildren().remove(bt);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(resourceName));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.showAndWait();
        anc.getChildren().add(plus);
        anc.getChildren().add(bt);
    }
}