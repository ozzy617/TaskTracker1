package com.example.tasktracker1;

import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

public class TaskButtonOperator {
    private final static String MAIN_FONT = "American Typewriter Semibold";
    private static int INITIAL_BUTTON_POS = 70;
    private final static int BUTTONS_OFFSET = 28;

    public RadioButton designButton(String text, int pos) {
        RadioButton radioButton = new RadioButton(text);
        radioButton.setStyle("-fx-mark-color:  blue;");
        radioButton.setFont(Font.font(MAIN_FONT,15));
        radioButton.setTextFill(Color.BLACK);
        radioButton.setLayoutX(290);
        radioButton.setLayoutY(pos);
        radioButton.setPrefWidth(437);
        radioButton.setPrefHeight(20);
        radioButton.setOnMouseEntered(e -> {
            radioButton.setCursor(Cursor.HAND);
        });
        radioButton.setOnMouseExited(e -> {
            radioButton.setCursor(Cursor.DEFAULT);
        });
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

    public Line designLine(int linePos) {
        Line line = new Line();
        line.setStroke(Color.LIGHTGREY);
        line.setStartX(286);
        line.setStartY(linePos);
        line.setEndX(750);
        line.setEndY(linePos);
        return line;
    }
    public Label countTasks(int rButtonListSize, Label label) {
        Label tasksAmountNumb = label;
        String tasksAmount = String.valueOf(rButtonListSize);
        tasksAmountNumb.setText(tasksAmount);
        tasksAmountNumb.setLayoutX(590);
        tasksAmountNumb.setLayoutY(16);
        tasksAmountNumb.setFont(Font.font(MAIN_FONT,28));
        return tasksAmountNumb;
    }
    public Label writeListName(String strListName, Label label) {
        Label listNameLabel = label;
        listNameLabel.setText(strListName);
        listNameLabel.setLayoutX(300);
        listNameLabel.setLayoutY(16);
        listNameLabel.setFont(Font.font(MAIN_FONT,28));
        return listNameLabel;
    }
}
