package com.example.tasktracker1;

public class TaskAndListNameTransporter {
    private static String task;
    private static TaskAndListListener taskListener;

    private static TaskAndListListener listNameListener;

    public void setTaskListener(TaskAndListListener inputListener) {
        taskListener = inputListener;
    }

    public void setListNameListener(TaskAndListListener inputListener) {
        listNameListener = inputListener;
    }

    public void getListName(String listName) {
        listNameListener.getStringText(listName);
    }
    public void getTask(String task) {
//        task = task1;
        taskListener.getStringText(task);
    }
}
