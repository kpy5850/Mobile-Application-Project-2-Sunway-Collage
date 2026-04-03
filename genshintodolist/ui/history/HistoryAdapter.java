package com.example.genshintodolist.ui.history;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.genshintodolist.R;
import com.example.genshintodolist.data.DBManager;
import com.example.genshintodolist.data.ToDoItem;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<ListItem> itemList;
    private final DBManager dbManager;

    public HistoryAdapter(List<ListItem> itemList, DBManager dbManager){
        this.itemList = itemList;
        this.dbManager = dbManager;
    }

    @Override
    public int getItemViewType(int position){
        return itemList.get(position).getType();
    }

    @Override
    public int getItemCount(){
        return itemList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if(viewType == ListItem.TYPE_DATE){
            View view = inflater.inflate(R.layout.item_history_date, parent, false);
            return new DateViewHolder(view);
        } else{
            View view = inflater.inflate(R.layout.todo_item_layout, parent, false);
            return new TaskViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position){
        ListItem item = itemList.get(position);
        if(item.getType() == ListItem.TYPE_DATE){
            ((DateViewHolder) holder).bind((DateItem) item);
        } else{
            ((TaskViewHolder) holder).bind(((TaskItem) item).getTask());
        }
    }

    //ViewHolder Date
    static class DateViewHolder extends RecyclerView.ViewHolder{
        private final TextView txtDate;

        public DateViewHolder(View itemView){
            super(itemView);
            txtDate = itemView.findViewById(R.id.txtHistoryDate);
        }

        public void bind(DateItem item){
            txtDate.setText(item.getDate());
        }
    }

    //ViewHolder Task
    static class TaskViewHolder extends RecyclerView.ViewHolder{
        private final TextView taskText;
        private final TextView taskDescription;
        private final TextView taskDate;

        public TaskViewHolder(View itemView){
            super(itemView);
            taskText = itemView.findViewById(R.id.taskText);
            taskDescription = itemView.findViewById(R.id.taskDescription);
            taskDate = itemView.findViewById(R.id.taskDate);
        }

        public void bind(ToDoItem task){
            taskText.setText(task.getTask());
            taskDescription.setText(task.getDescription());
            taskDate.setText(task.getDate());

            taskText.setPaintFlags(task.isDone()
                    ? taskText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG
                    : taskText.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);

            taskText.setAlpha(task.isDone() ? 0.4f : 1f);
        }
    }
}

