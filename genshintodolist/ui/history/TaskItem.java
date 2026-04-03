package com.example.genshintodolist.ui.history;

import com.example.genshintodolist.data.ToDoItem;

public class TaskItem extends ListItem {
    private final ToDoItem task;

    public TaskItem(ToDoItem task){
        this.task = task;
    }

    public ToDoItem getTask(){
        return task;
    }

    @Override
    public int getType(){
        return TYPE_TASK;
    }
}
