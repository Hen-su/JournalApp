package com.example.journalapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private ArrayList<TaskItem> taskItemArrayList;

    public TaskAdapter(ArrayList<TaskItem> taskItemArrayList, Context context) {
        this.taskItemArrayList = taskItemArrayList;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView dueDate;
        private final TextView taskName;
        private final TextView taskDesc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dueDate = itemView.findViewById(R.id.txt_due_date);
            taskName = itemView.findViewById(R.id.txt_task_name);
            taskDesc = itemView.findViewById(R.id.txt_task_desc);
        }
    }
    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item, parent, false);
        TaskAdapter.ViewHolder viewHolder = new TaskAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder holder, int position) {
        TaskItem taskItem = taskItemArrayList.get(position);
        holder.dueDate.setText(taskItem.getDate());
        holder.taskName.setText(taskItem.getName());
        holder.taskDesc.setText(taskItem.getDescription());
    }

    @Override
    public int getItemCount() {
        return taskItemArrayList == null ? 0 : taskItemArrayList.size();
    }
}
