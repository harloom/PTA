package com.coverteam.pta.views.from_cuti;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.coverteam.pta.R;
import com.coverteam.pta.d_menuUtama;
import com.coverteam.pta.data.models.AlasanCuti;
import com.coverteam.pta.data.models.DocumentCuti;
import com.coverteam.pta.data.models.StatusDocument;
import com.coverteam.pta.data.models.Users;
import com.coverteam.pta.data.providers.FirestoreCollectionName;
import com.coverteam.pta.data.repositorys.DocumentCutiRepository;
import com.coverteam.pta.data.repositorys.DocumentCutiRepositoryImp;
import com.coverteam.pta.data.repositorys.UsersRepository;
import com.coverteam.pta.data.repositorys.UsersRepositoryImp;
import com.coverteam.pta.tools.CustomErrorText;
import com.coverteam.pta.tools.HelperSize;
import com.coverteam.pta.tools.Tools;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.Timestamp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.wajahatkarim3.easyvalidation.core.Validator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class FromCutiView extends AppCompatActivity implements View.OnClickListener{

    Users usr;

    LinearLayout btn_back,ln_notice;
//    Button btn_min,btn_plus;

    Button btn_lanjut;

    Integer nilai_sisa_cuti = 0;

    TextView txsisa_cuti, txjum_hari, notice_txt;
    ImageView foto;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    String username;

    DatabaseReference reference;
    TextView in_nama,in_nip,in_jabatan,in_sisa;
    ProgressBar progressBar,progressBar0;

    EditText in_alasan,in_alamat,in_noHP;

    AutoCompleteTextView  in_type_alasan;


    // button switch penting/tidak
    SwitchMaterial button_switch;

    //listView date selected
    private ListView listViewDateSelected;
    private ArrayList<String> listDateString = new ArrayList<>();
    private ArrayList<Date> listDateSelected = new ArrayList<>();

    private static final int ACTIVITY_REQUEST_CODE_DATE = 0;

    public  static  final String SISA_CUTI_INTENT = "sisa_cuti_intent";
    public  static  final  String URGENT_INTENT = "urgent_intent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_form_cuti);

        getUsernameLocal();

        in_nama = findViewById(R.id.in_nama);
        in_nip = findViewById(R.id.in_nip);
        in_jabatan = findViewById(R.id.in_jabatan);
        in_sisa = findViewById(R.id.in_sisa);
        progressBar = findViewById(R.id.progressbar);
        progressBar0 = findViewById(R.id.progressbar0);

        TextInputLayout layout_alasan = findViewById(R.id.in_alasan);
        in_alasan = layout_alasan.getEditText();

        TextInputLayout layout_alamat =findViewById(R.id.in_alamat);
        in_alamat = layout_alamat.getEditText();

        TextInputLayout layoutNoHP =findViewById(R.id.in_NoHP);
        in_noHP = layoutNoHP.getEditText();

        foto = findViewById(R.id.fotouser);

        TextInputLayout layout_tipe_alasan = findViewById(R.id.input_type_alasan);
        in_type_alasan =  (AutoCompleteTextView)  layout_tipe_alasan.getEditText();


        ArrayAdapter<String> atasanAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                AlasanCuti.listData);
        in_type_alasan.setAdapter(atasanAdapter);

        in_type_alasan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String teks = in_type_alasan.getText().toString();
                if(teks.equals(AlasanCuti.CUTI_ALASAN_PENTING)){

                }else{
                    in_alasan.setText(teks);

                }
            }
        });


//        findViewById(R.id.in_mulai).setOnClickListener(this);
//        findViewById(R.id.in_selesai).setOnClickListener(this);




        findViewById(R.id.button_pilih_tanngal).setOnClickListener(this);

        // listview find
        listViewDateSelected  = findViewById(R.id.listDateSelected);

        // button min and plush
//        btn_min = findViewById(R.id.btnmin);
//        btn_min.setOnClickListener(this);
//
//        btn_plus = findViewById(R.id.btnplus);
//        btn_plus.setOnClickListener(this);
//        txjum_hari = findViewById(R.id.jum_cuti);


        btn_lanjut = findViewById(R.id.button_lanjut);
        btn_lanjut.setOnClickListener(this);

        btn_back = findViewById(R.id.button_back);
        btn_back.setOnClickListener(this);

        ln_notice = findViewById(R.id.ln_notice);

//        btn_min.animate().alpha(0).setDuration(300).start();


        button_switch = findViewById(R.id.switch_penting);
        button_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    Toast.makeText(FromCutiView.this,"Pengajuan Penting!",Toast.LENGTH_SHORT).show();
                } else {
                    // The toggle is disabled
                    Toast.makeText(FromCutiView.this,"Pengajuan Tidak Penting!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        getInformationFromDB();
    }

//    private void checkCutiUser() {
//        if (nilai_sisa_cuti == 0){
//            in_sisa.setTextColor(Color.parseColor("#D1206B"));
//            ln_notice.setVisibility(View.VISIBLE);
//            btn_lanjut.setVisibility(View.GONE);
//        }
//    }

    private  void onGetDateCuti(){
                Intent go = new Intent(FromCutiView.this, CalenderPicker.class);

                // intent put sisa cuti
                go.putExtra(URGENT_INTENT, button_switch.isChecked());
                go.putExtra(SISA_CUTI_INTENT,nilai_sisa_cuti);
                startActivityForResult(go,ACTIVITY_REQUEST_CODE_DATE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check that it is the SecondActivity with an OK result
        if (requestCode == ACTIVITY_REQUEST_CODE_DATE) {
            if (resultCode == RESULT_OK) {

                // get String data from Intent
                DataResultDate dateResult = data.getParcelableExtra(Intent.EXTRA_TEXT);


                listDateString.clear();
                //init data listview

                // format date
                 SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());

                 // for each
                for (Date date : dateResult.listDate) {
                    // add to array
                    listDateSelected.add(date);
                    listDateString.add(dateFormatter.format(date));
                }

                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(FromCutiView.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, listDateString);
                listViewDateSelected.setAdapter(adapter);
                HelperSize.getListViewSize(listViewDateSelected );



                Toast.makeText(FromCutiView.this,dateResult.message , Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.in_mulai:
//                showDatePicker();
//                break;
//            case R.id.in_selesai:
//                showDatePicker2();
//                break;

            case R.id.button_pilih_tanngal:
                onGetDateCuti();
                break;
//            case R.id.btnplus:
//                nilaijum_hari+=1;
//                txjum_hari.setText(nilaijum_hari.toString());
//                if (nilaijum_hari > 1){
//                    btn_min.animate().alpha(1).setDuration(300).start();
//                    btn_min.setEnabled(true);
//                }
//                if(nilaijum_hari>nilai_sisa_cuti){
//                    in_sisa.setTextColor(Color.parseColor("#D1206B"));
//                    ln_notice.setVisibility(View.VISIBLE);
//                    btn_plus.setVisibility(View.GONE);
//                    btn_lanjut.setVisibility(View.GONE);
//                }
//                break;
//            case R.id.btnmin:
//                nilaijum_hari-=1;
//                txjum_hari.setText(nilaijum_hari.toString());
//                if (nilaijum_hari < 2){
//                    btn_min.animate().alpha(0).setDuration(300).start();
//                    btn_min.setEnabled(false);
//                }
//                if(nilaijum_hari<=nilai_sisa_cuti){
//                    in_sisa.setTextColor(Color.parseColor("#5ABD8C"));
//                    ln_notice.setVisibility(View.GONE);
//                    btn_plus.setVisibility(View.VISIBLE);
//                    btn_lanjut.setVisibility(View.VISIBLE);
//                }
//                break;
            case R.id.button_back:
                Intent gomenu = new Intent(FromCutiView.this, d_menuUtama.class);
                startActivity(gomenu);
                break;
            case R.id.button_lanjut:
                saveCutiOnDB();
//                testButton();
                break;
        }
    }

    private void testButton(){

        Toast.makeText(FromCutiView.this,"Test Butoon delay 3 second",Toast.LENGTH_SHORT).show();
        btn_lanjut.setEnabled(false);
        Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    btn_lanjut.setEnabled(true);
                    Toast.makeText(FromCutiView.this,"OK",Toast.LENGTH_SHORT).show();
                }
            },3000);

    }

    private void saveCutiOnDB() {
        btn_lanjut.setEnabled(false);

        String alasancuti = in_alasan.getText().toString();
        String alamatcuti = in_alamat.getText().toString();
        String nohpcuti = in_noHP.getText().toString();

        Boolean urgent = button_switch.isChecked();
        Integer jumhari = listDateSelected.size();

        //validation

        //valid alasan
        Validator validatorAlasan= new Validator(alasancuti);
        Boolean validAlasan =  validatorAlasan.nonEmpty(CustomErrorText.isNotEmpty("Alasan")).addErrorCallback(it -> {
                    in_alasan.setError(it);
                    return null;
                }
        ).addSuccessCallback(() -> {
            in_alasan.setError(null);
            return null;
        }).check();


        //valid alamat
        Validator validatorAlamatCuti= new Validator(alamatcuti);
        Boolean validAlamatCuti =  validatorAlamatCuti.nonEmpty(CustomErrorText.isNotEmpty("Alamat Cuti")).addErrorCallback(it -> {
            in_alamat.setError(it);
                    return null;
                }
        ).addSuccessCallback(() -> {
            in_alamat.setError(null);
            return null;
        }).check();

        //valid no hp
        Validator validatorNumberPhone = new Validator(nohpcuti);
        Boolean validPhoneNumber = validatorNumberPhone
                .nonEmpty(CustomErrorText.isNotEmpty("No HP")).addErrorCallback(it -> {
                    in_noHP.setError(it);
                            return null;
                        }
                ).addSuccessCallback(() -> {
                    in_noHP.setError(null);
                    return null;
                }).check();


//        Boolean validJumlhari = jumhari > 0;
        Validator validatorJumlahHari = new Validator(Integer.toString(jumhari));
        Boolean validJumlhari = validatorJumlahHari
                .validNumber("Harus Ada tanggal")
                .greaterThan(0,"Tanggal tidak boleh Kosong")
                .addErrorCallback(it -> {
                    Toast.makeText(FromCutiView.this,it,Toast.LENGTH_SHORT).show();
                            return null;
                        }
                ).addSuccessCallback(() -> {

                    return null;
                })
                .check();

        // check if other false
        if(!validJumlhari || !validAlasan  || !validAlamatCuti || !validPhoneNumber){
            Toast.makeText(FromCutiView.this,"Silhakan Periksa from",Toast.LENGTH_SHORT).show();
            btn_lanjut.setEnabled(true);
        }else {
            // send to server
            Toast.makeText(FromCutiView.this,"Kirim...",Toast.LENGTH_SHORT).show();

            DocumentCuti documentCuti = new DocumentCuti();

            // data diri
            documentCuti.setNamaPengaju(usr.getNama());
            documentCuti.setNipPengaju(usr.getNip());
            documentCuti.setAlasan(alasancuti);
            documentCuti.setAlamat(alamatcuti);
            documentCuti.setNoHp(nohpcuti);
            documentCuti.setUrgent(urgent);
            documentCuti.setTypeAlasan(in_type_alasan.getText().toString());

            // date cuti
            documentCuti.setListTgl(listDateSelected);
            documentCuti.setJumlahCutiHari(listDateSelected.size());


            // instance calender
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            documentCuti.setTahun(String.valueOf(calendar.get(Calendar.YEAR)));
            documentCuti.setBulan(String.valueOf(calendar.get(Calendar.MONTH)));


            // status
            documentCuti.setStatus(StatusDocument.PROGRESS);


            // sent to firebase

            DocumentCutiRepository documentCutiRepository = new DocumentCutiRepositoryImp(FirestoreCollectionName.DOCUMENT_CUTI);
            documentCutiRepository.create(documentCuti, usr,usr.getNip() + '-' + calendar.getTimeInMillis())
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(FromCutiView.this,"Success...",Toast.LENGTH_SHORT).show();

                        // clear all
                        clearForm();
                    }else{
                        // error handle
                        Toast.makeText(FromCutiView.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                    btn_lanjut.setEnabled(true);
                }

            });



        }





//        if(validateInputs(alasancuti,alamatcuti,nohpcuti,tglmulaicuti,tglselesaicuti)){
//            progressBar.setVisibility(View.VISIBLE);
//            reference = FirebaseDatabase.getInstance().getReference("Pengajuan_Cuti");
//            idcuti = reference.push().getKey();
//            DataCuti cuti = new DataCuti(idcuti,masuknama,masuknip,masukjabatan,alasancuti,alamatcuti,nohpcuti,tglmulaicuti,tglselesaicuti,cutistatus,tolakalasan,suratcuti,username,jumhari);
//            reference.child(idcuti).setValue(cuti);
//            saveCutiUser();
//
//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    progressBar.setVisibility(View.GONE);
//                    Intent intent = new Intent(FromCutiView.this, g_pengajuan_terkirim.class);
//                    intent.putExtra("cutiid",idcuti);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
//                    finish();
//                }
//            },3000);
//
//        }


    }


//    private void saveCutiUser() {
//
//
////        reference = FirebaseDatabase.getInstance().getReference()
////                .child("pegawai2").child(username_key_new);
////        reference.addListenerForSingleValueEvent(new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                try {
////                    dataSnapshot.getRef().child("SISA_CUTI").setValue(nilai_sisa_cuti-jumhari);
////                }
////                catch (Exception e){
////                    Toast.makeText(getApplicationContext(), "Terjadi Kesalahan ngurang", Toast.LENGTH_LONG).show();
////                    e.printStackTrace();
////                }
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError databaseError) {
////
////            }
////        });
//    }


    private void getInformationFromDB() {
        btn_lanjut.setEnabled(false);

        UsersRepository usersRepository =  new UsersRepositoryImp(Users.class, FirestoreCollectionName.USERS);
        usersRepository.get(username_key_new).addOnCompleteListener(new OnCompleteListener<Users>() {
            @Override
            public void onComplete(@NonNull Task<Users> task) {
                if(task.isSuccessful()){
                    btn_lanjut.setEnabled(true);
                     usr =  task.getResult();
                in_nama.setText(Objects.requireNonNull(usr.getNama()));
                in_nip.setText(Objects.requireNonNull(usr.getNip()));
                in_jabatan.setText(Objects.requireNonNull(usr.getJabatan()));
                nilai_sisa_cuti = usr.getJumlahMaximalCutiPertahun();
                in_sisa.setText(nilai_sisa_cuti.toString());
                username = usr.getUsername();

//                checkCutiUser();
                Picasso.with(FromCutiView.this)
                        .load(usr.getFoto())
                        .into(foto, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {
                                Toast.makeText(getApplicationContext(),"Gagal Memuat Foto",Toast.LENGTH_SHORT).show();
                            }
                        });
                    progressBar0.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                }else{
                    btn_lanjut.setEnabled(false);
                }
            }
        });



//        reference = FirebaseDatabase.getInstance().getReference()
//                .child("pegawai2").child(username_key_new);
//        reference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                in_nama.setText(Objects.requireNonNull(dataSnapshot.child("NAMA").getValue()).toString());
//                in_nip.setText(Objects.requireNonNull(dataSnapshot.child("NIP").getValue()).toString());
//                in_jabatan.setText(Objects.requireNonNull(dataSnapshot.child("JABATAN").getValue()).toString());
//                in_sisa.setText(Objects.requireNonNull(dataSnapshot.child("SISA_CUTI").getValue()).toString());
//                username = dataSnapshot.child("USERNAME").getValue().toString();
//                nilai_sisa_cuti = Integer.valueOf(in_sisa.getText().toString());
//                checkCutiUser();
//                Picasso.with(FromCutiView.this)
//                        .load(dataSnapshot.child("FOTO").getValue().toString())
//                        .into(foto, new Callback() {
//                            @Override
//                            public void onSuccess() {
//                                progressBar0.setVisibility(View.GONE);
//                            }
//
//                            @Override
//                            public void onError() {
//                                Toast.makeText(getApplicationContext(),"Gagal Memuat Foto",Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                progressBar.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
//            }
//        });
    }

    private  void clearForm(){
        in_alasan.setText("");
        in_alamat.setText("");
        in_noHP.setText("");
        button_switch.setChecked(false);
        listDateString.clear();
        listDateSelected.clear();

        ArrayAdapter adapter = (ArrayAdapter) listViewDateSelected.getAdapter();
        if(adapter != null){
            adapter.notifyDataSetChanged();
        }


        getInformationFromDB();
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }
}
