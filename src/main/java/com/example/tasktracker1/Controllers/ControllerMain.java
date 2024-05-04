package com.example.tasktracker1.Controllers;

import com.example.tasktracker1.MainAppWindow;
import com.example.tasktracker1.util.StyleHelper;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.util.Duration;

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

    @FXML
    private Button completed;

    @FXML
    private FontAwesomeIconView completeIcon;

    @FXML
    private Label completeLabel;

    public void initialize() {
        searchField.setStyle("-fx-focus-color: #a41ba4; -fx-border-radius: 3px; -fx-background-color: #83899b; -fx-border-style: solid; -fx-border-color: #83899b;");
        searchField.setFocusTraversable(false);//#5a606e
        Platform.runLater(() -> anc.requestFocus());
        bt.setOnAction(actionEvent -> setOnAction("/com/example/tasktracker1/addWindow.fxml"));
        StyleHelper.styleButton(bt);
        anc.setOnMouseClicked(e -> {
            Platform.runLater(() -> anc.requestFocus());
        });
        plus.setOnAction(actionEvent -> {
            setOnAction("/com/example/tasktracker1/AddListWindow.fxml");
        });
        plus.setOnMouseEntered(e -> {
            plus.setCursor(Cursor.HAND);
            plus.setStyle("-fx-background-color: #5a606e;");
        });
        plus.setOnMouseExited(e -> {
            plus.setCursor(Cursor.DEFAULT);
            plus.setStyle(StyleHelper.TRANSPARENT_STYLE);
        });
        completed.setOpacity(1);
        completed.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                completed.setStyle("-fx-background-color: #838BA1FF;-fx-background-radius: 15");
                completeIcon.setFill(Paint.valueOf("#d0d0d0"));
                completeLabel.setStyle("-fx-text-fill: #d0d0d0");
                completed.setCursor(Cursor.DEFAULT);
            } else {

                completed.setDisable(false);
                completed.setStyle("-fx-background-color: #d0d0d0; -fx-background-radius: 15");
                completeIcon.setFill(Paint.valueOf("#7C7979"));
                completeLabel.setStyle("-fx-text-fill: #000000");
                completed.setCursor(Cursor.HAND);
            }
        });
//        completed.setOnAction(e -> {
//            completed.setStyle("-fx-background-color: #838BA1FF;-fx-background-radius: 15");
//            completeIcon.setFill(Paint.valueOf("#d0d0d0"));
//            completeLabel.setStyle("-fx-text-fill: #d0d0d0");
//        });
//        completed.setOnMouseReleased(e -> {
//v
//        });
//        completed.setOnAction(e->{
//
//        });
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