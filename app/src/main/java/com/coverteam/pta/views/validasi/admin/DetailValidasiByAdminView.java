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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.coverteam.pta.R;
import com.coverteam.pta.MenuUtamaActivity;
import com.coverteam.pta.data.models.DocumentCuti;
import com.coverteam.pta.data.models.Role;
import com.coverteam.pta.data.models.Users;
import com.coverteam.pta.data.providers.FirestoreCollectionName;
import com.coverteam.pta.data.repositorys.DocumentCutiRepository;
import com.coverteam.pta.data.repositorys.DocumentCutiRepositoryImp;
import com.coverteam.pta.data.repositorys.UsersRepository;
import com.coverteam.pta.data.repositorys.UsersRepositoryImp;
import com.coverteam.pta.tools.HelperSize;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class DetailValidasiByAdminView extends AppCompatActivity implements View.OnClickListener {
    // array atasan
    private ArrayList<Users> listUsersAtasan = new ArrayList<>();
    // array atasan
    private ArrayList<Users> listUsersPejabat = new ArrayList<>();
    private TextInputLayout tl_atasan;
    private TextInputLayout tl_pejabat;
    private static final String TAG = "DetailValidasiByAdminView";
    String idcuti, statuscuti, alasantolakstring, username;
    TextView in_nama, in_nip, in_jabatan, in_alasan, sisa_cuti, in_alamat, in_hp;
    TextView statuspegawai, statusatasan, statuspejabat, txalasantolak;
    LinearLayout laypegawaiacc, layatasan, layatasanacc, laypejabat, laypejabatacc, laypenolakan;
    LinearLayout laypegawai;
    EditText alasantolak;

    Button kirim;

    //button action
    Button trm_pegawai, tolak_pegawai, terima_atasan, tolak_atasan, terima_pejabat, tolak_pejabat;

    Button tanggh_pegawai,tanggh_atasan,tanggh_pejabat;


    private ListView listViewDateSelected;
    private ArrayList<String> listDateString = new ArrayList<>();

    private DocumentCuti documentCuti;
    private Users userLogin;
    private Users userDoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_j_validasi);

        layatasan = findViewById(R.id.layatasan);
        laypejabat = findViewById(R.id.laypejabat);
        laypegawai = findViewById(R.id.laypegawai);

        Intent intent = getIntent();
        idcuti = intent.getStringExtra("cutiid");
        String role = intent.getStringExtra("role");

        if (role.equals(Role.ADMIN)) {
            laypejabat.setVisibility(View.VISIBLE);
            layatasan.setVisibility(View.VISIBLE);
            laypegawai.setVisibility(View.VISIBLE);
        } else if (role.equals(Role.PEJABAT)|| role.equals(Role.WAKIL_KETUA) || role.equals(Role.KETUA)) {
            laypejabat.setVisibility(View.VISIBLE);
            layatasan.setVisibility(View.GONE);
            laypegawai.setVisibility(View.VISIBLE);
        }else if(role.equals(Role.KEPEGAWAIAN)) {
            laypejabat.setVisibility(View.GONE);
            layatasan.setVisibility(View.VISIBLE);
            laypegawai.setVisibility(View.VISIBLE);
        }else {
            laypejabat.setVisibility(View.GONE);
            layatasan.setVisibility(View.GONE);
            laypegawai.setVisibility(View.GONE);
        }

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


        layatasanacc = findViewById(R.id.layatasanacc);

        laypejabatacc = findViewById(R.id.laypejabatacc);


        laypenolakan = findViewById(R.id.layalasantolak);

        alasantolak = findViewById(R.id.alasantolak);
        txalasantolak = findViewById(R.id.txalasantolak);
        kirim = findViewById(R.id.kirim);

        //tl atasan and pejabat
        tl_atasan = findViewById(R.id.input_atasan);
        tl_pejabat = findViewById(R.id.input_pejabat);


        // listview find
        listViewDateSelected = findViewById(R.id.listDateSelected);

        trm_pegawai = findViewById(R.id.terima_kepegawaian);
        trm_pegawai.setOnClickListener(this);


        tolak_pegawai = findViewById(R.id.tolak_kepegawaian);
        tolak_pegawai.setOnClickListener(this);

//        Button tanggh_pegawai,tanggh_atasan,tanggh_pejabat;
        tanggh_pegawai = findViewById(R.id.tangguhkan_kepegawaian);
        tanggh_pegawai.setOnClickListener(this);

        terima_atasan = findViewById(R.id.btn_terima_atasan);
        terima_atasan.setOnClickListener(this);

        tolak_atasan = findViewById(R.id.btn_tolak_atasan);
        tolak_atasan.setOnClickListener(this);

        tanggh_atasan = findViewById(R.id.btn_tangguhkan_atasan);
        tanggh_atasan.setOnClickListener(this);

        terima_pejabat = findViewById(R.id.btn_terima_pejabat);
        terima_pejabat.setOnClickListener(this);

        tolak_pejabat = findViewById(R.id.btn_tolak_pejabat);
        tolak_pejabat.setOnClickListener(this);

        tanggh_pejabat = findViewById(R.id.btn_tangguhkan_pejabat);
        tanggh_pejabat.setOnClickListener(this);

        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.kirim).setOnClickListener(this);

        getPengajuanCutiFromDB();
        getUserLogin();
        iniDataPejabat();
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

            case R.id.tangguhkan_kepegawaian:
                changeStatus(DocumentCuti.MESSAGE_TANGGUH_PEGAWAI);
                break;

            case R.id.btn_terima_atasan:
                changeStatus(DocumentCuti.MESSAGE_TERIMA_ATASAN);
                break;
            case R.id.btn_tolak_atasan:
                changeStatus(DocumentCuti.MESSAGE_TOLAK_ATASAN);
                break;
            case R.id.btn_tangguhkan_atasan:
                changeStatus(DocumentCuti.MESSAGE_TANGGUH_ATASAN);
                break;
            case R.id.btn_terima_pejabat:
                changeStatus(DocumentCuti.MESSAGE_TERIMA_PEJABAT);
                break;
            case R.id.btn_tolak_pejabat:
                changeStatus(DocumentCuti.MESSAGE_TOLAK_PEJABAT);
                break;

            case R.id.btn_tangguhkan_pejabat:
                changeStatus(DocumentCuti.MESSAGE_TANGGUH_PEJABAT);
                break;
            case R.id.back:
                Intent home = new Intent(DetailValidasiByAdminView.this, MenuUtamaActivity.class);
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
        repository.update(documentCutiUpdate, userDoc).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(DetailValidasiByAdminView.this, "Successfull", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DetailValidasiByAdminView.this, "Coba Ulang!", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
                                tanggh_pegawai.setVisibility(View.VISIBLE);
                                tolak_pegawai.setVisibility(View.VISIBLE);

                                statuspegawai.setText(DocumentCuti.MESSAGE_TERIMA_PEGAWAIAN);
                                statuspegawai.setTextColor(Color.parseColor(DocumentCuti.colorTerima));
                            } else if (documentCuti.getValidasiKepagawaian() != null && documentCuti.getValidasiKepagawaian().equals(DocumentCuti.TOLAK)) {
                                trm_pegawai.setVisibility(View.VISIBLE);
                                tolak_pegawai.setVisibility(View.GONE);
                                tanggh_pegawai.setVisibility(View.VISIBLE);

                                statuspegawai.setText(DocumentCuti.MESSAGE_TOLAK_PEGAWAIAN);
                                statuspegawai.setTextColor(Color.parseColor(DocumentCuti.colorTOLAK));
                            }else if (documentCuti.getValidasiKepagawaian() != null && documentCuti.getValidasiKepagawaian().equals(DocumentCuti.TANGGUHKAN)) {
                                trm_pegawai.setVisibility(View.VISIBLE);
                                tolak_pegawai.setVisibility(View.VISIBLE);
                                tanggh_pegawai.setVisibility(View.GONE);

                                statuspegawai.setText(DocumentCuti.MESSAGE_TANGGUH_PEGAWAI);
                                statuspegawai.setTextColor(Color.parseColor(DocumentCuti.colorTOLAK));
                            }  else {
                                trm_pegawai.setVisibility(View.VISIBLE);
                                tolak_pegawai.setVisibility(View.VISIBLE);
                                tanggh_pegawai.setVisibility(View.VISIBLE);
                                statuspegawai.setText(DocumentCuti.MESSAGE_MENUNGGU_PEGAWAIAN);
                                statuspegawai.setTextColor(Color.parseColor(DocumentCuti.colorMenunggu));
                            }

                            if (documentCuti.getValidasiAtasan() != null && documentCuti.getValidasiAtasan().equals(DocumentCuti.TERIMA)) {
                                terima_atasan.setVisibility(View.GONE);
                                tolak_atasan.setVisibility(View.VISIBLE);
                                tanggh_atasan.setVisibility(View.VISIBLE);

                                statusatasan.setText(DocumentCuti.MESSAGE_TERIMA_ATASAN);
                                statusatasan.setTextColor(Color.parseColor(DocumentCuti.colorTerima));
                            } else if (documentCuti.getValidasiAtasan() != null && documentCuti.getValidasiAtasan().equals(DocumentCuti.TOLAK)) {
                                terima_atasan.setVisibility(View.VISIBLE);
                                tolak_atasan.setVisibility(View.GONE);
                                tanggh_atasan.setVisibility(View.VISIBLE);

                                statusatasan.setText(DocumentCuti.MESSAGE_TOLAK_ATASAN);
                                statusatasan.setTextColor(Color.parseColor(DocumentCuti.colorTOLAK));
                            }  else if (documentCuti.getValidasiAtasan() != null && documentCuti.getValidasiAtasan().equals(DocumentCuti.TANGGUHKAN)) {
                                terima_atasan.setVisibility(View.VISIBLE);
                                tolak_atasan.setVisibility(View.VISIBLE);
                                tanggh_atasan.setVisibility(View.GONE);

                                statusatasan.setText(DocumentCuti.MESSAGE_TANGGUH_ATASAN);
                                statusatasan.setTextColor(Color.parseColor(DocumentCuti.colorTOLAK));
                            }else {
                                terima_atasan.setVisibility(View.VISIBLE);
                                tolak_atasan.setVisibility(View.VISIBLE);

                                statusatasan.setText(DocumentCuti.MESSAGE_MENUNGGU_PERSETUJUAN);
                                statusatasan.setTextColor(Color.parseColor(DocumentCuti.colorMenunggu));
                            }

                            if (documentCuti.getValidasiPejabat() != null && documentCuti.getValidasiPejabat().equals(DocumentCuti.TERIMA)) {
                                terima_pejabat.setVisibility(View.GONE);
                                tolak_pejabat.setVisibility(View.VISIBLE);
                                tanggh_pejabat.setVisibility(View.VISIBLE);

                                statuspejabat.setText(DocumentCuti.MESSAGE_TERIMA_PEJABAT);
                                statuspejabat.setTextColor(Color.parseColor(DocumentCuti.colorTerima));
                            } else if (documentCuti.getValidasiPejabat() != null && documentCuti.getValidasiPejabat().equals(DocumentCuti.TOLAK)) {
                                terima_pejabat.setVisibility(View.VISIBLE);
                                tolak_pejabat.setVisibility(View.GONE);
                                tanggh_pejabat.setVisibility(View.VISIBLE);
                                statuspejabat.setText(DocumentCuti.MESSAGE_TOLAK_PEJABAT);
                                statuspejabat.setTextColor(Color.parseColor(DocumentCuti.colorTOLAK));
                            }else if (documentCuti.getValidasiPejabat() != null && documentCuti.getValidasiPejabat().equals(DocumentCuti.TANGGUHKAN)) {
                                terima_pejabat.setVisibility(View.VISIBLE);
                                tolak_pejabat.setVisibility(View.VISIBLE);
                                tanggh_pejabat.setVisibility(View.GONE);

                                statuspejabat.setText(DocumentCuti.MESSAGE_TANGGUH_PEJABAT);
                                statusatasan.setTextColor(Color.parseColor(DocumentCuti.colorTOLAK));
                            } else {
                                terima_pejabat.setVisibility(View.VISIBLE);
                                tolak_pejabat.setVisibility(View.VISIBLE);
                                tanggh_pejabat.setVisibility(View.VISIBLE);

                                statuspejabat.setText(DocumentCuti.MESSAGE_MENUNGGU_PERSETUJUAN);
                                statuspejabat.setTextColor(Color.parseColor(DocumentCuti.colorMenunggu));
                            }

                            // if all tolak tampil kirim pesan
                            if (documentCuti.getValidasiPejabat() != null && documentCuti.getValidasiPejabat().equals(DocumentCuti.TOLAK)
                                    && documentCuti.getValidasiKepagawaian() != null && documentCuti.getValidasiKepagawaian().equals(DocumentCuti.TOLAK)
                                    && documentCuti.getValidasiAtasan() != null && documentCuti.getValidasiAtasan().equals(DocumentCuti.TOLAK)
                            ) {
                                findViewById(R.id.layalasantolak).setVisibility(View.VISIBLE);
                            } else {
                                findViewById(R.id.layalasantolak).setVisibility(View.GONE);
                            }


                            getDocumentUser(documentCuti.getNipPengaju());

                        } else {
                            Log.d(TAG, "Current data: null");
                        }
//                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void iniDataAtasan() {
        //init data atasan
        TextInputLayout inputLayoutAtasan = findViewById(R.id.input_atasan);
        // mengambil autocomplate view
        AutoCompleteTextView autoAtasanView = (AutoCompleteTextView) inputLayoutAtasan.getEditText();

        //array intiliaze;
        ArrayList<String> listAtasan = new ArrayList<>();

        ArrayAdapter<String> atasanAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listAtasan);
        autoAtasanView.setAdapter(atasanAdapter);


        //query users
        UsersRepository usersRepository = new UsersRepositoryImp(Users.class, FirestoreCollectionName.USERS);
        usersRepository.documentCollection().get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    listUsersAtasan.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("print", document.getId() + " => " + document.getData());
                        listUsersAtasan.add(document.toObject(Users.class));
                    }

                    listAtasan.clear();
                    ArrayList<String> localListAtasan = new ArrayList();
                    listAtasan.add("Tidak ada atasan");
                    for (Users u : listUsersAtasan) {
                        localListAtasan.add(u.getNip() + "-" + u.getNama());
                    }
                    listAtasan.addAll(localListAtasan);
                    atasanAdapter.notifyDataSetChanged();
                } else {
                    Log.d("print", "Error getting documents: ", task.getException());
                    listAtasan.clear();
                    listAtasan.add("Tidak ada atasan");
                    atasanAdapter.notifyDataSetChanged();

                }
            }
        });


    }

    private void iniDataPejabat() {
        //init data atasan
        TextInputLayout inputPejabat = findViewById(R.id.input_pejabat);
        // mengambil autocomplate view
        AutoCompleteTextView autoAtasanView = (AutoCompleteTextView) inputPejabat.getEditText();

        //array intiliaze;
        ArrayList<String> listPejabat = new ArrayList<>();

        ArrayAdapter<String> pejabatAdapater = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listPejabat);
        autoAtasanView.setAdapter(pejabatAdapater);


        //query users
        UsersRepository usersRepository = new UsersRepositoryImp(Users.class, FirestoreCollectionName.USERS);
        usersRepository.documentCollection().whereIn("role", Arrays.asList(Role.PEJABAT,Role.KETUA,Role.WAKIL_KETUA)).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    listUsersPejabat.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("print_pejabat", document.getId() + " => " + document.getData());
                        listUsersPejabat.add(document.toObject(Users.class));
                    }

                    listPejabat.clear();
                    ArrayList<String> localListPejabat = new ArrayList();
                    listPejabat.add("Tidak ada pejabat");
                    for (Users u : listUsersPejabat) {
                        Log.d("local_print", u.getNip() + " => " + u.getNama());
                        localListPejabat.add(u.getNip() + "-" + u.getNama());
                    }
                    listPejabat.addAll(localListPejabat);
                    pejabatAdapater.notifyDataSetChanged();

                    if (localListPejabat.size() == 0) {
                        tl_pejabat.getEditText().setText("Tidak ada pejabat");
                    }
                } else {
                    Log.d("print_pejabat", "Error getting documents: ", task.getException());
                    listPejabat.clear();
                    listPejabat.add("Tidak ada atasan");
                    pejabatAdapater.notifyDataSetChanged();

                }
            }
        });

    }


    private void getDocumentUser(String nipUser) {
        UsersRepository usersRepository = new UsersRepositoryImp(Users.class, FirestoreCollectionName.USERS);
        usersRepository.get(nipUser).addOnCompleteListener(new OnCompleteListener<Users>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<Users> task) {
                if (task.isSuccessful()) {
                    userDoc = task.getResult();
                    sisa_cuti.setText(userDoc.getJumlahMaximalCutiPertahun().toString());
                    in_jabatan.setText(Objects.requireNonNull(userDoc.getJabatan()));
                    System.out.println("atasan : " + userDoc.getAtasan());

                    if (userDoc.getAtasan().equals("Tidak Ada atasan")) {
                        iniDataAtasan();
                        tl_atasan.getEditText().setText("Tidak Ada atasan");
                    } else {
                        usersRepository.get(userDoc.getAtasan()).addOnCompleteListener(new OnCompleteListener<Users>() {
                            @Override
                            public void onComplete(@NonNull Task<Users> task) {
                                if (task.isSuccessful()) {
                                    Users atasan = task.getResult();
                                    tl_atasan.getEditText().setText(atasan.getNip() + "-" + atasan.getNama());
                                }
                            }
                        });

                    }


                } else {
                    Toast.makeText(getApplicationContext(), "NIP tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getUserLogin() {
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
                    userLogin = task.getResult();
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
            documentCutiUpdate.setValidasiNipKepagawaian(userLogin.getNip());
            updateVoid = repository.update(documentCutiUpdate, userLogin);

        } else if (DocumentCuti.MESSAGE_TOLAK_PEGAWAIAN.equals(status)) {
            documentCutiUpdate.setValidasiKepagawaian(DocumentCuti.TOLAK);
            documentCutiUpdate.setValidasiNipKepagawaian(userLogin.getNip());
            updateVoid = repository.update(documentCutiUpdate, userLogin);

        } else if(DocumentCuti.MESSAGE_TANGGUH_PEGAWAI.equals(status)){
            documentCutiUpdate.setValidasiKepagawaian(DocumentCuti.TANGGUHKAN);
            documentCutiUpdate.setValidasiNipKepagawaian(userLogin.getNip());
            updateVoid = repository.update(documentCutiUpdate, userLogin);

        }else if (DocumentCuti.MESSAGE_TERIMA_ATASAN.equals(status)) {
            //setAtasan
            if (tl_atasan.getEditText().getText().toString().equals("Tidak ada atasan")) {


            } else {
                String[] splitAtasan = tl_atasan.getEditText().getText().toString().split("-");
                // manupulation getIndex
                documentCuti.setValidasiNipAtasan(splitAtasan[0]);
            }


            documentCutiUpdate.setValidasiAtasan(DocumentCuti.TERIMA);

            updateVoid = repository.update(documentCutiUpdate, userDoc);

        } else if (DocumentCuti.MESSAGE_TOLAK_ATASAN.equals(status)) {

            documentCutiUpdate.setValidasiAtasan(DocumentCuti.TOLAK);
            documentCutiUpdate.setValidasiNipAtasan(userLogin.getNip());

            //setAtasan
            if (tl_atasan.getEditText().getText().toString().equals("Tidak ada atasan")) {


            } else {
                String[] splitAtasan = tl_atasan.getEditText().getText().toString().split("-");
                // manupulation getIndex
                documentCuti.setValidasiNipAtasan(splitAtasan[0]);
            }


            updateVoid = repository.update(documentCutiUpdate, userDoc);
        }else if(DocumentCuti.MESSAGE_TANGGUH_ATASAN.equals(status)){

            String[] splitAtasan = tl_atasan.getEditText().getText().toString().split("-");
            documentCuti.setValidasiNipAtasan(splitAtasan[0]);
            documentCutiUpdate.setValidasiAtasan(DocumentCuti.TANGGUHKAN);

            updateVoid = repository.update(documentCutiUpdate, userDoc);

        } else if (DocumentCuti.MESSAGE_TERIMA_PEJABAT.equals(status)) {


            //setPejabat
            if (tl_pejabat.getEditText().getText().toString().equals("Tidak ada pejabat")) {
                documentCuti.setValidasiNipPejabat(tl_pejabat.getEditText().getText().toString());

            } else {
                String[] splitAtasan = tl_pejabat.getEditText().getText().toString().split("-");
                // manupulation getIndex
                documentCuti.setValidasiNipPejabat(splitAtasan[0]);
            }


            documentCutiUpdate.setValidasiPejabat(DocumentCuti.TERIMA);


            updateVoid = repository.update(documentCutiUpdate, userDoc);

        } else if (DocumentCuti.MESSAGE_TOLAK_PEJABAT.equals(status)) {
            //setPejabat
            if (tl_pejabat.getEditText().getText().toString().equals("Tidak ada pejabat")) {
                documentCuti.setValidasiNipPejabat(tl_pejabat.getEditText().getText().toString());
            } else {
                String[] splitAtasan = tl_pejabat.getEditText().getText().toString().split("-");
                // manupulation getIndex
                documentCuti.setValidasiNipPejabat(splitAtasan[0]);
            }
            documentCutiUpdate.setValidasiPejabat(DocumentCuti.TOLAK);
            updateVoid = repository.update(documentCutiUpdate, userDoc);

        }else if(DocumentCuti.MESSAGE_TANGGUH_PEJABAT.equals(status)) {

            String[] splitAtasan = tl_atasan.getEditText().getText().toString().split("-");
            documentCuti.setValidasiNipPejabat(splitAtasan[0]);
            documentCutiUpdate.setValidasiPejabat(DocumentCuti.TANGGUHKAN);

            updateVoid = repository.update(documentCutiUpdate, userDoc);
        }else {
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
