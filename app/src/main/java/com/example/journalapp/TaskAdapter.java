package com.example.journalapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private ArrayList<TaskItem> taskItemArrayList;
    private ArrayList<TaskItem> taskItemArrayListFull;
    private ItemClickListener mitemClickListener;
    public TaskAdapter(ArrayList<TaskItem> taskItemArrayList, Context context, ItemClickListener itemClickListener) {
        this.taskItemArrayList = taskItemArrayList;
        this.taskItemArrayListFull=new ArrayList<>(taskItemArrayList);
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
        TaskItem taskItem = taskItemArrayList.get(position);
        holder.dueDate.setText(taskItem.getDate());
        holder.taskName.setText(taskItem.getName());
        holder.taskDesc.setText(taskItem.getDescription());

        holder.itemView.setOnClickListener(view -> {
            mitemClickListener.onItemClick(taskItem);
        });

        holder.btnMarkDone.setOnClickListener(view -> {
            mitemClickListener.onMarkDone(taskItem);
        });
    }
    public interface ItemClickListener{
        void onItemClick(TaskItem taskItem);
        void onMarkDone(TaskItem taskItem);
    }
    @Override
    public int getItemCount() {
        return taskItemArrayList == null ? 0 : taskItemArrayList.size();
    }

    public Filter getFilter() {
        return taskFilter;
    }
    private Filter taskFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<TaskItem> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(taskItemArrayListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (TaskItem item : taskItemArrayListFull) {
                    if (item.getDescription().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraints, FilterResults results) {
            taskItemArrayList.clear();
            taskItemArrayList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}
