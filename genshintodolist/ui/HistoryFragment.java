package com.example.genshintodolist.ui;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.genshintodolist.R;
import com.example.genshintodolist.data.DBManager;
import com.example.genshintodolist.data.DatabaseHelper;
import com.example.genshintodolist.data.ToDoItem;
import com.example.genshintodolist.ui.history.HistoryAdapter;
import com.example.genshintodolist.ui.history.DateItem;
import com.example.genshintodolist.ui.history.ListItem;
import com.example.genshintodolist.ui.history.TaskItem;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

public class HistoryFragment extends Fragment {

    private DBManager dbManager;
    private RecyclerView recyclerView;
    private HistoryAdapter adapter;
    private List<ListItem> itemList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dbManager = new DBManager(getContext());
        dbManager.open();

        Cursor cursor = dbManager.fetchCompletedTasks();
        int taskCount = cursor.getCount();

        itemList = new ArrayList<>();
        adapter = new HistoryAdapter(itemList, dbManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        if(dbManager != null){
            dbManager.close();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadHistoryData();
    }

    private void loadHistoryData(){
        itemList.clear();
        Cursor cursor = dbManager.fetchDeleted();

        int doneTaskCount = 0;

        if (cursor != null) {
            Map<String, List<ToDoItem>> groupedByDate = new LinkedHashMap<>();

            while (cursor.moveToNext()) {
                boolean isDone = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.DONE)) == 1;
                boolean isDeleted = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.IS_DELETED)) == 1;

                if (isDone) {
                    doneTaskCount++;

                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper._ID));
                    String task = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.TITLE));
                    String taskdescription = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.DESCRIPTION));
                    String taskdate = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.DATE));

                    ToDoItem item = new ToDoItem(id, task, taskdescription, true, taskdate);
                    if(!groupedByDate.containsKey(taskdate)){
                        groupedByDate.put(taskdate, new ArrayList<>());
                    }
                    groupedByDate.get(taskdate).add(item);
                }
            }
            cursor.close();
            
            //Add to ItemList To DateItem and TaskItem
            for(String date : groupedByDate.keySet()){
                itemList.add(new DateItem(date));
                for(ToDoItem task : groupedByDate.get(date)){
                    itemList.add(new TaskItem(task));
                }
            }
        }
        adapter.notifyDataSetChanged();

        TextView taskCountText = getView().findViewById(R.id.DoneText);
        if(taskCountText != null) {
            String message = "You have done " + doneTaskCount + (doneTaskCount == 1 ? " task " : " tasks ");
            taskCountText.setText(message);
        }
    }
}