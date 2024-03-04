package com.example.tasktracker1;

import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

public class TaskButtonOperator {

    private static int initialButtonPos = StyleHelper.TASK_BUTTON_INITIAL_POSITION;
    private final static int BUTTONS_OFFSET = 28;

    public RadioButton designButton(String text, int pos) {
        RadioButton radioButton = new RadioButton(text);
        radioButton.setStyle("-fx-mark-color:  blue;");
        radioButton.setFont(Font.font(StyleHelper.MAIN_FONT,15));
        radioButton.setTextFill(Color.BLACK);
        radioButton.setLayoutX(290);
        radioButton.setLayoutY(pos);
        radioButton.setPrefWidth(437);
        radioButton.setPrefHeight(20);
        radioButton.setOnMouseEntered(e -> radioButton.setCursor(Cursor.HAND));
        radioButton.setOnMouseExited(e -> radioButton.setCursor(Cursor.DEFAULT));
        return radioButton;
    }

    public Line designLine(int linePos) {
        Line line = new Line();
        line.setStroke(Color.LIGHTGREY);
        line.setStartX(290);
        line.setStartY(linePos);
        line.setEndX(750);
        line.setEndY(linePos);
        return line;
    }

    public void countTasks(int rButtonListSize, Label label) {
        String tasksAmount = String.valueOf(rButtonListSize);
        label.setText(tasksAmount);
        label.setLayoutX(590);
        label.setLayoutY(16);
        label.setFont(Font.font(StyleHelper.MAIN_FONT,28));
    }

    public void writeListName(String strListName, Label label) {
        label.setText(strListName);
        label.setLayoutX(300);
        label.setLayoutY(16);
        label.setFont(Font.font(StyleHelper.MAIN_FONT,28));
    }

    public void setActualPosition(int pos) {
        initialButtonPos = pos;
    }

    public int getActualPosition() {
        return initialButtonPos;
    }

    public void changeActualPosition() {
        initialButtonPos += BUTTONS_OFFSET;
    }
}
