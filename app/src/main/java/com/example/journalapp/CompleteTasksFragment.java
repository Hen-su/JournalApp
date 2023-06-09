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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CompleteTasksFragment extends Fragment {

    private RecyclerView recyclerView;
    private CompleteTaskAdapter adapter;
    private ArrayList<TaskItem> taskItemArrayList;
    private Button btn_activeTasks, btn_delete;
    DbHandler db;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_complete_tasks, container, false);
        db = new DbHandler(getContext());
        taskItemArrayList = db.getCompleteTasks();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_completetask);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(createAdapter());

        TasksFragment tasksFragment = new TasksFragment();
        btn_activeTasks=view.findViewById(R.id.btn_ActiveTasks);
        btn_activeTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout, tasksFragment);
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerView.setAdapter(createAdapter());
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

    public CompleteTaskAdapter createAdapter(){
        taskItemArrayList=db.getCompleteTasks();
        adapter = new CompleteTaskAdapter(taskItemArrayList, getContext(), new CompleteTaskAdapter.ItemClickListener() {
            @Override
            public void onItemClick(TaskItem taskItem) {
                Intent intent = new Intent(getContext(), ViewCompleteTaskItem.class);
                int taskID = taskItem.getId();
                Bundle extra = new Bundle();
                extra.putInt("id", taskID);
                intent.putExtras(extra);
                startActivity(intent);
            }

            @Override
            public void onDeleteTask(TaskItem taskItem) {
                int taskID=taskItem.getId();
                db.deleteTask(taskID);
                recyclerView.setAdapter(createAdapter());
            }
        });
        return adapter;
    }
}