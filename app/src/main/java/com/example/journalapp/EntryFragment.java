package com.example.journalapp;

import static android.app.Activity.RESULT_OK;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class EntryFragment extends Fragment {

    private RecyclerView recyclerView;

    private EntryAdapter adapter;
    private ArrayList<EntryItem> entryItemArrayList = new ArrayList<EntryItem>();
    FloatingActionButton fab_add;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entry, container, false);
        fab_add = (FloatingActionButton) view.findViewById(R.id.fab_add);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        adapter = new EntryAdapter(entryItemArrayList, getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity(), AddEntryActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        setHasOptionsMenu(true);
        return view;
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

    public static final int REQUEST_CODE = 1;
    //Gets data from user input to create EntryItem object
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            String newSubject = data.getStringExtra("subject");
            String newEntry = data.getStringExtra("entry");
            String newDate = data.getStringExtra("formattedDate");

            buildRecyclerView(newSubject, newDate, newEntry);
            Toast.makeText(getContext(), "Success", Toast.LENGTH_LONG).show();
        }
    }
    public void buildRecyclerView (String newSubject, String newDate, String newEntry) {
        entryItemArrayList.add(new EntryItem(newDate, newSubject, newEntry));
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.beginTransaction().detach(this).commitNow();
        fragmentManager.beginTransaction().attach(this).commitNow();
    }
}