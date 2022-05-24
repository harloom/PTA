package com.coverteam.pta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import com.coverteam.pta.data.repositorys.UsersRepository;
import com.coverteam.pta.data.repositorys.UsersRepositoryImp;
import com.coverteam.pta.printer.PdfViewerExampleActivity;
import com.coverteam.pta.tools.CustomMask;
import com.coverteam.pta.tools.PicassoImageLoader;
import com.coverteam.pta.views.created_users.UsersListActivity;
import com.coverteam.pta.views.from_cuti.FromCutiView;
import com.coverteam.pta.views.riwayat_cuti.RiwayatCutiView;
import com.coverteam.pta.views.validasi.admin.ValidasiByAdminView;
import com.coverteam.pta.views.validasi.atasan.ValidasiByAtasanView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lv.chi.photopicker.ChiliPhotoPicker;
import lv.chi.photopicker.PhotoPickerFragment;

public class d_menuUtama extends AppCompatActivity implements View.OnClickListener , PhotoPickerFragment.Callback{

    LinearLayout lnr_validasi , lnr_add_user;
    TextView nama,nip,namahorizon1,namahorizon2,kethorizon1,kethorizon2;
    ProgressBar progressBar,progressbar2,progressbar3,progressBar0;
    ImageView gambarhorizon, gambarhorizon2,fotouser;

    DatabaseReference reference;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";
    String id1 = "",id2 = "";



    // user data
        private  Users users;


//    private final SimpleStorageHelper storageHelper = new SimpleStorageHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_menu_utama);
//        setupSimpleStorage(savedInstanceState);
//        permissionRequest.check();


        //initial chilPhoto
        ChiliPhotoPicker.INSTANCE.init(new PicassoImageLoader(),"lv.chi.sample.fileprovider");

        getUsernameLocal();

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

    private void pickGallery(){
         PhotoPickerFragment photoPickerFragment = PhotoPickerFragment.
                 Companion.newInstance(false,true,1, R.style.ChiliPhotoPicker_Dark);
         photoPickerFragment.show(getSupportFragmentManager(),"picker");

    }

    @Override
    public void onImagesPicked(@NotNull ArrayList<Uri> arrayList) {
        Log.d("print",arrayList.toString());
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.circlefoto:
                pickGallery();
                break;
            case R.id.icon_add_user:
                Intent goManagementUser = new Intent(d_menuUtama.this, UsersListActivity.class);
                startActivity(goManagementUser);
                break;

            case R.id.ikon_cuti:
                Intent gocuti = new Intent(d_menuUtama.this, FromCutiView.class);
                startActivity(gocuti);
                break;
            case R.id.ikon_profil:
                Intent goprofil = new Intent(d_menuUtama.this, f_profil.class);
                startActivity(goprofil);
                break;
            case R.id.ikon_panduan:
                Intent gopanduan = new Intent(d_menuUtama.this, h_panduan.class);
                startActivity(gopanduan);
                break;
            case R.id.ikon_riwayat:
                Intent goriwayat = new Intent(d_menuUtama.this, RiwayatCutiView.class);
                startActivity(goriwayat);
                break;
            case R.id.ikon_validasi:

                if(users.getRole().equals(Role.ADMIN)){

                    Intent govalidasiAdmin = new Intent(d_menuUtama.this, ValidasiByAdminView.class);
                    startActivity(govalidasiAdmin);
                }else{
                    Intent govalidasiAtasan = new Intent(d_menuUtama.this, ValidasiByAtasanView.class);
                    startActivity(govalidasiAtasan);
                }


                break;
            case R.id.agenda1:
                if (id1.equals("")){
                    break;
                }
                else {
                    Intent itemone = new Intent(d_menuUtama.this, l_agenda.class);
                    itemone.putExtra("id",id1);
                    startActivity(itemone);
                    break;
                }
            case R.id.agenda2:
                if (id2.equals("")){
                    break;
                }
                else {
                    Intent itemtwo = new Intent(d_menuUtama.this, l_agenda.class);
                    itemtwo.putExtra("id",id2);
                    startActivity(itemtwo);
                    break;
                }
        }
    }

    private void getInformationFromDB() {
        UsersRepository usersRepository =  new UsersRepositoryImp(Users.class, FirestoreCollectionName.USERS);
        usersRepository.get(username_key_new).addOnCompleteListener(new OnCompleteListener<Users>() {
            @Override
            public void onComplete(@NonNull Task<Users> task) {
                    if(task.isSuccessful()){
                        Users localUsers =  task.getResult();
                        users = localUsers;

                        //visible validasi from if role admin
                        System.out.print(localUsers.getRole());
                        if(localUsers.getRole().equals(Role.ADMIN)){

                            lnr_add_user.setVisibility(View.VISIBLE);
                        }
                        lnr_validasi.setVisibility(View.VISIBLE);

                        nama.setText(localUsers.getNama());
                        nip.setText(CustomMask.formatNIP(localUsers.getNip()));
                        Picasso.with(d_menuUtama.this)
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
//
//        reference = FirebaseDatabase.getInstance().getReference()
//                .child("pegawai2").child(username_key_new);
//
//
//        reference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                try {
//                    nama.setText(dataSnapshot.child("NAMA").getValue().toString());
//                    nip.setText(dataSnapshot.child("NIP").getValue().toString());
//                    Picasso.with(d_menuUtama.this)
//                            .load(dataSnapshot.child("FOTO").getValue().toString())
//                            .into(fotouser, new Callback() {
//                                @Override
//                                public void onSuccess() {
//                                    progressBar0.setVisibility(View.GONE);
//                                }
//
//                                @Override
//                                public void onError() {
//                                    Toast.makeText(getApplicationContext(),"Gagal Memuat Foto",Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                    progressBar.setVisibility(View.GONE);
//                }
//                catch (Exception e){
//                    Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }

    private void getAgendaNew() {
        reference = FirebaseDatabase.getInstance().getReference()
                .child("Agenda");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    int index = (int) dataSnapshot.getChildrenCount();
                    int index2 = index-1;
                    int count = 0;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        count++;
                        if (count == index) {
                            id1 = snapshot.child("id").getValue().toString();
                            namahorizon1.setText(snapshot.child("judul").getValue().toString());
                            kethorizon1.setText(snapshot.child("keterangan").getValue().toString());
                            Picasso.with(d_menuUtama.this)
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
                            Picasso.with(d_menuUtama.this)
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
                }
                else {
                    Toast.makeText(getApplicationContext(),"Terjadi Kesalahan", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }

    private void goLogout(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(username_key,null);
        editor.apply();
        progressBar.setVisibility(View.VISIBLE);
        Intent gologin = new Intent(d_menuUtama.this, c_login.class);
        gologin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(gologin);
        finish();
    }



//    private void setupSimpleStorage(Bundle savedState) {
//        if (savedState != null) {
//            storageHelper.onRestoreInstanceState(savedState);
//        }
//
//
//        storageHelper.setOnStorageAccessGranted((requestCode, root) -> {
//            String absolutePath = DocumentFileUtils.getAbsolutePath(root, getBaseContext());
//            Toast.makeText(
//                    getBaseContext(),
//                    getString(R.string.ss_selecting_root_path_success_without_open_folder_picker, absolutePath),
//                    Toast.LENGTH_SHORT
//            ).show();
//            return null;
//        });
//        storageHelper.setOnFileSelected((requestCode, files) -> {
//            String message = "File selected: " + DocumentFileUtils.getFullName(files.get(0));
//            Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
//            return null;
//        });
//        storageHelper.setOnFolderSelected((requestCode, folder) -> {
//            String message = "Folder selected: " + DocumentFileUtils.getAbsolutePath(folder, getBaseContext());
//            Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
//            return null;
//        });
//        storageHelper.setOnFileCreated((requestCode, file) -> {
//            String message = "File created: " + file.getName();
//            Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
//            return null;
//        });
//    }
//
//    private final ActivityPermissionRequest permissionRequest = new ActivityPermissionRequest.Builder(this)
//            .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
//            .withCallback(new PermissionCallback() {
//                @Override
//                public void onPermissionsChecked(@NotNull PermissionResult result, boolean fromSystemDialog) {
//                    String grantStatus = result.getAreAllPermissionsGranted() ? "granted" : "denied";
//                    Toast.makeText(getBaseContext(), "Storage permissions are " + grantStatus, Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onShouldRedirectToSystemSettings(@NotNull List<PermissionReport> blockedPermissions) {
//                    SimpleStorageHelper.redirectToSystemSettings(d_menuUtama.this);
//                }
//            })
//            .build();
}
