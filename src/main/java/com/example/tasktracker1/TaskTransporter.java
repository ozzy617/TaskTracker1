package com.example.tasktracker1;

public class TaskTransporter {
    private static String task;
    private static TaskListener listener;
    private TaskListener l;
    public void setTaskListener(TaskListener inputListener) {
        listener = inputListener;
    }
    public static  void taskGetter(String task1){
        task = task1;
        listener.getTaskText(task);
        //System.out.println(task + " 11");
    }
}
