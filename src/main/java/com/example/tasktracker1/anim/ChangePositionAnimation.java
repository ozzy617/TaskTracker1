package com.example.tasktracker1.anim;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class ChangePositionAnimation {

    private TranslateTransition translateTransition;

    public ChangePositionAnimation(Node node) {
        translateTransition = new TranslateTransition(Duration.seconds(0.2),node);
        translateTransition.setFromY(0f);
        translateTransition.setByY(-28f);
        translateTransition.setCycleCount(1);
    }


    public void playAnim() {
        translateTransition.playFromStart();
    }
}
