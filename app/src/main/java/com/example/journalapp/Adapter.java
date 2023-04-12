package com.example.journalapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter {
    TextView itm_subject;
    TextView itm_date;
    ArrayList subject, date;
    Context context;

    public Adapter(Context context, ArrayList subject, ArrayList date) {
        this.context = context;
        this.subject = subject;
        this.date = date;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itm_subject = (TextView) itemView.findViewById(R.id.itm_subject);
            itm_date = (TextView) itemView.findViewById(R.id.itm_date);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entry_list_item, parent, false);
        Adapter.ViewHolder viewHolder = new Adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView subjectView = holder.itemView.findViewById(R.id.itm_subject);
        TextView dateView = holder.itemView.findViewById(R.id.itm_date);
        subjectView.setText((String) subject.get(position));
        dateView.setText((String) date.get(position));
    }

    @Override
    public int getItemCount() {
        return subject.size();
    }
}
