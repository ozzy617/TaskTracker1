package com.example.tasktracker1;

public class StringOperator {
    public boolean chekStrExistance(String taskStr) {
        String string = taskStr.replaceAll(" ", "");
        return !string.isEmpty();
    }
}
