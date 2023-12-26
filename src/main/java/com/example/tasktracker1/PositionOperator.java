package com.example.tasktracker1;

import javafx.scene.control.RadioButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class PositionOperator {
    public static RadioButton buttonDesigner(String text, int pos){
        RadioButton radioButton = new RadioButton(text);
        radioButton.setFont(Font.font("American Typewriter Semibold",15));
        radioButton.setTextFill(Color.DARKBLUE);
        radioButton.setLayoutX(10);
        radioButton.setLayoutY(pos);
        radioButton.setPrefWidth(470);
        radioButton.setPrefHeight(40);
        return radioButton;
    }

    private static int selectedPos = 42;

    public static void setActualPosition(int pos){
        selectedPos = pos;
    }
    public static int getActualPosition(){
        return selectedPos;
    }
    public static void actualPositionChanger(){
        selectedPos += 28;
    }
}
