package com.example.tasktracker1;

import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ListButtonOperator {
    private final static String MAIN_FONT = "American Typewriter Semibold";
    private static int INITIAL_BUTTON_POS = 42;
    private final static int BUTTONS_OFFSET = 40;

    public ToggleButton designButton(String text, int pos) {

        ToggleButton button = new ToggleButton(text);
        button.setFont(Font.font(MAIN_FONT,15));
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
    public Label designTasksAmount(String tasksAmount, int position) {
        Label taskCounter = new Label(tasksAmount);
        taskCounter.setLayoutX(220);
        taskCounter.setLayoutY(position+5);
        taskCounter.setTextFill(Color.LIGHTGREY);
        taskCounter.setFont(Font.font(MAIN_FONT,14));
        return taskCounter;
    }
    public ToggleButton designDeleteListButton(int position) {
        ToggleButton toggleButton = new ToggleButton("×");
        toggleButton.setStyle("-fx-background-color: transparent;");
        toggleButton.setTextFill(Color.LIGHTGREY);
        toggleButton.setPrefHeight(2);
        toggleButton.setPrefWidth(2);
        toggleButton.setLayoutX(240);
        toggleButton.setLayoutY(position+1);
        toggleButton.setOnMouseEntered(e -> {
            toggleButton.setCursor(Cursor.HAND);
            toggleButton.setStyle("-fx-background-color: #DCDCDC;");
            toggleButton.setOpacity(0.5);
            toggleButton.setTextFill(Color.BLACK);
        });
        toggleButton.setOnMouseExited(e -> {
            toggleButton.setCursor(Cursor.DEFAULT);
            toggleButton.setStyle("-fx-background-color: transparent;");
            toggleButton.setOpacity(1);
            toggleButton.setTextFill(Color.LIGHTGREY);
        });
        return toggleButton;
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
