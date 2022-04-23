package com.coverteam.pta.views.created_users;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.coverteam.pta.R;
import com.coverteam.pta.data.models.Role;
import com.coverteam.pta.tools.CustomErrorText;
import com.coverteam.pta.tools.Tools;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import com.wajahatkarim3.easyvalidation.core.Validator;

import kotlin.Unit;
import kotlin.jvm.functions.*;
public class UsersCreateActivity extends AppCompatActivity {

    private  TextInputLayout tl_name;

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


    private  void initVar(){
        tl_name  = findViewById(R.id.input_name);
    }

    private  void initFunction(){

      Button button =  findViewById(R.id.buttonSumbit);
      button.setOnClickListener(view -> {
          // button onclick

          //validation
          Validator validatorName =  new Validator(tl_name.getEditText().getText().toString());
          validatorName.nonEmpty(CustomErrorText.isNotEmpty("Nama")).addErrorCallback(it->{
                    tl_name.setError(it);
                    return null;
                  }
          ).addSuccessCallback(()->{
                    tl_name.setError(null);
                  return null;
          }).check();




          Tools.println("on Button Sumbit");


      });

    }
    


    //initialize data
    private  void initData(){
        // init data role
        TextInputLayout inputLayoutRole= findViewById(R.id.input_role);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,Role.getList());
        AutoCompleteTextView autoRoleView = (AutoCompleteTextView) inputLayoutRole.getEditText();
        autoRoleView.setAdapter(adapter);
        
        
        //init data atasan
        TextInputLayout inputLayoutAtasan = findViewById(R.id.input_atasan);
        // mengambil autocomplate view
        AutoCompleteTextView autoAtasanView = (AutoCompleteTextView) inputLayoutAtasan.getEditText();

        //array intiliaze;
        ArrayList<String> listAtasan = new ArrayList<>();
        listAtasan.add("Tidak Ada atasan");
        ArrayAdapter atasanAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listAtasan);
        autoAtasanView.setAdapter(atasanAdapter);



        String example = "example";
        Validator validator = new Validator(example);
        validator.nonEmpty("Tidak boleh kosong");
        Boolean isOK = validator.check();

        Tools.println("validation OK " + isOK);

    }




    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}