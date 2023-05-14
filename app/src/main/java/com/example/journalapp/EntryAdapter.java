package com.example.journalapp;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import java.util.ArrayList;
import java.util.List;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.ViewHolder> {

    private ArrayList<EntryItem> EntryItemArrayList;
    private ArrayList<EntryItem> EntryItemArrayListFull;

    public EntryAdapter(ArrayList<EntryItem> entryItemArrayList, Context context) {
        this.EntryItemArrayList = entryItemArrayList;
        this.EntryItemArrayListFull = new ArrayList<>(entryItemArrayList);
    }

    @NonNull
    @Override
    public EntryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("ResourceType") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entry_list_item, parent, false);
        EntryAdapter.ViewHolder viewHolder = new EntryAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EntryAdapter.ViewHolder holder, int position) {
        EntryItem entryItem = EntryItemArrayList.get(position);
        holder.date.setText(entryItem.getDate());
        holder.subject.setText(entryItem.getSubject());
        holder.entry.setText(entryItem.getEntry());
    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return EntryItemArrayList == null ? 0 : EntryItemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView date;
        private final TextView subject;
        private final TextView entry;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.itm_date);
            subject = itemView.findViewById(R.id.itm_subject);
            entry = itemView.findViewById(R.id.itm_entry);
        }
    }
    public Filter getFilter() {
        return entryFilter;
    }
    private Filter entryFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<EntryItem> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(EntryItemArrayListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (EntryItem item : EntryItemArrayListFull) {
                    if (item.getEntry().toLowerCase().contains(filterPattern)) {
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
            EntryItemArrayList.clear();
            EntryItemArrayList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}
