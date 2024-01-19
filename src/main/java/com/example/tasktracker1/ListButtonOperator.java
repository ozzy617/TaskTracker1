package com.example.tasktracker1;

import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ListButtonOperator {
    private final static String BUTTONS_FONT = "American Typewriter Semibold";
    private static int INITIAL_BUTTON_POS = 42;
    private final static int BUTTONS_OFFSET = 40;

    public ToggleButton designButton(String text, int pos) {
        ToggleButton button = new ToggleButton(text);
        button.setFont(Font.font(BUTTONS_FONT,15));
        button.setTextFill(Color.WHITE);
        button.setStyle("-fx-background-color: #4682B4");
        button.setLayoutX(20);
        button.setLayoutY(pos);
        button.setPrefWidth(250);//437
        button.setPrefHeight(12);//40
        return button;
    }

    public void setActualPosition(int pos) {
        INITIAL_BUTTON_POS = pos;
    }

    public int getActualPosition() {
        return INITIAL_BUTTON_POS;
    }

    public void actualPositionChanger() {
        INITIAL_BUTTON_POS += BUTTONS_OFFSET;
    }
}
