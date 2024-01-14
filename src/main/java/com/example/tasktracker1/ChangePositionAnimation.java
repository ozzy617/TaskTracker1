package com.example.tasktracker1;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class ChangePositionAnimation {
    private TranslateTransition tt;
    private Node node;
    private int p;

    public ChangePositionAnimation(Node node) {
        tt = new TranslateTransition(Duration.seconds(0.2),node);
        //tt.setFromX(0f);
        tt.setFromY(0f);
        tt.setByY(-28f);
        tt.setCycleCount(1);
        //ЗДЕСЬ БУДЕТ ДОБАВЛЕНА АНИМАЦИЯ
    }


    public void playAnim() {
        tt.playFromStart();
//        tt = new TranslateTransition(Duration.millis(1),node);
//        tt.setByY(28);
    }

    public void s() {

    }
}
