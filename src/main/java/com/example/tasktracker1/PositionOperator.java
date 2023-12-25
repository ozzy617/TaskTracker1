package com.example.tasktracker1;

import javafx.scene.control.RadioButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class PositionOperator {

    private final static int BUTTONS_OFFSET = 28;
    private final static int INITIAL_BUTTON_POS = 42;
    private final static String BUTTONS_FONT = "American Typewriter Semibold";

    private int selectedPos = INITIAL_BUTTON_POS;

    public static RadioButton designButton(String text, int pos) {
        RadioButton radioButton = new RadioButton(text);
        radioButton.setFont(Font.font(BUTTONS_FONT, 15));
        radioButton.setTextFill(Color.DARKBLUE);
        radioButton.setLayoutX(10);
        radioButton.setLayoutY(pos);
        radioButton.setPrefWidth(470);
        radioButton.setPrefHeight(40);
        return radioButton;
    }

    public void setActualPosition(int pos) {
        selectedPos = pos;
    }

    public int getActualPosition() {
        return selectedPos;
    }

    public void actualPositionChanger() {
        selectedPos += BUTTONS_OFFSET;
    }
}
