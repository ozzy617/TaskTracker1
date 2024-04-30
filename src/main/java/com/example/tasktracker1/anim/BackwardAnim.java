package com.example.tasktracker1.anim;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class BackwardAnim {
    private TranslateTransition translateTransition;

    public BackwardAnim(Node node){
        translateTransition = new TranslateTransition(Duration.millis(1),node);
        translateTransition.setFromY(0f);
        translateTransition.setByY(0f);
        translateTransition.setCycleCount(1);
    }

    public void playAnim() {
        translateTransition.playFromStart();
    }
}
