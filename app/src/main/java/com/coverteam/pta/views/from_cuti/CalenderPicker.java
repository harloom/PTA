package com.coverteam.pta.views.from_cuti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.coverteam.pta.R;
import com.coverteam.pta.views.created_users.UsersCreateActivity;
import com.coverteam.pta.views.created_users.UsersListActivity;
import com.savvi.rangedatepicker.CalendarPickerView;
import com.savvi.rangedatepicker.SubTitle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
public class CalenderPicker extends AppCompatActivity {
    private Toolbar toolbar;
    CalendarPickerView calendar;
    Button button;
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




        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        final Calendar lastMONTH = Calendar.getInstance();
        lastMONTH.add(Calendar.MONTH, -1);

        calendar = findViewById(R.id.calendar_view);
//        button = findViewById(R.id.get_selected_dates);
        ArrayList<Integer> list = new ArrayList<>();

        // minggu disable
        list.add(1);

        calendar.deactivateDates(list);
        ArrayList<Date> arrayList = new ArrayList<>();
        try {
            SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");

            String strdate = "22-4-2022";
            String strdate2 = "26-4-2022";

            Date newdate = dateformat.parse(strdate);
            Date newdate2 = dateformat.parse(strdate2);
            arrayList.add(newdate);
            arrayList.add(newdate2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        calendar.init(lastMONTH.getTime(), nextYear.getTime(), new SimpleDateFormat("MMMM, YYYY", Locale.getDefault())) //
                .inMode(CalendarPickerView.SelectionMode.RANGE) //
                .withDeactivateDates(list)
                .withSubTitles(getSubTitles())
                .withHighlightedDates(arrayList);




        calendar.scrollToDate(new Date());

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(CalenderPicker.this, "list " + calendar.getSelectedDates().toString(), Toast.LENGTH_LONG).show();
//            }
//        });
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
//        Tools.println(item.getItemId() +"");
        switch (item.getItemId()) {
            case R.id.save:
                // go activity from create users
                Toast.makeText(CalenderPicker.this, "list " + calendar.getSelectedDates().toString(), Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}