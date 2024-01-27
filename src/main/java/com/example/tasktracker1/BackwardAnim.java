package com.example.tasktracker1;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class BackwardAnim {
    private TranslateTransition tt;
    private Node node;
    private int p;

    public BackwardAnim(Node node, int pos){
        tt = new TranslateTransition(Duration.millis(1),node);
        tt.setFromY(0f);
        tt.setByY(0f);
        tt.setCycleCount(1);
    }


    public void playAnim() {
        tt.playFromStart();
    }
}
