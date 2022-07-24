package com.coverteam.pta.views.from_cuti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.coverteam.pta.R;
import com.coverteam.pta.data.models.DocumentCuti;
import com.coverteam.pta.data.providers.FirestoreCollectionName;
import com.coverteam.pta.data.repositorys.DocumentCutiRepository;
import com.coverteam.pta.data.repositorys.DocumentCutiRepositoryImp;
import com.coverteam.pta.tools.DateRangeTools;
import com.coverteam.pta.tools.Tools;
import com.coverteam.pta.views.created_users.UsersCreateActivity;
import com.coverteam.pta.views.created_users.UsersListActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.QuerySnapshot;
import com.savvi.rangedatepicker.CalendarPickerView;
import com.savvi.rangedatepicker.SubTitle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CalenderPicker extends AppCompatActivity {
    private Toolbar toolbar;
    private  CalendarPickerView calendar;
    private ProgressBar progressBar;

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

        final Calendar nextMonth = Calendar.getInstance();
        nextMonth.add(Calendar.MONTH, 2);

        final Calendar lastMONTH = Calendar.getInstance();
        lastMONTH.add(Calendar.DATE, -3);

        calendar = findViewById(R.id.calendar_view);
        progressBar= findViewById(R.id.loadingFetch);

        // array untuk hari minggu
        ArrayList<Integer> listDayOfWeek = new ArrayList<>();

        // minggu disable
        listDayOfWeek.add(1);

        // max anotherday in 10 count
        ArrayList<Date> listMaxDateOfCuti = new ArrayList<>();
        ArrayList<SubTitle> listCountDateOfCuti = new ArrayList<>();

        //check if urgent get loop history data
        if(!urgent){

            loadingShow();
            DocumentCutiRepository documentCutiRepository =
                    new DocumentCutiRepositoryImp(FirestoreCollectionName.DOCUMENT_CUTI);
            documentCutiRepository.documentCollection()
                    .whereGreaterThan("firstDateCuti",lastMONTH.getTime())
                    .whereLessThan("firstDateCuti",nextMonth.getTime()).get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){

                        listMaxDateOfCuti.clear();
                        listCountDateOfCuti.clear();
                      List<DocumentCuti> docs =   task.getResult().toObjects(DocumentCuti.class);
                      Log.d("docs", docs.toString());
                        ArrayList<String> timeTMP = new ArrayList<>();


                        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MMMM/yyyy", Locale.getDefault());

                        docs.forEach(documentCuti -> {
                          documentCuti.getListTgl().forEach((date -> {
                              timeTMP.add(dateFormatter.format(date));

                          }));
                      });

                        Map<String ,Long > map = timeTMP.stream()
                                .collect(  Collectors.groupingBy(c ->c , Collectors.counting())) ;


                        map.forEach(   (k , v ) -> {
                                try {
                                    Date dateE = dateFormatter.parse(k);
                                    if(v >=2 ){
                                    listMaxDateOfCuti.add(dateE);

                                    }
                                    listCountDateOfCuti.add(getSub(dateE,v.intValue()));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                        });

                    }

                    calendar.init(lastMONTH.getTime(), nextMonth.getTime(), new SimpleDateFormat("MMMM, YYYY", Locale.getDefault())) //
                            .inMode(CalendarPickerView.SelectionMode.RANGE) //
                            .withDeactivateDates(listDayOfWeek)
                            .withSubTitles(listCountDateOfCuti)
                            .withHighlightedDates(listMaxDateOfCuti);

                    calendar.scrollToDate(new Date());
                    loadingDismiss();
                }
            });

        }else{
            calendar.init(lastMONTH.getTime(), nextMonth.getTime(), new SimpleDateFormat("MMMM, YYYY", Locale.getDefault())) //
                    .inMode(CalendarPickerView.SelectionMode.RANGE) //
                    .withDeactivateDates(listDayOfWeek)
                    .withHighlightedDates(listMaxDateOfCuti);

            calendar.scrollToDate(new Date());
            loadingDismiss();
        }




    }


    private void  loadingShow(){
        calendar.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }
    private void  loadingDismiss(){
        calendar.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }


    private   SubTitle getSub(Date date ,Integer count){
        return new SubTitle(date,count.toString());
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