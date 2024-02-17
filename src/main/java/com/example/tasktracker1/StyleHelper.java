package com.example.tasktracker1;

import javafx.scene.Cursor;
import javafx.scene.control.Button;

public class StyleHelper {
    public static final String ON_MOUSE_ENTERED_STYLE = "-fx-background-color: #4682B4;";

    public static final String ON_MOUSE_EXITED_STYLE = "-fx-background-color: #008080;";

    public static final String MAIN_FONT = "American Typewriter Semibold";

    public static final int TASK_BUTTON_INITIAL_POSITION = 70;

    public static final int LIST_BUTTON_INITIAL_POSITION = 42;

    public static final String TRANSPARENT_STYLE = "-fx-background-color: transparent;";

    public static void styleButton(Button button) {
        button.setOpacity(1);
        button.setOnMouseEntered(e -> {
            button.setCursor(Cursor.HAND);
            button.setStyle(StyleHelper.ON_MOUSE_ENTERED_STYLE);
        });

        button.setOnMouseExited(e -> {
            button.setCursor(Cursor.DEFAULT);
            button.setStyle(StyleHelper.ON_MOUSE_EXITED_STYLE);
        });
    }
}
