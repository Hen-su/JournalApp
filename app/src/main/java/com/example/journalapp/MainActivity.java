package com.example.journalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    TabItem entryTab, taskTab;
    FrameLayout frameLayout;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String name = getIntent().getStringExtra("name");
        setTitle("Welcome, "+ name);
        tabLayout = findViewById(R.id.TabLayout);
        frameLayout = findViewById(R.id.framelayout);
        entryTab = findViewById(R.id.EntryTab);
        taskTab = findViewById(R.id.TaskTab);
        EntryFragment entryFragment = new EntryFragment();
        TasksFragment tasksFragment = new TasksFragment();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabPosition = tab.getPosition();
                if(tabPosition == 0) {
                    replaceFragment(entryFragment);
                }
                else if (tabPosition == 1) {
                    replaceFragment(tasksFragment);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int tabPosition = tab.getPosition();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                int tabPosition = tab.getPosition();
            }
        });
        TabLayout.Tab tab = tabLayout.getTabAt(0);  // which you want to select.
        tab.select();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnu_logout:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();
    }
}