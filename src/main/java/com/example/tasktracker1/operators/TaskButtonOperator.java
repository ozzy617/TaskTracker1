package com.example.tasktracker1.operators;

import com.example.tasktracker1.util.StyleHelper;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

public class TaskButtonOperator {

    private static int initialButtonPos = StyleHelper.TASK_BUTTON_INITIAL_POSITION;
    private final static int BUTTONS_OFFSET = 28;

    public RadioButton designButton(String text, int pos) {
        RadioButton radioButton = new RadioButton(text);
        //"-fx-background-color: red; -fx-border-color: transparent; -fx-background-radius: 10;"
        radioButton.setStyle("-fx-mark-color: black; -fx-show-tick-marks: true; -fx-focus-color: black");
        radioButton.setFont(Font.font(StyleHelper.MAIN_FONT,15));
        radioButton.setTextFill(Color.WHITE);
        radioButton.setOpacity(1);

        radioButton.setLayoutX(290);
        radioButton.setLayoutY(pos);
        radioButton.setPrefWidth(437);
       // radioButton.setPrefHeight(10);
        radioButton.setOnMouseEntered(e -> radioButton.setCursor(Cursor.HAND));
        radioButton.setOnMouseExited(e -> radioButton.setCursor(Cursor.DEFAULT));
        return radioButton;
    }

    public Label designLabel(String text, int pos) {
        Label label = new Label();
        label.setText(text);
        label.setLayoutX(290);
        label.setLayoutY(pos);
        label.setFont(Font.font(StyleHelper.MAIN_FONT,18));
        label.setTextFill(Paint.valueOf("#FFFFFF"));
        return label;
    }

    public Line designLine(int linePos) {
        Line line = new Line();
        line.setStroke(Paint.valueOf("#474C5F"));
        line.setStartX(290);
        line.setStartY(linePos);
        line.setEndX(750);
        line.setEndY(linePos);
        return line;
    }

    public void countTasks(int rButtonListSize, Label label) {
        String tasksAmount = String.valueOf(rButtonListSize);
        label.setTextFill(Color.WHITE);
        label.setText(tasksAmount);
        label.setLayoutX(590);
        label.setLayoutY(16);
        label.setFont(Font.font(StyleHelper.MAIN_FONT,28));
    }

    public void writeListName(String strListName, Label label) {
        label.setTextFill(Color.WHITE);
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
