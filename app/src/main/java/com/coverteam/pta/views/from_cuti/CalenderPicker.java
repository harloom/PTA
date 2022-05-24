package com.coverteam.pta.views.from_cuti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.coverteam.pta.R;
import com.coverteam.pta.tools.Tools;
import com.coverteam.pta.views.created_users.UsersCreateActivity;
import com.coverteam.pta.views.created_users.UsersListActivity;
import com.savvi.rangedatepicker.CalendarPickerView;
import com.savvi.rangedatepicker.SubTitle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
public class CalenderPicker extends AppCompatActivity {
    private Toolbar toolbar;
    private  CalendarPickerView calendar;


    private  Integer sisa_cuti;

    private  Boolean urgent;

    //sisa cuti
    private  TextView tv_sisa;



    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_picker);


        toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
        //back button show
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);


        sisa_cuti = getIntent().getIntExtra(FromCutiView.SISA_CUTI_INTENT,0);
        urgent = getIntent().getBooleanExtra(FromCutiView.URGENT_INTENT,false);

        tv_sisa = findViewById(R.id.sisa);


        tv_sisa.setText(getString(R.string.cuti_info) + sisa_cuti);

        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        final Calendar lastMONTH = Calendar.getInstance();
        lastMONTH.add(Calendar.DATE, -3);

        calendar = findViewById(R.id.calendar_view);
        ArrayList<Integer> list = new ArrayList<>();

        // minggu disable
        list.add(1);

        calendar.deactivateDates(list);
        ArrayList<Date> arrayList = new ArrayList<>();
        try {
            SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");

            String strdate = "22-5-2022";
            String strdate2 = "26-5-2022";

            Date newdate = dateformat.parse(strdate);
            Date newdate2 = dateformat.parse(strdate2);
//            arrayList.add(newdate);
//            arrayList.add(newdate2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        calendar.init(lastMONTH.getTime(), nextYear.getTime(), new SimpleDateFormat("MMMM, YYYY", Locale.getDefault())) //
                .inMode(CalendarPickerView.SelectionMode.RANGE) //
                .withDeactivateDates(list)
                .withSubTitles(getSubTitles())
                .withHighlightedDates(arrayList);




        calendar.scrollToDate(new Date());

    }



    private ArrayList<SubTitle> getSubTitles() {
        final ArrayList<SubTitle> subTitles = new ArrayList<>();
        final Calendar tmrw = Calendar.getInstance();
        tmrw.add(Calendar.DAY_OF_MONTH, 0);
        subTitles.add(new SubTitle(tmrw.getTime(), "10"));
        return subTitles;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_save,menu);
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                // go activity from create users
                returnValue();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    // fungsi result datePicker
    private  void returnValue(){



        //
        DataResultDate dataResultDate = new DataResultDate();


        List<Date> dateSelected = calendar.getSelectedDates();

        // validasi panjang sisa cuti dan tanggal yg di pilih

        if(dateSelected.size() > sisa_cuti){
            Toast.makeText(CalenderPicker.this,"Sisa Cuti tidak cukup, Silahkan Pilih Ulang",Toast.LENGTH_LONG).show();
            return;
        }


        dataResultDate.listDate = dateSelected;
        dataResultDate.message = "OK";

        Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_TEXT, dataResultDate);

        setResult(RESULT_OK, intent);
        finish();
    }
}