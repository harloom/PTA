package com.coverteam.pta.views.created_users.detail_user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.coverteam.pta.R;
import com.coverteam.pta.data.models.Users;
import com.coverteam.pta.data.providers.FirestoreCollectionName;
import com.coverteam.pta.data.repositorys.UsersRepository;
import com.coverteam.pta.data.repositorys.UsersRepositoryImp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class DetailUsersActivity extends AppCompatActivity {
    ProgressBar progressBar,progressBar0;
    TextView namaprofil,nipprofil,in_nama,in_nip,in_jabatan,in_sisa;
    ImageView fotouser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_users);
        findViewById(R.id.button_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        namaprofil = findViewById(R.id.namaprofil);
        nipprofil = findViewById(R.id.nipprofil);
        in_nama = findViewById(R.id.in_nama);
        in_nip = findViewById(R.id.in_nip);
        in_jabatan = findViewById(R.id.in_jabatan);
        in_sisa = findViewById(R.id.in_sisa);
        progressBar = findViewById(R.id.progressbar);
        progressBar0 = findViewById(R.id.progressbar0);
        fotouser = findViewById(R.id.fotouser);

        String id = getIntent().getStringExtra("id");
        if(id == null){
            finish();
        }else{
            getInformationFromDB(id);
        }
    }

    private void getInformationFromDB(String id) {

        UsersRepository usersRepository =  new UsersRepositoryImp(Users.class, FirestoreCollectionName.USERS);
        usersRepository.get(id).addOnCompleteListener(new OnCompleteListener<Users>() {
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

                    Picasso.with(DetailUsersActivity.this)
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
                }
            }
        });


    }
}