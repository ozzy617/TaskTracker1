package com.example.tasktracker1;

import javafx.scene.control.RadioButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TaskButton {
    public static RadioButton buttonDesigner(String text, int pos){
        RadioButton radioButton = new RadioButton(text);
        radioButton.setFont(Font.font("American Typewriter Semibold",15));
        radioButton.setTextFill(Color.DARKBLUE);
        radioButton.setLayoutX(10);
        radioButton.setLayoutY(pos);
        radioButton.setPrefWidth(150);
        radioButton.setPrefHeight(40);
        return radioButton;
    }
}
