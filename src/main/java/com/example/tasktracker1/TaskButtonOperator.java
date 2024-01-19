package com.example.tasktracker1;

import javafx.scene.control.RadioButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TaskButtonOperator {
    private final static String BUTTONS_FONT = "American Typewriter Semibold";
    private static int INITIAL_BUTTON_POS = 42;
    private final static int BUTTONS_OFFSET = 28;

    public RadioButton designButton(String text, int pos) {
        RadioButton radioButton = new RadioButton(text);
        radioButton.setFont(Font.font(BUTTONS_FONT,15));
        radioButton.setTextFill(Color.DARKBLUE);
        radioButton.setLayoutX(290);
        radioButton.setLayoutY(pos);
        radioButton.setPrefWidth(437);//437
        radioButton.setPrefHeight(20);//40
        return radioButton;
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
    public void downgradeActualPosition() {
        INITIAL_BUTTON_POS -= BUTTONS_OFFSET;
    }
}
