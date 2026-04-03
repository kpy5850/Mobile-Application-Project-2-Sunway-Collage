package com.example.genshintodolist.data;

public class ToDoItem {
    private int id;
    private String task;
    private boolean done;
    private String date;
    private String description;


    public ToDoItem(int id, String task, String description, boolean done, String date){
        this.id = id;
        this.task = task;
        this.description = description;
        this.done = done;
        this.date = date;
    }

    public int getID(){
        return id;
    }
    public String getTask(){
        return task;
    }
    public void setTask(String task){
        this.task = task;
    }
    public boolean isDone() { return done; }
    public void setDone(boolean done) { this.done = done; }
    public void setDescription(String taskdescription){ description = taskdescription; }
    public String getDescription(){ return description; }
    public String getDate() { return date; }
    public void setDate(String taskdate) { date = taskdate; }
}
