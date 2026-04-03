package com.example.genshintodolist.ui.history;

public abstract class ListItem {
    public static final int TYPE_DATE = 0;
    public static final int TYPE_TASK = 1;

    abstract public int getType();
}
