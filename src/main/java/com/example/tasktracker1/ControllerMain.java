package com.example.tasktracker1;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerMain {

    @FXML
    private Button bt = new Button();

    @FXML
    private Button plus;

    @FXML
    private AnchorPane anc;

    @FXML
    private TextField searchField;

    public void initialize() {
        searchField.setStyle(" -fx-focus-color: DCDCDC;");
        searchField.setFocusTraversable(false);
        Platform.runLater(() -> anc.requestFocus());
        bt.fire();
        bt.setOnAction(actionEvent -> setOnAction("addWindow.fxml"));
        StyleHelper.styleButton(bt);
        anc.setOnMouseClicked(e -> {
            Platform.runLater(() -> anc.requestFocus());
        });
        plus.setOnAction(actionEvent -> {
            setOnAction("AddListWindow.fxml");
        });
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
        Platform.runLater(() -> anc.requestFocus());
    }
}