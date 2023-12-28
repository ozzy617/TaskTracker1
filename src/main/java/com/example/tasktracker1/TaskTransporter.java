package com.example.tasktracker1;

public class TaskTransporter {
    private static String task;
    private static TaskListener listener;

    public void setTaskListener(TaskListener inputListener) {
        listener = inputListener;
    }

    public static void getTask(String task1) {
        task = task1;
        listener.getTaskText(task);
    }
}
