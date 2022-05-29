package com.coverteam.pta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.coverteam.pta.data.models.Users;
import com.coverteam.pta.data.providers.FirestoreCollectionName;
import com.coverteam.pta.data.repositorys.UsersRepository;
import com.coverteam.pta.data.repositorys.UsersRepositoryImp;
import com.coverteam.pta.views.signature.SignatureActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";
    ProgressBar progressBar,progressBar0;
    TextView namaprofil,nipprofil,in_nama,in_nip,in_jabatan,in_sisa;
    ImageView fotouser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_profil);

        getUsernameLocal();

        findViewById(R.id.button_logout).setOnClickListener(this);
        findViewById(R.id.button_back).setOnClickListener(this);

        namaprofil = findViewById(R.id.namaprofil);
        nipprofil = findViewById(R.id.nipprofil);
        in_nama = findViewById(R.id.in_nama);
        in_nip = findViewById(R.id.in_nip);
        in_jabatan = findViewById(R.id.in_jabatan);
        in_sisa = findViewById(R.id.in_sisa);
        progressBar = findViewById(R.id.progressbar);
        progressBar0 = findViewById(R.id.progressbar0);
        fotouser = findViewById(R.id.fotouser);

        getInformationFromDB();


        findViewById(R.id.button_signature).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goSignaturePad = new Intent(ProfileActivity.this, SignatureActivity.class);
                startActivity(goSignaturePad);
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_logout:
                goLogout();
                break;
            case R.id.button_back:
                Intent goback = new Intent(ProfileActivity.this, MenuUtamaActivity.class);
                startActivity(goback);
                break;
        }
    }

    private void getInformationFromDB() {

        UsersRepository usersRepository =  new UsersRepositoryImp(Users.class, FirestoreCollectionName.USERS);
        usersRepository.get(username_key_new).addOnCompleteListener(new OnCompleteListener<Users>() {
            @Override
            public void onComplete(@NonNull Task<Users> task) {
                if(task.isSuccessful()){
                    Users localUsers =  task.getResult();

                    //visible validasi from if role admin

                    namaprofil.setText(localUsers.getNama());
                    nipprofil.setText(localUsers.getNip());
                    in_nama.setText(localUsers.getNama());
                    in_nip.setText(localUsers.getNip());
                    in_jabatan.setText(localUsers.getJabatan());
                    in_sisa.setText(localUsers.getJumlahMaximalCutiPertahun().toString());

                    Picasso.with(ProfileActivity.this)
                            .load(localUsers.getFoto())
                            .into(fotouser, new Callback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onError() {
                                    progressBar0.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(),"Gagal Memuat Foto",Toast.LENGTH_SHORT).show();
                                }
                            });
                    progressBar0.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                }else{
                    //if error
                    goLogout();
                }
            }
        });

    }

    private void goLogout(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(username_key,null);
        editor.apply();
        progressBar.setVisibility(View.VISIBLE);
        Intent gologin = new Intent(ProfileActivity.this, LoginActivity.class);
        gologin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(gologin);
        finish();
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }
}
