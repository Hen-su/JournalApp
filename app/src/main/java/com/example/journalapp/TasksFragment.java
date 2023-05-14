package com.example.journalapp;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TasksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TasksFragment extends Fragment {
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private ArrayList<TaskItem> taskItemArrayList = new ArrayList<TaskItem>();
    private FloatingActionButton fab_add_task;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);
        fab_add_task = (FloatingActionButton) view.findViewById(R.id.fab_add_task);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_task);
        adapter = new TaskAdapter(taskItemArrayList, getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        fab_add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddTaskActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        setHasOptionsMenu(true);
        return view;
    }
    public static final int REQUEST_CODE = 1;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            String newDate = data.getStringExtra("date");
            String newName = data.getStringExtra("name");
            String newDesc = data.getStringExtra("desc");
            /*
            DateTimeFormatter stringDate = DateTimeFormatter.ofPattern("dd/mm/yyyy");
            DateTimeFormatter dateString = DateTimeFormatter.ofPattern("E, dd MMM yyyy");
            LocalDate dateObj = LocalDate.parse(newDate, stringDate);
            String formattedDate = dateObj.format(dateString); */
            buildRecyclerView(newDate, newName, newDesc, false);
        }
    }
    public void buildRecyclerView (String newDate, String newName, String newDesc, boolean status) {
        taskItemArrayList.add(new TaskItem(newDate, newName, newDesc, status));
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.beginTransaction().detach(this).commitNow();
        fragmentManager.beginTransaction().attach(this).commitNow();
    }
}