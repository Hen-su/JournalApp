package com.example.journalapp;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EntryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntryFragment extends Fragment {
    FloatingActionButton fab_add;
    RecyclerView recyclerView;
    ArrayList subject = new ArrayList<>(Arrays.asList());
    ArrayList entry = new ArrayList<>(Arrays.asList());
    ArrayList date = new ArrayList<>(Arrays.asList());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_entry, container, false);
        fab_add = (FloatingActionButton) view.findViewById(R.id.fab_add);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        Adapter adapter = new Adapter(EntryFragment.this.getContext(), subject, date);
        recyclerView.setAdapter(adapter);

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddEntryActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        return view;

    }
    public static final int REQUEST_CODE = 1;
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            String newSubject = data.getStringExtra("subject");
            String newEntry = data.getStringExtra("entry");
            String newDate = data.getStringExtra("formattedDate");

            subject.add(newSubject);
            entry.add(newEntry);
            date.add(newDate);

            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction().detach(this).commitNow();
            fragmentManager.beginTransaction().attach(this).commitNow();

        }
    }
}