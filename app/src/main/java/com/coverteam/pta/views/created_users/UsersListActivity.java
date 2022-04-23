package com.coverteam.pta.views.created_users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.coverteam.pta.R;
import com.coverteam.pta.data.models.Role;
import com.coverteam.pta.data.models.Users;
import com.coverteam.pta.tools.Tools;

import java.util.ArrayList;

public class UsersListActivity extends AppCompatActivity {
    private Toolbar toolbar;

    private RecyclerView rcv_users;

    private  AdapterListUser adapterListUser;

    private  IUserListOnTap callbackOnTap = new IUserListOnTap() {
        @Override
        public void onButtonOrderClick(int position) {
            Tools.println("Position : " + position);
        }
    } ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        toolbar = findViewById(R.id.topAppBar);
        rcv_users = findViewById(R.id.rcv_users);


        setSupportActionBar(toolbar);
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
        //back button show
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        initData();
    }

    // init data

    private  void initData(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rcv_users.setLayoutManager(layoutManager);


        //example list users

        ArrayList<Users> users = new ArrayList<>();

        Users ilham = new Users(
                Role.ADMIN,
                "Ilham",
                "1231313213",
                "",
                "08213131313213",
                "12345678",
                "admin_ilham",
                "1",
                "Admin IT",
                12,
                8,
                "Tidak Ada"
        );
        Users angga = new Users(
                Role.PEGAWAI,
                "angga",
                "12312313",
                "",
                "08213131313213",
                "12345678",
                "angga",
                "1",
                "IT",
                3,
                8,
                "Tidak Ada"
        );

        users.add(ilham);
        users.add(angga);


        adapterListUser = new AdapterListUser(this,users,callbackOnTap);
        rcv_users.setAdapter(adapterListUser);




    }





    @Override
    public boolean onSupportNavigateUp() {
       onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_users_toolbar,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        Tools.println(item.getItemId() +"");
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