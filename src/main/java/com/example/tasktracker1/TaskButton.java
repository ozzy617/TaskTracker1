package com.example.tasktracker1;

import javafx.application.Application;
import javafx.scene.control.RadioButton;
import javafx.scene.text.Font;

public class TaskButton {
    RadioButton button;
    public void buttonCopy(RadioButton b){
        button = b;
        buttonDesigner(button);
    }
    private void buttonDesigner(RadioButton b){
        b.setFont(Font.font("American Typewriter Semibold"));
        b.setPrefWidth(150);
        b.setPrefHeight(40);
    }
}
