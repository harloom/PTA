package com.coverteam.pta.views.validasi.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.coverteam.pta.R;
import com.coverteam.pta.d_menuUtama;
import com.coverteam.pta.data.models.DocumentCuti;
import com.coverteam.pta.data.models.Users;
import com.coverteam.pta.data.providers.FirestoreCollectionName;
import com.coverteam.pta.data.repositorys.DocumentCutiRepository;
import com.coverteam.pta.data.repositorys.DocumentCutiRepositoryImp;
import com.coverteam.pta.data.repositorys.UsersRepository;
import com.coverteam.pta.data.repositorys.UsersRepositoryImp;
import com.coverteam.pta.tools.HelperSize;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class DetailValidasiByAdminView extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "DetailValidasiByAdminView";
    String idcuti, statuscuti, alasantolakstring, username;
    TextView in_nama, in_nip, in_jabatan, in_alasan, sisa_cuti, in_alamat, in_hp;
    TextView statuspegawai, statusatasan, statuspejabat, txalasantolak;
    LinearLayout laypegawaiacc, layatasan, layatasanacc, laypejabat, laypejabatacc, laypenolakan;

    EditText alasantolak;

    Button kirim;

    //button action
    Button trm_pegawai, tolak_pegawai, terima_atasan, tolak_atasan, terima_pejabat, tolak_pejabat;


    private ListView listViewDateSelected;
    private ArrayList<String> listDateString = new ArrayList<>();

    private DocumentCuti documentCuti;
    private Users user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_j_validasi);

        Intent intent = getIntent();
        idcuti = intent.getStringExtra("cutiid");

        in_nama = findViewById(R.id.in_nama);
        in_nip = findViewById(R.id.in_nip);
        in_jabatan = findViewById(R.id.in_jabatan);
        in_alasan = findViewById(R.id.in_alasan);
        sisa_cuti = findViewById(R.id.sisa_cuti);
        in_alamat = findViewById(R.id.in_alamat);
        in_hp = findViewById(R.id.in_hp);

        statuspegawai = findViewById(R.id.txt_valid1);
        statusatasan = findViewById(R.id.txt_valid2);
        statuspejabat = findViewById(R.id.txt_valid3);
        laypegawaiacc = findViewById(R.id.laypegawaiacc);
        layatasan = findViewById(R.id.layatasan);
        layatasanacc = findViewById(R.id.layatasanacc);
        laypejabat = findViewById(R.id.laypejabat);
        laypejabatacc = findViewById(R.id.laypejabatacc);
        laypenolakan = findViewById(R.id.layalasantolak);
        alasantolak = findViewById(R.id.alasantolak);
        txalasantolak = findViewById(R.id.txalasantolak);
        kirim = findViewById(R.id.kirim);


        // listview find
        listViewDateSelected = findViewById(R.id.listDateSelected);

        trm_pegawai = findViewById(R.id.terima_kepegawaian);
        trm_pegawai.setOnClickListener(this);

        tolak_pegawai = findViewById(R.id.tolak_kepegawaian);
        tolak_pegawai.setOnClickListener(this);

        terima_atasan = findViewById(R.id.btn_terima_atasan);
        terima_atasan.setOnClickListener(this);

        tolak_atasan = findViewById(R.id.btn_tolak_atasan);
        tolak_atasan.setOnClickListener(this);

        terima_pejabat = findViewById(R.id.btn_terima_pejabat);
        terima_pejabat.setOnClickListener(this);

        tolak_pejabat = findViewById(R.id.btn_tolak_pejabat);
        tolak_pejabat.setOnClickListener(this);

        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.kirim).setOnClickListener(this);

        getPengajuanCutiFromDB();
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.terima_kepegawaian:
                changeStatus(DocumentCuti.MESSAGE_TERIMA_PEGAWAIAN);
                break;
            case R.id.tolak_kepegawaian:
                changeStatus(DocumentCuti.MESSAGE_TOLAK_PEGAWAIAN);
                break;
            case R.id.btn_terima_atasan:
                changeStatus(DocumentCuti.MESSAGE_TERIMA_ATASAN);
                break;
            case R.id.btn_tolak_atasan:
                changeStatus(DocumentCuti.MESSAGE_TOLAK_ATASAN);
                break;
            case R.id.btn_terima_pejabat:
                changeStatus(DocumentCuti.MESSAGE_TERIMA_PEJABAT);
                break;
            case R.id.btn_tolak_pejabat:
                changeStatus(DocumentCuti.MESSAGE_TOLAK_PEJABAT);
                break;
            case R.id.back:
                Intent home = new Intent(DetailValidasiByAdminView.this, d_menuUtama.class);
                startActivity(home);
                break;
            case R.id.kirim:
                kirimalasan();
                break;
        }
    }

    private void kirimalasan() {
        DocumentCuti documentCutiUpdate = documentCuti;
        DocumentCutiRepository repository = new DocumentCutiRepositoryImp(FirestoreCollectionName.DOCUMENT_CUTI);

        documentCutiUpdate.setPenolakanMessage(alasantolak.getText().toString());
        repository.update(documentCutiUpdate, user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(DetailValidasiByAdminView.this, "Successfull", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DetailValidasiByAdminView.this, "Coba Ulang!", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        reference = FirebaseDatabase.getInstance().getReference()
//                .child("Pengajuan_Cuti").child(idcuti);
//        reference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                try {
//                    dataSnapshot.getRef().child("cutiTolakAlasan").setValue(alasantolak.getText().toString());
//                    getPengajuanCutiFromDB();
//                    Toast.makeText(getApplicationContext(), "Alasan Penolakan Cuti Telah Diperbarui!", Toast.LENGTH_SHORT).show();
//                }
//                catch (Exception e){
//                    Toast.makeText(getApplicationContext(), "Terjadi Kesalahan 3", Toast.LENGTH_LONG).show();
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

    private void getPengajuanCutiFromDB() {

        DocumentCutiRepository documentCutiRepository = new DocumentCutiRepositoryImp(
                FirestoreCollectionName.DOCUMENT_CUTI
        );


        documentCutiRepository.documentCollection().document(idcuti)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        if (snapshot != null && snapshot.exists()) {

                            documentCuti = snapshot.toObject(DocumentCuti.class);
                            in_nama.setText(Objects.requireNonNull(documentCuti.getNamaPengaju()));
                            in_nip.setText(Objects.requireNonNull(documentCuti.getNipPengaju()));

                            in_alamat.setText(Objects.requireNonNull(documentCuti.getAlamat()));
                            in_alasan.setText(Objects.requireNonNull(documentCuti.getAlasan()));
                            in_hp.setText(Objects.requireNonNull(documentCuti.getNoHp()));
//                            in_type_alasan.setText(documentCuti.getTypeAlasan());
//                            pentingVaribale.setText(documentCuti.getUrgent() ? "Penting" : "");
                            // format date
                            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());

                            listDateString.clear();


                            // for each
                            for (Date date : documentCuti.getListTgl()) {
                                // add to array
                                listDateString.add(dateFormatter.format(date));
                            }

                            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(DetailValidasiByAdminView.this,
                                    android.R.layout.simple_list_item_1, android.R.id.text1, listDateString);
                            listViewDateSelected.setAdapter(adapter);
                            HelperSize.getListViewSize(listViewDateSelected);


                            //validasi
                            if (documentCuti.getValidasiKepagawaian() != null && documentCuti.getValidasiKepagawaian().equals(DocumentCuti.TERIMA)) {
                                trm_pegawai.setVisibility(View.GONE);
                                tolak_pegawai.setVisibility(View.VISIBLE);

                                statuspegawai.setText(DocumentCuti.MESSAGE_TERIMA_PEGAWAIAN);
                                statuspegawai.setTextColor(Color.parseColor(DocumentCuti.colorTerima));
                            } else if (documentCuti.getValidasiKepagawaian() != null && documentCuti.getValidasiKepagawaian().equals(DocumentCuti.TOLAK)) {
                                trm_pegawai.setVisibility(View.VISIBLE);
                                tolak_pegawai.setVisibility(View.GONE);


                                statuspegawai.setText(DocumentCuti.MESSAGE_TOLAK_PEGAWAIAN);
                                statuspegawai.setTextColor(Color.parseColor(DocumentCuti.colorTOLAK));
                            } else {
                                trm_pegawai.setVisibility(View.VISIBLE);
                                tolak_pegawai.setVisibility(View.VISIBLE);

                                statuspegawai.setText(DocumentCuti.MESSAGE_MENUNGGU_PEGAWAIAN);
                                statuspegawai.setTextColor(Color.parseColor(DocumentCuti.colorMenunggu));
                            }

                            if (documentCuti.getValidasiAtasan() != null && documentCuti.getValidasiAtasan().equals(DocumentCuti.TERIMA)) {
                                terima_atasan.setVisibility(View.GONE);
                                tolak_atasan.setVisibility(View.VISIBLE);

                                statusatasan.setText(DocumentCuti.MESSAGE_TERIMA_ATASAN);
                                statusatasan.setTextColor(Color.parseColor(DocumentCuti.colorTerima));
                            } else if (documentCuti.getValidasiAtasan() != null && documentCuti.getValidasiAtasan().equals(DocumentCuti.TOLAK)) {
                                terima_atasan.setVisibility(View.VISIBLE);
                                tolak_atasan.setVisibility(View.GONE);


                                statusatasan.setText(DocumentCuti.MESSAGE_TOLAK_ATASAN);
                                statusatasan.setTextColor(Color.parseColor(DocumentCuti.colorTOLAK));
                            } else {
                                terima_atasan.setVisibility(View.VISIBLE);
                                tolak_atasan.setVisibility(View.VISIBLE);

                                statusatasan.setText(DocumentCuti.MESSAGE_MENUNGGU_PERSETUJUAN);
                                statusatasan.setTextColor(Color.parseColor(DocumentCuti.colorMenunggu));
                            }

                            if (documentCuti.getValidasiPejabat() != null && documentCuti.getValidasiPejabat().equals(DocumentCuti.TERIMA)) {
                                terima_pejabat.setVisibility(View.GONE);
                                tolak_pejabat.setVisibility(View.VISIBLE);


                                statuspejabat.setText(DocumentCuti.MESSAGE_TERIMA_PEJABAT);
                                statuspejabat.setTextColor(Color.parseColor(DocumentCuti.colorTerima));
                            } else if (documentCuti.getValidasiPejabat() != null && documentCuti.getValidasiPejabat().equals(DocumentCuti.TOLAK)) {
                                terima_pejabat.setVisibility(View.VISIBLE);
                                tolak_pejabat.setVisibility(View.GONE);

                                statuspejabat.setText(DocumentCuti.MESSAGE_TOLAK_PEJABAT);
                                statuspejabat.setTextColor(Color.parseColor(DocumentCuti.colorTOLAK));
                            } else {
                                terima_pejabat.setVisibility(View.VISIBLE);
                                tolak_pejabat.setVisibility(View.VISIBLE);

                                statuspejabat.setText(DocumentCuti.MESSAGE_MENUNGGU_PERSETUJUAN);
                                statuspejabat.setTextColor(Color.parseColor(DocumentCuti.colorMenunggu));
                            }

                            // if all tolak tampil kirim pesan
                            if (documentCuti.getValidasiPejabat() != null && documentCuti.getValidasiPejabat().equals(DocumentCuti.TOLAK)
                                    && documentCuti.getValidasiKepagawaian() != null && documentCuti.getValidasiKepagawaian().equals(DocumentCuti.TOLAK)
                                    && documentCuti.getValidasiAtasan() != null && documentCuti.getValidasiAtasan().equals(DocumentCuti.TOLAK)
                            ){
                                findViewById(R.id.layalasantolak).setVisibility(View.VISIBLE);
                            }else{
                                findViewById(R.id.layalasantolak).setVisibility(View.GONE);
                            }



                            getUser();

                        } else {
                            Log.d(TAG, "Current data: null");
                        }
//                        progressBar.setVisibility(View.GONE);
                    }
                });
//        pengajuancuti = FirebaseDatabase.getInstance().getReference()
//                .child("Pengajuan_Cuti").child(idcuti);
//        pengajuancuti.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                try {
//                    in_nama.setText(Objects.requireNonNull(dataSnapshot.child("cutiNama").getValue()).toString());
//                    in_nip.setText(Objects.requireNonNull(dataSnapshot.child("cutiNIP").getValue()).toString());
//                    username = Objects.requireNonNull(dataSnapshot.child("cutiUsername").getValue()).toString();
//                    in_jabatan.setText(Objects.requireNonNull(dataSnapshot.child("cutiJabatan").getValue()).toString());
//                    in_alamat.setText(Objects.requireNonNull(dataSnapshot.child("cutiAlamat").getValue()).toString());
//                    in_alasan.setText(Objects.requireNonNull(dataSnapshot.child("cutiAlasan").getValue()).toString());
//                    in_hp.setText(Objects.requireNonNull(dataSnapshot.child("cutiNoHP").getValue()).toString());
//                    in_tgl_mulai.setText(Objects.requireNonNull(dataSnapshot.child("cutiTglMulai").getValue()).toString());
//                    in_tgl_selesai.setText(Objects.requireNonNull(dataSnapshot.child("cutiTglSelesai").getValue()).toString());
//                    alasantolakstring = dataSnapshot.child("cutiTolakAlasan").getValue().toString();
//                    txalasantolak.setText(Objects.requireNonNull(dataSnapshot.child("cutiTolakAlasan").getValue()).toString());
//                    statuscuti = dataSnapshot.child("cutiStatus").getValue().toString();
//                    if (statuscuti.equals("ditolakpegawai")){
//                        statuspegawai.setText("DATA DI TOLAK OLEH KEPEGAWAIAN");
//                        laypegawaiacc.setVisibility(View.GONE);
//                        laypenolakan.setVisibility(View.VISIBLE);
//                        if (!alasantolakstring.equals("")){
//                            alasantolak.setVisibility(View.GONE);
//                            kirim.setVisibility(View.GONE);
//                        }
//                    }
//                    else if (statuscuti.equals("cekatasan")){
//                        statuspegawai.setText("DATA DI TERIMA OLEH KEPEGAWAIAN");
//                        statuspegawai.setTextColor(Color.parseColor("#5ABD8C"));
//                        laypegawaiacc.setVisibility(View.GONE);
//                        layatasan.setVisibility(View.VISIBLE);
//                    }
//                    else if (statuscuti.equals("ditolakatasan")){
//                        statuspegawai.setText("DATA DI TERIMA OLEH KEPEGAWAIAN");
//                        statuspegawai.setTextColor(Color.parseColor("#5ABD8C"));
//                        statusatasan.setText("ATASAN MENOLAK PENGAJUAN CUTI");
//                        laypegawaiacc.setVisibility(View.GONE);
//                        layatasan.setVisibility(View.VISIBLE);
//                        layatasanacc.setVisibility(View.GONE);
//                        laypenolakan.setVisibility(View.VISIBLE);
//                        if (!alasantolakstring.equals("")){
//                            alasantolak.setVisibility(View.GONE);
//                            kirim.setVisibility(View.GONE);
//                        }
//                    }
//                    else if (statuscuti.equals("cekpejabat")){
//                        statuspegawai.setText("DATA DI TERIMA OLEH KEPEGAWAIAN");
//                        statuspegawai.setTextColor(Color.parseColor("#5ABD8C"));
//                        statusatasan.setText("PENGAJUAN CUTI DI TERIMA OLEH ATASAN");
//                        statusatasan.setTextColor(Color.parseColor("#5ABD8C"));
//                        laypegawaiacc.setVisibility(View.GONE);
//                        layatasan.setVisibility(View.VISIBLE);
//                        layatasanacc.setVisibility(View.GONE);
//                        laypejabat.setVisibility(View.VISIBLE);
//                    }
//                    else if (statuscuti.equals("ditolakpejabat")){
//                        statuspegawai.setText("DATA DI TERIMA OLEH KEPEGAWAIAN");
//                        statuspegawai.setTextColor(Color.parseColor("#5ABD8C"));
//                        statusatasan.setText("PENGAJUAN CUTI DI TERIMA OLEH ATASAN");
//                        statusatasan.setTextColor(Color.parseColor("#5ABD8C"));
//                        statuspejabat.setText("PEJABAT MENOLAK PENGAJUAN CUTI");
//                        laypegawaiacc.setVisibility(View.GONE);
//                        layatasan.setVisibility(View.VISIBLE);
//                        layatasanacc.setVisibility(View.GONE);
//                        laypejabat.setVisibility(View.VISIBLE);
//                        laypejabatacc.setVisibility(View.GONE);
//                        laypenolakan.setVisibility(View.VISIBLE);
//                        if (!alasantolakstring.equals("")){
//                            alasantolak.setVisibility(View.GONE);
//                            kirim.setVisibility(View.GONE);
//                        }
//                    }
//                    else if (statuscuti.equals("allok")){
//                        statuspegawai.setText("DATA DI TERIMA OLEH KEPEGAWAIAN");
//                        statuspegawai.setTextColor(Color.parseColor("#5ABD8C"));
//                        statusatasan.setText("PENGAJUAN CUTI DI TERIMA OLEH ATASAN");
//                        statusatasan.setTextColor(Color.parseColor("#5ABD8C"));
//                        statuspejabat.setText("PENGAJUAN CUTI DI TERIMA OLEH PEJABAT");
//                        statuspejabat.setTextColor(Color.parseColor("#5ABD8C"));
//                        laypegawaiacc.setVisibility(View.GONE);
//                        layatasan.setVisibility(View.VISIBLE);
//                        layatasanacc.setVisibility(View.GONE);
//                        laypejabat.setVisibility(View.VISIBLE);
//                        laypejabatacc.setVisibility(View.GONE);
//                    }
//                    getInformationFromDB();
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


    private void getUser() {
        String USERNAME_KEY = "usernamekey";
        String username_key = "";
        String username_key_new = "";
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
        UsersRepository usersRepository = new UsersRepositoryImp(Users.class, FirestoreCollectionName.USERS);
        usersRepository.get(username_key_new).addOnCompleteListener(new OnCompleteListener<Users>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<Users> task) {
                if (task.isSuccessful()) {
                    user = task.getResult();
                    sisa_cuti.setText(user.getJumlahMaximalCutiPertahun().toString());
                    in_jabatan.setText(Objects.requireNonNull(user.getJabatan()));
                } else {
                    Toast.makeText(getApplicationContext(), "NIP tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void changeStatus(final String status) {
        DocumentCuti documentCutiUpdate = documentCuti;


        DocumentCutiRepository repository = new DocumentCutiRepositoryImp(FirestoreCollectionName.DOCUMENT_CUTI);

        Task<Void> updateVoid = null;

        if (DocumentCuti.MESSAGE_TERIMA_PEGAWAIAN.equals(status)) {

            documentCutiUpdate.setValidasiKepagawaian(DocumentCuti.TERIMA);
            documentCutiUpdate.setValidasiNipKepagawaian(user.getNip());
            updateVoid = repository.update(documentCutiUpdate, user);

        } else if (DocumentCuti.MESSAGE_TOLAK_PEGAWAIAN.equals(status)) {

            documentCutiUpdate.setValidasiKepagawaian(DocumentCuti.TOLAK);
            documentCutiUpdate.setValidasiNipKepagawaian(user.getNip());
            updateVoid = repository.update(documentCutiUpdate, user);

        } else if (DocumentCuti.MESSAGE_TERIMA_ATASAN.equals(status)) {

            documentCutiUpdate.setValidasiAtasan(DocumentCuti.TERIMA);

            documentCutiUpdate.setValidasiNipAtasan(user.getNip());
            updateVoid = repository.update(documentCutiUpdate, user);

        } else if (DocumentCuti.MESSAGE_TOLAK_ATASAN.equals(status)) {

            documentCutiUpdate.setValidasiAtasan(DocumentCuti.TOLAK);
            documentCutiUpdate.setValidasiNipAtasan(user.getNip());
            updateVoid = repository.update(documentCutiUpdate, user);
        } else if (DocumentCuti.MESSAGE_TERIMA_PEJABAT.equals(status)) {

            documentCutiUpdate.setValidasiPejabat(DocumentCuti.TERIMA);
            documentCutiUpdate.setValidasiNipPejabat(user.getNip());
            updateVoid = repository.update(documentCutiUpdate, user);

        } else if (DocumentCuti.MESSAGE_TOLAK_PEJABAT.equals(status)) {

            documentCutiUpdate.setValidasiPejabat(DocumentCuti.TOLAK);
            documentCutiUpdate.setValidasiNipPejabat(user.getNip());
            updateVoid = repository.update(documentCutiUpdate, user);

        } else {
            Toast.makeText(DetailValidasiByAdminView.this, "Button Tidak ditemukan", Toast.LENGTH_SHORT).show();
        }

        if (updateVoid == null) {
            return;
        }

        updateVoid.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(DetailValidasiByAdminView.this, "Updated", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
