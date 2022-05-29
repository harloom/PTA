package com.coverteam.pta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.coverteam.pta.data.models.Role;
import com.coverteam.pta.data.models.Users;
import com.coverteam.pta.data.providers.FirestoreCollectionName;
import com.coverteam.pta.data.repositorys.DocumentCutiRepositoryImp;
import com.coverteam.pta.data.repositorys.UploadCloudStorage;
import com.coverteam.pta.data.repositorys.UsersRepository;
import com.coverteam.pta.data.repositorys.UsersRepositoryImp;
import com.coverteam.pta.tools.CustomMask;
import com.coverteam.pta.tools.PicassoImageLoader;
import com.coverteam.pta.views.created_users.UsersListActivity;
import com.coverteam.pta.views.from_cuti.FromCutiView;
import com.coverteam.pta.views.riwayat_cuti.RiwayatCutiView;
import com.coverteam.pta.views.validasi.admin.ValidasiByAdminView;
import com.coverteam.pta.views.validasi.atasan.ValidasiByAtasanView;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import lv.chi.photopicker.ChiliPhotoPicker;
import lv.chi.photopicker.PhotoPickerFragment;
import ru.nikartm.support.ImageBadgeView;

public class MenuUtamaActivity extends AppCompatActivity implements View.OnClickListener, PhotoPickerFragment.Callback {

    LinearLayout lnr_validasi, lnr_add_user;
    TextView nama, nip, namahorizon1, namahorizon2, kethorizon1, kethorizon2;
    ProgressBar progressBar, progressbar2, progressbar3, progressBar0;
    ImageView gambarhorizon, gambarhorizon2, fotouser;

    DatabaseReference reference;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";
    String id1 = "", id2 = "";

    TextView pgrsIndicator;

    // user data
    private Users users;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_menu_utama);

        //initial chilPhoto
        ChiliPhotoPicker.INSTANCE.init(new PicassoImageLoader(), "lv.chi.sample.fileprovider");

        getUsernameLocal();

        pgrsIndicator = findViewById(R.id.indicator_progress);

        findViewById(R.id.ikon_cuti).setOnClickListener(this);
        findViewById(R.id.ikon_profil).setOnClickListener(this);
        findViewById(R.id.ikon_panduan).setOnClickListener(this);
        findViewById(R.id.ikon_riwayat).setOnClickListener(this);
        findViewById(R.id.ikon_validasi).setOnClickListener(this);
        findViewById(R.id.agenda1).setOnClickListener(this);
        findViewById(R.id.agenda2).setOnClickListener(this);
        findViewById(R.id.circlefoto).setOnClickListener(this);

        findViewById(R.id.icon_add_user).setOnClickListener(this);

        nama = findViewById(R.id.namamenu);
        nip = findViewById(R.id.nipmenu);
        progressBar = findViewById(R.id.progressbar);
        progressbar2 = findViewById(R.id.progressbar2);
        progressbar3 = findViewById(R.id.progressbar3);
        progressBar0 = findViewById(R.id.progressbar0);

        namahorizon1 = findViewById(R.id.txhorizon);
        namahorizon2 = findViewById(R.id.txhorizon2);
        kethorizon1 = findViewById(R.id.txhorizonket);
        kethorizon2 = findViewById(R.id.txhorizonket2);
        gambarhorizon = findViewById(R.id.gambarhorizon);
        gambarhorizon2 = findViewById(R.id.gambarhorizon2);
        fotouser = findViewById(R.id.fotouserhome);


        //validation
        lnr_validasi = findViewById(R.id.ikon_validasi);
        lnr_add_user = findViewById(R.id.icon_add_user);


        getInformationFromDB();
        getAgendaNew();
    }

    // fungsi tombol ambil libaray
    private void pickGallery() {
        PhotoPickerFragment photoPickerFragment = PhotoPickerFragment.
                Companion.newInstance(false, true, 1, R.style.ChiliPhotoPicker_Dark);
        photoPickerFragment.show(getSupportFragmentManager(), "picker");

    }

    // fungsi dijalankan setelah ambil gambar di gallery / foto
    @Override
    public void onImagesPicked(@NotNull ArrayList<Uri> arrayList) {
        pgrsIndicator.setVisibility(View.VISIBLE);
        Log.d("print", arrayList.toString());
        if (arrayList.size() > 0) {
            UploadCloudStorage cloud = new UploadCloudStorage(users.getNip());
            cloud.uploadImage(arrayList.get(0))
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();

                            String teks = progress + "%";
                            pgrsIndicator.setText(teks);
                        }
                    })

                    .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            // Continue with the task to get the download URL
                            return cloud.getUserRef().getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        pgrsIndicator.setVisibility(View.GONE);
                        Uri downloadUri = task.getResult();
                        updateAvatar(downloadUri.toString());
                        Picasso.with(MenuUtamaActivity.this)
                                .load(downloadUri)
                                .into(fotouser, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError() {
                                        progressBar0.setVisibility(View.GONE);
                                        Toast.makeText(getApplicationContext(), "Gagal Memuat Foto", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });
        }
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.circlefoto:
                pickGallery();
                break;
            case R.id.icon_add_user:
                Intent goManagementUser = new Intent(MenuUtamaActivity.this, UsersListActivity.class);
                startActivity(goManagementUser);
                break;

            case R.id.ikon_cuti:
                Intent gocuti = new Intent(MenuUtamaActivity.this, FromCutiView.class);
                startActivity(gocuti);
                break;
            case R.id.ikon_profil:
                Intent goprofil = new Intent(MenuUtamaActivity.this, ProfileActivity.class);
                startActivity(goprofil);
                break;
            case R.id.ikon_panduan:
                Intent gopanduan = new Intent(MenuUtamaActivity.this, PanduanActivity.class);
                startActivity(gopanduan);
                break;
            case R.id.ikon_riwayat:
                Intent goriwayat = new Intent(MenuUtamaActivity.this, RiwayatCutiView.class);
                startActivity(goriwayat);
                break;
            case R.id.ikon_validasi:

                if (users.getRole().equals(Role.ADMIN)) {
                    Intent govalidasiAdmin = new Intent(MenuUtamaActivity.this, ValidasiByAdminView.class);
                    startActivity(govalidasiAdmin);
                } else {
                    Intent govalidasiAtasan = new Intent(MenuUtamaActivity.this, ValidasiByAtasanView.class);
                    startActivity(govalidasiAtasan);
                }


                break;
            case R.id.agenda1:
                if (id1.equals("")) {
                    break;
                } else {
                    Intent itemone = new Intent(MenuUtamaActivity.this, AgendaActivity.class);
                    itemone.putExtra("id", id1);
                    startActivity(itemone);
                    break;
                }
            case R.id.agenda2:
                if (id2.equals("")) {
                    break;
                } else {
                    Intent itemtwo = new Intent(MenuUtamaActivity.this, AgendaActivity.class);
                    itemtwo.putExtra("id", id2);
                    startActivity(itemtwo);
                    break;
                }
        }
    }

    private void getInformationFromDB() {
        UsersRepository usersRepository = new UsersRepositoryImp(Users.class, FirestoreCollectionName.USERS);
        usersRepository.get(username_key_new).addOnCompleteListener(new OnCompleteListener<Users>() {
            @Override
            public void onComplete(@NonNull Task<Users> task) {
                if (task.isSuccessful()) {

                    // assigment to variable user
                    Users localUsers = task.getResult();
                    users = localUsers;

                    // get badge notification
                    getBadge();


                    //visible validasi from if role admin
                    System.out.print(localUsers.getRole());
                    if (localUsers.getRole() != null && localUsers.getRole().equals(Role.ADMIN)) {

                        lnr_add_user.setVisibility(View.VISIBLE);
                    }
                    lnr_validasi.setVisibility(View.VISIBLE);

                    nama.setText(localUsers.getNama());
                    nip.setText(CustomMask.formatNIP(localUsers.getNip()));

                    //render image
                    Picasso.with(MenuUtamaActivity.this)
                            .load(localUsers.getFoto())
                            .into(fotouser, new Callback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onError() {
                                    progressBar0.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), "Gagal Memuat Foto", Toast.LENGTH_SHORT).show();
                                }
                            });
                    progressBar0.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                } else {
                    //if error
                    goLogout();
                }
            }
        });
    }

    // update avatar
    private void updateAvatar(String uri) {
        UsersRepository usersRepository = new UsersRepositoryImp(Users.class, FirestoreCollectionName.USERS);
        users.setFoto(uri);
        usersRepository.update(users).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MenuUtamaActivity.this, "Update Foto", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MenuUtamaActivity.this, "Gagal Update Foto", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void getAgendaNew() {
        reference = FirebaseDatabase.getInstance().getReference()
                .child("Agenda");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    int index = (int) dataSnapshot.getChildrenCount();
                    int index2 = index - 1;
                    int count = 0;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        count++;
                        if (count == index) {
                            id1 = snapshot.child("id").getValue().toString();
                            namahorizon1.setText(snapshot.child("judul").getValue().toString());
                            kethorizon1.setText(snapshot.child("keterangan").getValue().toString());
                            Picasso.with(MenuUtamaActivity.this)
                                    .load(snapshot.child("gambar").getValue().toString()).centerCrop().fit()
                                    .into(gambarhorizon, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                            progressbar2.setVisibility(View.GONE);
                                        }

                                        @Override
                                        public void onError() {

                                        }
                                    });
                        }
                        if (count == index2) {
                            id2 = snapshot.child("id").getValue().toString();
                            namahorizon2.setText(snapshot.child("judul").getValue().toString());
                            kethorizon2.setText(snapshot.child("keterangan").getValue().toString());
                            Picasso.with(MenuUtamaActivity.this)
                                    .load(snapshot.child("gambar").getValue().toString()).centerCrop().fit()
                                    .into(gambarhorizon2, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                            progressbar3.setVisibility(View.GONE);
                                        }

                                        @Override
                                        public void onError() {

                                        }
                                    });
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }

    // badge for number
    private void getBadge() {
        if (users.getRole().equals(Role.ADMIN)) {
            CollectionReference collectionReference = new DocumentCutiRepositoryImp(FirestoreCollectionName.DOCUMENT_CUTI)
                    .documentCollection();
            collectionReference
                    .whereEqualTo("validasiAtasan" , null)
                    .whereEqualTo("validasiKepagawaian" , null)
                    .whereEqualTo("validasiPejabat" , null).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){

                        int count = 0;
                       QuerySnapshot value =  task.getResult();
                        for (QueryDocumentSnapshot doc : value) {
                            count++;
                        }
                        ImageBadgeView imageBadgeView =  findViewById(R.id.badgeValidasi);
                        imageBadgeView.setBadgeValue(count);
                    }
                }
            });



        }

    }

    private void goLogout() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(username_key, null);
        editor.apply();
        progressBar.setVisibility(View.VISIBLE);
        Intent gologin = new Intent(MenuUtamaActivity.this, LoginActivity.class);
        gologin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(gologin);
        finish();
    }


}
