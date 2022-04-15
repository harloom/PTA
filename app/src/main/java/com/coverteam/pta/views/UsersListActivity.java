package com.coverteam.pta.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.coverteam.pta.ASplashScreen;
import com.coverteam.pta.R;
import com.coverteam.pta.b_started;

public class UsersListActivity extends AppCompatActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);

        //back button show
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_users_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.to_add_user:
                // go activity from create users
                Intent goFormUser = new Intent(UsersListActivity.this, UsersCreateActivity.class);
                startActivity(goFormUser);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}