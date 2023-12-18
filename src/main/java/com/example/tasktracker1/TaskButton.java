package com.example.tasktracker1;

import javafx.application.Application;
import javafx.scene.control.RadioButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TaskButton {
    public String f;
    RadioButton button;
    public void buttonCopy(RadioButton b){
        button = b;
        //buttonDesigner(button);
    }
    public static RadioButton buttonDesigner(String text, int pos){
        int yPosition = 50;
        RadioButton testR= new RadioButton(text);
        testR.setFont(Font.font("American Typewriter Semibold",15));
        testR.setTextFill(Color.DARKBLUE);
        testR.setLayoutX(10);
        testR.setLayoutY(pos);
        testR.setPrefWidth(150);
        testR.setPrefHeight(40);
        return testR;
    }
}
