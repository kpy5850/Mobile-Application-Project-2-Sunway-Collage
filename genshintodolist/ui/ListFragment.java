package com.example.genshintodolist.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.genshintodolist.R;
import com.example.genshintodolist.data.DBManager;
import com.example.genshintodolist.data.ToDoItem;
import com.example.genshintodolist.data.DatabaseHelper;
import com.example.genshintodolist.ui.ToDoAdapter;
import com.example.genshintodolist.ui.AddList;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private DBManager dbManager;
    private RecyclerView recyclerView;
    private ToDoAdapter adapter;
    private List<ToDoItem> itemList;
    private ImageView bgGif;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        bgGif = view.findViewById(R.id.bgGif);

        //RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewToDo);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dbManager = new DBManager(getContext());
        dbManager.open();

        itemList = new ArrayList<>();
        adapter = new ToDoAdapter(itemList, dbManager);
        recyclerView.setAdapter(adapter);

        // Swipe to delete
        ItemTouchHelper.SimpleCallback swipeCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                ToDoItem item = itemList.get(position);

                if (direction == ItemTouchHelper.LEFT) {
                    dbManager.softDelete(item.getID());
                    itemList.remove(position);
                    adapter.notifyItemRemoved(position);
                } else {
                    adapter.notifyItemChanged(position);
                }
            }
        };

        new ItemTouchHelper(swipeCallback).attachToRecyclerView(recyclerView);

        // Add button
        Button btnAdd = view.findViewById(R.id.btnAddTask);
        btnAdd.setOnClickListener(v ->
                startActivity(new Intent(getContext(), AddList.class)));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("ListFragment", "onResume called");
        loadWallpaper();
        refreshData();
    }

    //Background
    private void loadWallpaper() {
        SharedPreferences prefs = requireContext().getSharedPreferences("genshin_prefs", Context.MODE_PRIVATE);
        int rawResId = prefs.getInt("selected_wallpaper_resid", R.raw.nilou);

        Glide.with(this)
                .asGif()
                .load("android.resource://" + requireContext().getPackageName() + "/" + rawResId)
                .transition(DrawableTransitionOptions.withCrossFade(300))
                .into(bgGif);
    }

    private void refreshData() {
        itemList.clear();
        Cursor cursor = dbManager.fetchActive();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper._ID));
                String task = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.TITLE));
                String taskdescription = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.DESCRIPTION));
                boolean done = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.DONE)) == 1;
                String taskdate = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.DATE));
                itemList.add(new ToDoItem(id, task, taskdescription, done, taskdate));
            }
            cursor.close();
        }
        adapter.notifyDataSetChanged();
    }
}