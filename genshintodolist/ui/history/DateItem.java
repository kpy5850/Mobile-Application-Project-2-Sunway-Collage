package com.example.genshintodolist.ui.history;

public class DateItem extends ListItem{
    private final String date;

    public DateItem(String date){
        this.date = date;
    }

    public String getDate(){
        return  date;
    }

    @Override
    public int getType(){
        return TYPE_DATE;
    }
}
