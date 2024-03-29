package com.example.journalapp;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.HashMap;


public class TasksFragment extends Fragment {
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private ArrayList<TaskItem> taskItemArrayList;
    private Button btn_completeTasks;
    private FloatingActionButton fab_add_task;
    private FrameLayout frameLayout;
    DbHandler db;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);
        db = new DbHandler(getContext());
        taskItemArrayList = db.getActiveTasks();
        fab_add_task = (FloatingActionButton) view.findViewById(R.id.fab_add_task);
        btn_completeTasks= view.findViewById(R.id.btn_completedTasks);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_task);
        TaskAdapter adapter=createAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        CompleteTasksFragment completeTasksFragment = new CompleteTasksFragment();
        btn_completeTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout, completeTasksFragment);
                fragmentTransaction.commit();
            }
        });

        fab_add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddTaskActivity.class));
            }
        });
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        TaskAdapter newAdapter = createAdapter();
        recyclerView.setAdapter(newAdapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    public TaskAdapter createAdapter(){
        taskItemArrayList=db.getActiveTasks();
        adapter = new TaskAdapter(taskItemArrayList, getContext(), new TaskAdapter.ItemClickListener() {
            @Override
            public void onItemClick(TaskItem taskItem) {
                Intent intent = new Intent(getContext(), ViewTaskItem.class);
                int taskID = taskItem.getId();
                Bundle extra = new Bundle();
                extra.putInt("id", taskID);
                intent.putExtras(extra);
                startActivity(intent);
            }

            @Override
            public void onMarkDone(TaskItem taskItem) {
                int taskID = taskItem.getId();
                db.updateTaskStatus(taskID);
                Toast.makeText(getContext(), "Task marked as done", Toast.LENGTH_SHORT).show();
                recyclerView.setAdapter(createAdapter());
            }
        });
        return adapter;
    }
}