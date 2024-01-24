package com.example.tasktracker1;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;

public class ListButtonOperator {
    private final static String BUTTONS_FONT = "American Typewriter Semibold";
    private static int INITIAL_BUTTON_POS = 42;
    private final static int BUTTONS_OFFSET = 40;

    public ToggleButton designButton(String text, int pos) {
//        LinearGradient gradient = new LinearGradient(0,0,1,0,true, CycleMethod.NO_CYCLE, new Stop[]{
//                new Stop(0, javafx.scene.paint.Color.DARKBLUE),
//                new Stop(1, javafx.scene.paint.Color.LIGHTBLUE)
//        });

        ToggleButton button = new ToggleButton(text);
        button.setFont(Font.font(BUTTONS_FONT,15));
        button.setTextFill(Color.WHITE);
        button.setStyle("-fx-background-color: #20B2AA");
        button.setOnMouseEntered(e -> {
            button.setCursor(Cursor.HAND);
            button.setStyle("-fx-background-color: #FFFFFF, " +
                    "linear-gradient(from 0% 0% to 100% 100%, #20B2AA, #4169E1)");
        });
        button.setOnMouseExited(e -> {
            button.setCursor(Cursor.DEFAULT);
            if (!button.isDisable()){
                button.setStyle("-fx-background-color: #20B2AA");
            }
        });
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
