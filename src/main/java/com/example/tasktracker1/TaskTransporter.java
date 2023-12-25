package com.example.tasktracker1;

public class TaskTransporter {

    private static TaskListener listener;

    public void setTaskListener(TaskListener inputListener) {
        listener = inputListener;
    }

    public static void getTask(String task) {
        listener.getTaskText(task);
    }
}
