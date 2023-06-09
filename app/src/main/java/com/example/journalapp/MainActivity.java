package com.example.journalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.HashMap;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    TabItem entryTab, taskTab;
    FrameLayout frameLayout;
    Session session;
    HashMap<String, String> user;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myapp", 0);
        SharedPreferences.Editor editor = pref.edit();
        String name = pref.getString("name", null);
        setTitle("Welcome, "+ name);
        tabLayout = findViewById(R.id.TabLayout);
        frameLayout = findViewById(R.id.framelayout);
        entryTab = findViewById(R.id.EntryTab);
        taskTab = findViewById(R.id.TaskTab);
        EntryFragment entryFragment = new EntryFragment();
        TasksFragment tasksFragment = new TasksFragment();
        session = new Session(this);
        if(!session.loggedIn()){
            logout();
        }
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
        replaceFragment(entryFragment);
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
                logout();
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

    private void logout(){
        session.setLoggedIn(false);
        session.clearEditor();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }
}