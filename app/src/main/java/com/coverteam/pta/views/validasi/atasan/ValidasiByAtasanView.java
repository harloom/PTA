package com.coverteam.pta.views.validasi.atasan;

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
import android.widget.Toast;

import com.coverteam.pta.R;
import com.coverteam.pta.adapter.AdapterCuti;
import com.coverteam.pta.d_menuUtama;
import com.coverteam.pta.data.models.DocumentCuti;
import com.coverteam.pta.data.models.Role;
import com.coverteam.pta.data.models.Users;
import com.coverteam.pta.data.providers.FirestoreCollectionName;
import com.coverteam.pta.data.repositorys.DocumentCutiRepository;
import com.coverteam.pta.data.repositorys.DocumentCutiRepositoryImp;
import com.coverteam.pta.data.repositorys.UsersRepository;
import com.coverteam.pta.data.repositorys.UsersRepositoryImp;
import com.coverteam.pta.tools.CustomMask;
import com.coverteam.pta.views.validasi.admin.DetailValidasiByAdminView;
import com.coverteam.pta.views.validasi.admin.ValidasiByAdminView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.DocumentCollections;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ValidasiByAtasanView extends AppCompatActivity {
    private  LinearLayout cuti404;

    private ListView listviewcuti;
    private List<DocumentCuti> cutiList;
    private AdapterCuti adapterCuti;

    private  Users users;
    private ArrayList<String> bawahanId = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validasi_by_atasan_view);

        getInformationFromDB();
        listviewcuti = findViewById(R.id.listviewCuti);
        cuti404 = findViewById(R.id.cuti_not_found);

        cutiList = new ArrayList<>();
        adapterCuti = new AdapterCuti(ValidasiByAtasanView.this,cutiList);
        listviewcuti.setAdapter(adapterCuti);

        listviewcuti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DocumentCuti dataCuti = cutiList.get(position);
                Intent intent = new Intent(getApplicationContext(), DetailValidasiByAdminView.class);
                intent.putExtra("cutiid",dataCuti.getIdDoc());
                intent.putExtra("role",users.getRole());
                startActivity(intent);
            }
        });

    }


    private void  getBawahan(){
        CollectionReference collectionReference = new UsersRepositoryImp(Users.class,FirestoreCollectionName.USERS).documentCollection();

        collectionReference.whereEqualTo("atasan" , getUsernameLocal()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    ArrayList<Users> users = new ArrayList<>();
                    bawahanId.clear();
                    QuerySnapshot q = task.getResult();
                    for (QueryDocumentSnapshot doc : q) {
                        Users user = doc.toObject(Users.class);
                        users.add(user);
                        bawahanId.add(user.getNip());
                    }
                    if(bawahanId.size() >0){
                        getData();
                    }

                }else{
                    Log.d("users","Bawahan tida di temukan");
                }
            }
        });


    }

    private  void getData(){
        // repository initlaize
        Log.d("fillter_bawahab",bawahanId.toString());
        DocumentCutiRepository documentCutiRepository = new DocumentCutiRepositoryImp(FirestoreCollectionName.DOCUMENT_CUTI);
        documentCutiRepository.documentCollection()
                .whereIn("nipPengaju",bawahanId)
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

                        Collections.reverse(cutiList);
                        AdapterCuti adapterCuti = (AdapterCuti) listviewcuti.getAdapter();
                        adapterCuti.notifyDataSetChanged();
                        cuti404.setVisibility(View.GONE);
                        listviewcuti.setVisibility(View.VISIBLE);


                        Log.d("Listen Firebase", " " + listDoc.size());
                    }
                });
    }


    public String getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences("usernamekey", MODE_PRIVATE);

        return sharedPreferences.getString("","");
    }

    private void getInformationFromDB() {
        UsersRepository usersRepository =  new UsersRepositoryImp(Users.class, FirestoreCollectionName.USERS);
        usersRepository.get(getUsernameLocal()).addOnCompleteListener(new OnCompleteListener<Users>() {
            @Override
            public void onComplete(@NonNull Task<Users> task) {
                if(task.isSuccessful()){
                    Users localUsers =  task.getResult();
                    users = localUsers;
                    getBawahan();
                }
            }
        });
    }
}