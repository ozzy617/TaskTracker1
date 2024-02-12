package com.example.tasktracker1;

public class StringOperator {
    public boolean checkStrExistance(String taskStr) { // СДЕЛАТЬ ИНТЕРФЕЙСОМ ИЛИ НЕТ
        String string = taskStr.replaceAll(" ", "");
        return !string.isEmpty();
    }
}
