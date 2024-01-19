package com.example.tasktracker1;

public class StringOperator {
    public boolean chekStrexistance(String taskStr) { // СДЕЛАТЬ ИНТЕРФЕЙСОМ ИЛИ НЕТ
        String string = taskStr.replaceAll(" ", "");
        if (string.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
