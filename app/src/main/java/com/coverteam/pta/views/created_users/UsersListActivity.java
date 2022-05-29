package com.coverteam.pta.views.created_users;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.coverteam.pta.R;
import com.coverteam.pta.data.models.Users;
import com.coverteam.pta.data.providers.FirestoreCollectionName;
import com.coverteam.pta.data.repositorys.UsersRepository;
import com.coverteam.pta.data.repositorys.UsersRepositoryImp;
import com.coverteam.pta.tools.Tools;
import com.coverteam.pta.views.created_users.detail_user.DetailUsersActivity;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UsersListActivity extends AppCompatActivity {
    private Toolbar toolbar;

    private RecyclerView rcv_users;
    ArrayList<Users> users = new ArrayList<>();
    private  AdapterListUser adapterListUser;

    private  IUserListOnTap callbackOnTap = new IUserListOnTap() {
        @Override
        public void onButtonOrderClick(int position) {
            Tools.println("Position : " + position);

            Intent goDetail = new Intent(UsersListActivity.this, DetailUsersActivity.class);
            goDetail.putExtra("id",users.get(position).getNip());
            startActivity(goDetail);
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

        ;
        adapterListUser = new AdapterListUser(this,users,callbackOnTap);
        rcv_users.setAdapter(adapterListUser);

        UsersRepository usersRepository = new UsersRepositoryImp(Users.class, FirestoreCollectionName.USERS);
        usersRepository.documentCollection().orderBy("timeStamp", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("Listen Firebase", "Listen failed.", e);
                    return;
                }

                users.clear();

                List<Users> listUsers = new ArrayList<>();
                for (QueryDocumentSnapshot doc : value) {

                    Users user = doc.toObject(Users.class);
                    listUsers.add(user);
                }
                users.clear();
                users.addAll(listUsers);
                adapterListUser.notifyDataSetChanged();
                Log.d("Listen Firebase", " " + listUsers.size());
            }
        });








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