package com.example.journalapp;

import static android.app.Activity.RESULT_OK;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
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
import java.util.HashMap;

public class EntryFragment extends Fragment {

    private RecyclerView recyclerView;

    private EntryAdapter adapter;
    private ArrayList<HashMap<String, String>> entryItemArrayList;
    FloatingActionButton fab_add;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entry, container, false);
        fab_add = (FloatingActionButton) view.findViewById(R.id.fab_add);
        DbHandler db = new DbHandler(getContext());
        entryItemArrayList = db.getAllEntries();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        adapter = new EntryAdapter(entryItemArrayList, getContext(), new EntryAdapter.ItemClickListener() {
            @Override
            public void onItemClick(HashMap<String, String> entryItem) {
                Intent intent = new Intent(getContext(), ViewEntryItem.class);
                int entryID = Integer.valueOf(entryItem.get("id"));
                Bundle extra = new Bundle();
                extra.putInt("id", entryID);
                intent.putExtras(extra);
                startActivity(intent);
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddEntryActivity.class));

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


    public void buildRecyclerView () {
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.beginTransaction().detach(this).commitNow();
        fragmentManager.beginTransaction().attach(this).commitNow();
    }
}