package com.coverteam.pta.views.validasi.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.coverteam.pta.R;
import com.coverteam.pta.adapter.AdapterCuti;
import com.coverteam.pta.data.models.DocumentCuti;
import com.coverteam.pta.data.models.Role;
import com.coverteam.pta.data.models.Users;
import com.coverteam.pta.data.providers.FirestoreCollectionName;
import com.coverteam.pta.data.repositorys.DocumentCutiRepository;
import com.coverteam.pta.data.repositorys.DocumentCutiRepositoryImp;
import com.coverteam.pta.data.repositorys.UsersRepository;
import com.coverteam.pta.data.repositorys.UsersRepositoryImp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ValidasiByAdminView extends AppCompatActivity {

    LinearLayout cuti404;

    ListView listviewcuti;
    List<DocumentCuti> cutiList;
    AdapterCuti adapterCuti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_k_list_valid);

        listviewcuti = findViewById(R.id.listviewCuti);
        cuti404 = findViewById(R.id.cuti_not_found);

        cutiList = new ArrayList<>();
        adapterCuti = new AdapterCuti(ValidasiByAdminView.this,cutiList);
        listviewcuti.setAdapter(adapterCuti);

        listviewcuti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DocumentCuti dataCuti = cutiList.get(position);
                Intent intent = new Intent(getApplicationContext(), DetailValidasiByAdminView.class);
                intent.putExtra("cutiid",dataCuti.getIdDoc());
                intent.putExtra("role", getRole());
                startActivity(intent);
            }
        });

        getData();

    }
//    private void getInformationFromDB(Intent intent) {
//        UsersRepository usersRepository =  new UsersRepositoryImp(Users.class, FirestoreCollectionName.USERS);
//        usersRepository.get(getUsernameLocal()).addOnCompleteListener(new OnCompleteListener<Users>() {
//            @Override
//            public void onComplete(@NonNull Task<Users> task) {
//                if(task.isSuccessful()){
//                    Users localUsers =  task.getResult();
//
//
//                }
//            }
//        });
//    }
    public String getRole(){
        SharedPreferences sharedPreferences = getSharedPreferences("usernamekey", MODE_PRIVATE);
        return sharedPreferences.getString("role","");
    }

    private  void getData(){
        // repository initlaize
        DocumentCutiRepository documentCutiRepository = new DocumentCutiRepositoryImp(FirestoreCollectionName.DOCUMENT_CUTI);
        documentCutiRepository.documentCollection()
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                        //snapshot

                        if (e != null) {
                            Log.w("Listen Firebase", "Listen failed.", e);
                            return;
                        }
                        cutiList.clear();
                        Log.d("lg",value.getDocuments().toString());

                        List<DocumentCuti> listDoc = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {
                            DocumentCuti docCuti = doc.toObject(DocumentCuti.class);
                            cutiList.add(docCuti);
                        }



                        // check size > 0
                        if(cutiList.size() > 0){
                            cuti404.setVisibility(View.GONE);
                            listviewcuti.setVisibility(View.VISIBLE);
                        }else{
                            listviewcuti.setVisibility(View.GONE);
                            cuti404.setVisibility(View.VISIBLE);
                        }

                                AdapterCuti adapterCuti = (AdapterCuti) listviewcuti.getAdapter();
                                    adapterCuti.notifyDataSetChanged();
                                cuti404.setVisibility(View.GONE);
                                listviewcuti.setVisibility(View.VISIBLE);


                        Log.d("Listen Firebase", " " + listDoc.size());
                    }
                });
    }

}
