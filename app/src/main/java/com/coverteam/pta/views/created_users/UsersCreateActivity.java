package com.coverteam.pta.views.created_users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.coverteam.pta.R;
import com.coverteam.pta.data.models.Role;
import com.coverteam.pta.data.models.Users;
import com.coverteam.pta.data.providers.BaseCallback;
import com.coverteam.pta.data.providers.FirestoreCollectionName;
import com.coverteam.pta.data.repositorys.UsersRepository;
import com.coverteam.pta.data.repositorys.UsersRepositoryImp;
import com.coverteam.pta.tools.CustomErrorText;
import com.coverteam.pta.tools.Tools;
import com.gk.emon.lovelyLoading.LoadingPopup;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Date;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.wajahatkarim3.easyvalidation.core.Validator;

public class UsersCreateActivity extends AppCompatActivity {

    private TextInputLayout tl_name;
    private TextInputLayout tl_nip;
    private TextInputLayout tl_role;
    private TextInputLayout tl_numberPhone;
    private TextInputLayout tl_golongan;
    private TextInputLayout tl_jabatan;
    private TextInputLayout tl_masaKerja;

    private TextInputLayout tl_maximunCuti;
    private TextInputLayout tl_atasan;

    private TextInputLayout tl_userName;
    private TextInputLayout tl_password;


    private  Button buttonSumbit;

    // array users
    private ArrayList<Users>  listUsersAtasan = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_create);
        Toolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
        //back button show
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        //call init data
        initData();


        initVar();
        initFunction();

    }


    private void initVar() {
        LoadingPopup.getInstance(UsersCreateActivity.this)
                .defaultLovelyLoading()
                .build();


         buttonSumbit = findViewById(R.id.buttonSumbit);

        tl_name = findViewById(R.id.input_name);
        tl_nip = findViewById(R.id.input_nip);
        tl_role = findViewById(R.id.input_role);

        tl_numberPhone = findViewById(R.id.input_no_handphone);
        tl_golongan = findViewById(R.id.input_golongan);
        tl_jabatan = findViewById(R.id.input_jabatan);
        tl_masaKerja = findViewById(R.id.input_masa_kerja);

        tl_maximunCuti = findViewById(R.id.input_max_cuti);
        tl_atasan = findViewById(R.id.input_atasan);


        tl_userName = findViewById(R.id.input_username);
        tl_password = findViewById(R.id.input_password);


        //listener nip
        tl_nip.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String teks = tl_nip.getEditText().getText().toString();
                tl_userName.getEditText().setText(teks);

            }
        });
    }

    private void initFunction() {

        buttonSumbit.setOnClickListener(view -> {
            // button onclick
            LoadingPopup.showLoadingPopUp();
            buttonSumbit.setEnabled(false);

            //validation
            Validator validatorName = new Validator(tl_name.getEditText().getText().toString());
           Boolean validName =  validatorName.nonEmpty(CustomErrorText.isNotEmpty("Nama")).addErrorCallback(it -> {
                        tl_name.setError(it);
                        return null;
                    }
            ).addSuccessCallback(() -> {
                tl_name.setError(null);
                return null;
            }).check();


            Validator validatorInputNIP = new Validator(tl_nip.getEditText().getText().toString());
            Boolean validNIP =validatorInputNIP.nonEmpty(CustomErrorText.isNotEmpty("NIP")).addErrorCallback(it -> {
                        tl_nip.setError(it);
                        return null;
                    }
            ).addSuccessCallback(() -> {
                tl_nip.setError(null);
                return null;
            }).check();


            Validator validatorRole = new Validator(tl_role.getEditText().getText().toString());
            Boolean validRole = validatorRole.nonEmpty(CustomErrorText.isNotEmpty("Role")).addErrorCallback(it -> {
                tl_role.setError(it);
                        return null;
                    }
            ).addSuccessCallback(() -> {
                tl_role.setError(null);
                return null;
            }).check();

            Validator validatorNumberPhone = new Validator(tl_numberPhone.getEditText().getText().toString());
            Boolean validPhoneNumber = validatorNumberPhone
                    .nonEmpty(CustomErrorText.isNotEmpty("No HP")).addErrorCallback(it -> {
                tl_numberPhone.setError(it);
                        return null;
                    }
            ).addSuccessCallback(() -> {
                tl_numberPhone.setError(null);
                return null;
            }).check();

            Validator validatorGolongan = new Validator(tl_golongan.getEditText().getText().toString());
            Boolean validGolongan = validatorGolongan.nonEmpty(CustomErrorText.isNotEmpty("Golongan")).addErrorCallback(it -> {
                tl_golongan.setError(it);
                        return null;
                    }
            ).addSuccessCallback(() -> {
                tl_golongan.setError(null);
                return null;
            }).check();

            Validator validatorJabatan = new Validator(tl_jabatan.getEditText().getText().toString());
            Boolean validJabatan =  validatorJabatan.nonEmpty(CustomErrorText.isNotEmpty("Jabatan")).addErrorCallback(it -> {
                tl_jabatan.setError(it);
                        return null;
                    }
            ).addSuccessCallback(() -> {
                tl_jabatan.setError(null);
                return null;
            }).check();


            Validator validatorMasaKerja = new Validator(tl_masaKerja.getEditText().getText().toString());
            Boolean validMasaKerja = validatorMasaKerja.nonEmpty(CustomErrorText.isNotEmpty("Masa Kerja"))
                    .validNumber("Harus Angka")
                    .addErrorCallback(it -> {
                tl_masaKerja.setError(it);
                        return null;
                    }
            ).addSuccessCallback(() -> {
                tl_masaKerja.setError(null);
                return null;
            }).check();


            Validator validatorMasaCuti = new Validator(tl_maximunCuti.getEditText().getText().toString());
            Boolean validMasaCuti = validatorMasaCuti.nonEmpty(CustomErrorText.isNotEmpty("Maximun Cuti"))
                    .validNumber("Harus Angka")
                    .addErrorCallback(it -> {
                tl_maximunCuti.setError(it);
                        return null;
                    }
            ).addSuccessCallback(() -> {
                tl_maximunCuti.setError(null);
                return null;
            }).check();



            Validator validatorAtasan = new Validator(tl_atasan.getEditText().getText().toString());
            Boolean validAtasan = validatorAtasan.nonEmpty(CustomErrorText.isNotEmpty("Atasan")).addErrorCallback(it -> {
                tl_atasan.setError(it);
                        return null;
                    }
            ).addSuccessCallback(() -> {
                tl_atasan.setError(null);
                return null;
            }).check();


            Validator validatorPassword = new Validator(tl_password.getEditText().getText().toString());
            Boolean validPassword = validatorPassword.nonEmpty(CustomErrorText.isNotEmpty("Password"))
                    .minLength(8,"Password minimal 8")
                    .addErrorCallback(it -> {
                tl_password.setError(it);
                        return null;
                    }
            ).addSuccessCallback(() -> {
                tl_password.setError(null);
                return null;
            }).check();

            //check validation
            if(!validName || !validNIP || !validRole || !validPhoneNumber
                    || !validGolongan || !validJabatan || !validMasaKerja
                    || !validMasaCuti || !validAtasan || !validPassword){
                Toast.makeText(UsersCreateActivity.this,"Silahkan Check kembali from!",Toast.LENGTH_LONG);

                buttonSumbit.setEnabled(true);
                LoadingPopup.hideLoadingPopUp();
                return;
            }


            // create button;

            //userEntity
            Users users = new Users();
            users.setNama(tl_name.getEditText().getText().toString());
            users.setNip(tl_nip.getEditText().getText().toString());
            users.setRole(tl_role.getEditText().getText().toString());
            users.setNoHandphone(tl_numberPhone.getEditText().getText().toString());
            users.setGolongan(tl_golongan.getEditText().getText().toString());
            users.setJabatan(tl_jabatan.getEditText().getText().toString());
            users.setMasaKerja(Integer.parseInt(tl_masaKerja.getEditText().getText().toString()));
            users.setJumlahMaximalCutiPertahun(Integer.parseInt(tl_maximunCuti.getEditText().getText().toString()));



            //setAtasan
            if(tl_atasan.getEditText().getText().toString() == "Tidak ada atasan"){
                users.setAtasan(tl_atasan.getEditText().getText().toString());

            }else{
                String[] splitAtasan =  tl_atasan.getEditText().getText().toString().split("-");
                // manupulation getIndex
                users.setAtasan(splitAtasan[0]);
            }

            users.setUsername(tl_userName.getEditText().getText().toString());
            users.setPassword(tl_password.getEditText().getText().toString());


            users.setTimeStamp(new Timestamp(new Date()));


            UsersRepository usersRepository =  new UsersRepositoryImp(Users.class, FirestoreCollectionName.USERS);

            //check if exits
            usersRepository.exists(users.getNip()).addOnCompleteListener(new OnCompleteListener<Boolean>() {
                @Override
                public void onComplete(@NonNull Task<Boolean> task) {
                    if(task.isSuccessful()){
                       Boolean exist =  task.getResult();
                       if(exist) {
                           Toast.makeText(UsersCreateActivity.this,"NIP Sudah Ada" ,Toast.LENGTH_LONG).show();
                       }else{
                           usersRepository.create(users, users.getNip(), new BaseCallback() {
                               @Override
                               public void onResponseCreated(String errorMessage, Object data) {
                                   Toast.makeText(UsersCreateActivity.this,errorMessage + data,Toast.LENGTH_LONG).show();
                               }

                           }).addOnCompleteListener(taskCreate -> {
                               if(taskCreate.isSuccessful()){
                                   clearData();
                                   Toast.makeText(UsersCreateActivity.this,"Akun di buat" ,Toast.LENGTH_LONG).show();
                               }
                               buttonSumbit.setEnabled(true);
                               LoadingPopup.hideLoadingPopUp();
                           });


                       }
                    }
                }
            });


            Tools.println("on Button Sumbit");


        });

    }





    //initialize data
    private void initData() {
        // init data role
        TextInputLayout inputLayoutRole = findViewById(R.id.input_role);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Role.getList());
        AutoCompleteTextView autoRoleView = (AutoCompleteTextView) inputLayoutRole.getEditText();
        autoRoleView.setAdapter(adapter);


        //init data atasan
        TextInputLayout inputLayoutAtasan = findViewById(R.id.input_atasan);
        // mengambil autocomplate view
        AutoCompleteTextView autoAtasanView = (AutoCompleteTextView) inputLayoutAtasan.getEditText();

        //array intiliaze;
        ArrayList<String> listAtasan = new ArrayList<>();

        ArrayAdapter<String> atasanAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listAtasan);
        autoAtasanView.setAdapter(atasanAdapter);


        //query users
        UsersRepository usersRepository = new UsersRepositoryImp(Users.class,FirestoreCollectionName.USERS);
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
                    for (Users u : listUsersAtasan ){
                        localListAtasan.add(u.getNip() + "-" +u.getNama());
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





        String example = "example";
        Validator validator = new Validator(example);
        validator.nonEmpty("Tidak boleh kosong");
        Boolean isOK = validator.check();

        Tools.println("validation OK " + isOK);

    }



    //init clear data
    private  void clearData(){
        tl_name.getEditText().setText("");
        tl_nip.getEditText().setText("");
        tl_role.getEditText().setText("");

        tl_numberPhone.getEditText().setText("");
        tl_golongan.getEditText().setText("");
        tl_jabatan.getEditText().setText("");
        tl_masaKerja.getEditText().setText("");

        tl_maximunCuti.getEditText().setText("");
        tl_atasan.getEditText().setText("");


        tl_userName.getEditText().setText("");
        tl_password.getEditText().setText("");
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}