package com.example.journalapp;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.HashMap;


public class TasksFragment extends Fragment {
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private ArrayList<HashMap<String, String>> taskItemArrayList;
    private FloatingActionButton fab_add_task;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);
        DbHandler db = new DbHandler(getContext());
        taskItemArrayList = db.getActiveTasks();
        fab_add_task = (FloatingActionButton) view.findViewById(R.id.fab_add_task);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_task);
        adapter = new TaskAdapter(taskItemArrayList, getContext(), new TaskAdapter.ItemClickListener() {
            @Override
            public void onItemClick(HashMap<String, String> taskItem) {
                Intent intent = new Intent(getContext(), ViewTaskItem.class);
                int taskID = Integer.valueOf(taskItem.get("id"));
                Bundle extra = new Bundle();
                extra.putInt("id", taskID);
                intent.putExtras(extra);
                startActivity(intent);
            }

            @Override
            public void onMarkDone(HashMap<String, String> taskItem) {
                int taskID = Integer.valueOf(taskItem.get("id"));
                db.updateTaskStatus(taskID);
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        fab_add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddTaskActivity.class));
                adapter.notifyDataSetChanged();
            }
        });
        setHasOptionsMenu(true);
        return view;
    }
    public void buildRecyclerView () {
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.beginTransaction().detach(this).commitNow();
        fragmentManager.beginTransaction().attach(this).commitNow();
    }
}