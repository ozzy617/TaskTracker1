package com.example.tasktracker1;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import  javafx.scene.paint.Paint;
import javafx.fxml.FXML;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("sample.fxml"));
        //ОТДЕЛЬНЫЙ МЕТОД
        AnchorPane pane = new AnchorPane();
        pane.getChildren().add(fxmlLoader.load());
//        Button addButton = new Button("Добавить задачу");
//         //rButton.setLayoutY(50);
//         //ТО ЧТО НИЖЕ -  В ОТЕДЛЬНЫЙ СЕТТЕР
//        //2 Конпки добавления реализовать в одну
//        addButton.setLayoutY(14);
//        addButton.setLayoutX(444);
//        addButton.setPrefWidth(150);
//        addButton.setPrefHeight(40);
//        addButton.setFont(Font.font("American Typewriter Semibold"));
//        pane.getChildren().add(addButton);
//        addButton.setOnAction(actionEvent -> {
//            RadioButton rButton = new RadioButton("TEST");
//            RadioButton dButton = new RadioButton("HAHA");
//            s(dButton);
//            s(rButton);
//            pane.getChildren().add(rButton);
//            pane.getChildren().add(dButton);
//        });
        Scene scene = new Scene(pane, 600, 300);
        stage.setTitle("TaskTracker");
        stage.setScene(scene);
        stage.show();

    }
    private void s(RadioButton b){
        b.setFont(Font.font("American Typewriter Semibold",20));
        b.setLayoutY(50);
        b.setTextFill(Color.DARKBLUE);
    }

    private void hightSetter(int h) {

    }


    public static void main(String[] args) {
        launch();
    }

}
