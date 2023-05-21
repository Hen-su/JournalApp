package com.example.journalapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private ArrayList<HashMap<String, String>> taskItemArrayList;
    private ItemClickListener mitemClickListener;
    public TaskAdapter(ArrayList<HashMap<String, String>> taskItemArrayList, Context context, ItemClickListener itemClickListener) {
        this.taskItemArrayList = taskItemArrayList;
        this.mitemClickListener = itemClickListener;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView dueDate;
        private final TextView taskName;
        private final TextView taskDesc;
        Button btnMarkDone;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dueDate = itemView.findViewById(R.id.txt_due_date);
            taskName = itemView.findViewById(R.id.txt_task_name);
            taskDesc = itemView.findViewById(R.id.txt_task_desc);
            btnMarkDone = itemView.findViewById(R.id.btn_markdone);
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
        HashMap<String, String> taskItem = taskItemArrayList.get(position);
        holder.dueDate.setText(taskItem.get("duedate"));
        holder.taskName.setText(taskItem.get("subject"));
        holder.taskDesc.setText(taskItem.get("description"));

        holder.itemView.setOnClickListener(view -> {
            mitemClickListener.onItemClick(taskItem);
        });

        holder.btnMarkDone.setOnClickListener(view -> {
            mitemClickListener.onMarkDone(taskItem);
        });
    }
    public interface ItemClickListener{
        void onItemClick(HashMap<String, String> taskItem);
        void onMarkDone(HashMap<String, String> taskItem);
    }
    @Override
    public int getItemCount() {
        return taskItemArrayList == null ? 0 : taskItemArrayList.size();
    }
}
