package com.example.tasktracker1;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class ChangePositionAnimation {

    private TranslateTransition tt;

    public ChangePositionAnimation(Node node) {
        tt = new TranslateTransition(Duration.seconds(0.2), node);
        tt.setFromY(0f);
        tt.setByY(-28f);
        tt.setCycleCount(1);
    }

    public void playAnim() {
        tt.playFromStart();
    }
}
