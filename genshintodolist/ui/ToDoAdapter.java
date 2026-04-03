package com.example.genshintodolist.ui;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.graphics.Paint;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.genshintodolist.R;
import com.example.genshintodolist.data.DBManager;
import com.example.genshintodolist.data.ToDoItem;
import com.example.genshintodolist.util.NotificationHelper;

import java.util.List;


public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder> {

    private final List<ToDoItem> itemList;
    private final DBManager dbManager;

    //View Holder

    public ToDoAdapter(List<ToDoItem> itemList, DBManager dbManager) {
        this.itemList = itemList;
        this.dbManager = dbManager;
    }

    public static class ToDoViewHolder extends RecyclerView.ViewHolder{
        CheckBox checkDone;
        TextView taskText;
        TextView taskDescription;
        TextView taskDate;

        public ToDoViewHolder(View itemView){
            super(itemView);
            checkDone = itemView.findViewById(R.id.checkDone);
            taskText = itemView.findViewById(R.id.taskText);
            taskDescription = itemView.findViewById(R.id.taskDescription);
            taskDate = itemView.findViewById(R.id.taskDate);
        }
    }

    //OnCreate
    @NonNull
    @Override
    public ToDoViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item_layout, parent, false);
        return new ToDoViewHolder(view);
    }

    //OnBind
    @Override
    public void onBindViewHolder(@NonNull ToDoViewHolder holder, int position){
        ToDoItem item = itemList.get(position);
        //setting checkbox
        holder.checkDone.setOnCheckedChangeListener(null);
        holder.checkDone.setChecked(item.isDone());

        //setting text
        holder.taskText.setText(item.getTask());

        //setting cross text
        holder.taskText.setPaintFlags(
                item.isDone()
                        ? holder.taskText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG
                        : holder.taskText.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG
        );

        //setting cross background & text color
        holder.itemView.setBackgroundColor(Color.parseColor("#DDDDDD"));
        holder.itemView.setAlpha(item.isDone() ? 0.4f : 0.7f);

        //setting description
        holder.taskDescription.setText(item.getDescription());

        //setting date
        holder.taskDate.setText(item.getDate());

        holder.checkDone.setOnCheckedChangeListener((CompoundButton button, boolean isChecked) -> {
            item.setDone(isChecked);
            dbManager.updateDone(item.getID(), isChecked);
            notifyItemChanged(position);

            //Update notify
            NotificationHelper.updateTodayPinnedNotification(holder.itemView.getContext(), dbManager);
        });

        //setting onclick when DELETE
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ModifyList.class);
            intent.putExtra("id", item.getID());
            intent.putExtra("title", item.getTask());
            intent.putExtra("description", item.getDescription());
            intent.putExtra("date", item.getDate());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount(){
        return itemList.size();
    }
}

