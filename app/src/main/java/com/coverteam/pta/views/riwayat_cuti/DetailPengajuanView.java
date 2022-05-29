package com.coverteam.pta.views.riwayat_cuti;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.coverteam.pta.R;
import com.coverteam.pta.data.models.DocumentCuti;
import com.coverteam.pta.data.models.Users;
import com.coverteam.pta.data.providers.FirestoreCollectionName;
import com.coverteam.pta.data.repositorys.DocumentCutiRepository;
import com.coverteam.pta.data.repositorys.DocumentCutiRepositoryImp;
import com.coverteam.pta.data.repositorys.UsersRepository;
import com.coverteam.pta.data.repositorys.UsersRepositoryImp;
import com.coverteam.pta.printer.DocumentPrint;
import com.coverteam.pta.tools.HelperSize;
import com.coverteam.pta.views.from_cuti.FromCutiView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Source;
import com.google.firebase.firestore.Transaction;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class DetailPengajuanView extends AppCompatActivity implements View.OnClickListener{

    private static final int PERMISSION_STORAGE_CODE = 1000;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";


    String idcuti;


    //listView date selected
    private ListView listViewDateSelected;
    private ArrayList<String> listDateString = new ArrayList<>();

    private DocumentCuti documentCuti;
    private  Users usr;
    private  Users dataPejabat;
    private  Users dataAtasan;

    TextView txsisacuti,in_nama,in_nip,in_jabatan,in_alasan,in_alamat,in_nohp,in_tgl_mulai,in_tgl_selesai;

    TextView in_type_alasan , pentingVaribale;
    TextView statuspegawai,statusatasan,statuspejabat,txalasantolak;
    LinearLayout layalasan;
    ProgressBar progressBar;
    Button download;

    private String TAG = "DetailPengajuan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g_pengajuan_terkirim);

        getUsernameLocal();
        Intent intent = getIntent();
        idcuti = intent.getStringExtra("cutiid");
        if(idcuti == null){
            finish();
        }

        txsisacuti = findViewById(R.id.sisa_cuti);
        in_nama = findViewById(R.id.in_nama);
        in_nip = findViewById(R.id.in_nip);
        in_jabatan = findViewById(R.id.in_jabatan);
        in_alamat = findViewById(R.id.in_alamat);
        in_type_alasan = findViewById(R.id.in_type_alasan);
        pentingVaribale = findViewById(R.id.pentingVaribale);
        in_alasan = findViewById(R.id.in_alasan);
        in_nohp = findViewById(R.id.in_hp);

        statuspegawai = findViewById(R.id.statuspegawai);
        statusatasan = findViewById(R.id.statusatasan);
        statuspejabat = findViewById(R.id.statuspejabat);
        progressBar = findViewById(R.id.progressbar);
        txalasantolak = findViewById(R.id.txalasantolak);
        layalasan = findViewById(R.id.layalasantolak);
        download = findViewById(R.id.download);

        // listview find
        listViewDateSelected  = findViewById(R.id.listDateSelected);

        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.download).setOnClickListener(this);
        getUser();
        getPengajuanCutiFromDB();
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                Intent home = new Intent(DetailPengajuanView.this, RiwayatCutiView.class);
                startActivity(home);
                break;
            case R.id.download:
                DocumentPrint documentPrint = new DocumentPrint(documentCuti);
                documentPrint.setDataPengaju(usr);
                documentPrint.setDataAtasan(dataAtasan);
                documentPrint.setDataPejabat(dataPejabat);
                documentPrint.print(DetailPengajuanView.this);
                break;
        }
    }

//    private void downloadFile() {
//        if (!downloadfile.equals("")){
//            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
//                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
//                requestPermissions(permissions,PERMISSION_STORAGE_CODE);
//            }
//            else {
//                startDownload();
//            }
//        }
//        else {
//            Toast.makeText(getApplicationContext(), "Surat Cuti Belum Dikirim Admin!", Toast.LENGTH_LONG).show();
//        }
//
//    }
//
//    private void startDownload() {
//        String fileExtension = MimeTypeMap.getFileExtensionFromUrl(downloadfile);
//        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadfile));
//        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
//        request.setTitle("Download");
//        request.setDescription("Mendownload Surat Cuti ...");
//        request.allowScanningByMediaScanner();
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,""+System.currentTimeMillis()+"."+fileExtension);
//
//        DownloadManager manager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
//        manager.enqueue(request);
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_STORAGE_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    startDownload();
                } else {
                    Toast.makeText(this, "Gagal Mendownload File, PERMISSION DENIED", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    private  void getUser(){
        UsersRepository usersRepository =  new UsersRepositoryImp(Users.class, FirestoreCollectionName.USERS);
        usersRepository.get(username_key_new).addOnCompleteListener(new OnCompleteListener<Users>() {
            @Override
            public void onComplete(@NonNull Task<Users> task) {
                if(task.isSuccessful()){
                    usr =  task.getResult();
                    txsisacuti.setText(usr.getJumlahMaximalCutiPertahun().toString());
                    in_jabatan.setText(Objects.requireNonNull(usr.getJabatan()));
                }else{
                    Toast.makeText(getApplicationContext(),"NIP tidak ditemukan",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private  void getAtasanAndPejabat(String nipAtasan , String nipPejabat){
        DocumentReference pejabatRef = null;
        DocumentReference atasanRef = null;
        if(documentCuti.getValidasiNipPejabat() !=null && !documentCuti.getValidasiNipPejabat().equals("")) {
             pejabatRef = new UsersRepositoryImp(Users.class,FirestoreCollectionName.USERS).documentCollection().document(nipPejabat);

        }


        if(    documentCuti.getValidasiNipAtasan() !=null && !documentCuti.getValidasiNipAtasan().equals("")){
            atasanRef = new UsersRepositoryImp(Users.class,FirestoreCollectionName.USERS).documentCollection().document(nipAtasan);

        }


        UsersRepositoryImp usersRepository =  new UsersRepositoryImp(Users.class, FirestoreCollectionName.USERS);
        DocumentReference finalPejabatRef = pejabatRef;
        DocumentReference finalAtasanRef = atasanRef;
        usersRepository.getDb().runTransaction(new Transaction.Function<Void>() {
            @Override
            public Void apply(Transaction transaction) throws FirebaseFirestoreException {

                if(finalPejabatRef !=null){
                    DocumentSnapshot snapshotPejabat = transaction.get(finalPejabatRef);

                    // check null
                    dataPejabat = snapshotPejabat.toObject(Users.class);
                }

                if(finalAtasanRef !=null){
                    DocumentSnapshot snapshotAtasan = transaction.get(finalAtasanRef);
                    dataAtasan = snapshotAtasan.toObject(Users.class);
                }


                // Success
                return null;
            }


        }).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        System.out.println("get pejabat and atasan Successfull");
//                        System.out.println(task.getResult().toString());
                    }else{
                        System.out.println("get pejabat and atasan gagal");
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
                            Log.d(TAG, "Current data: " + snapshot.toObject(DocumentCuti.class));
                             documentCuti = snapshot.toObject(DocumentCuti.class);
                             in_nama.setText(Objects.requireNonNull(documentCuti.getNamaPengaju()));
                            in_nip.setText(Objects.requireNonNull(documentCuti.getNipPengaju()));

                            in_alamat.setText(Objects.requireNonNull(documentCuti.getAlamat()));
                            in_alasan.setText(Objects.requireNonNull(documentCuti.getAlasan()));
                            in_nohp.setText(Objects.requireNonNull(documentCuti.getNoHp()));
                            in_type_alasan.setText(documentCuti.getTypeAlasan());
                            pentingVaribale.setText(documentCuti.getUrgent() ? "Penting" : "");


                            // format date
                            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
                            listDateString.clear();

                            // for each
                            for (Date date : documentCuti.getListTgl()) {
                                // add to array
                                listDateString.add(dateFormatter.format(date));
                            }

                            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(DetailPengajuanView.this,
                                    android.R.layout.simple_list_item_1, android.R.id.text1, listDateString);
                            listViewDateSelected.setAdapter(adapter);
                            HelperSize.getListViewSize(listViewDateSelected );


                            //validasi
                            if(documentCuti.getValidasiKepagawaian() != null && documentCuti.getValidasiKepagawaian().equals(DocumentCuti.TERIMA)) {
                                statuspegawai.setText(DocumentCuti.MESSAGE_TERIMA_PEGAWAIAN);
                                statuspegawai.setTextColor(Color.parseColor(DocumentCuti.colorTerima));
                            }else if(documentCuti.getValidasiKepagawaian() !=null && documentCuti.getValidasiKepagawaian().equals(DocumentCuti.TOLAK)){
                                statuspegawai.setText(DocumentCuti.MESSAGE_TOLAK_PEGAWAIAN);
                                statuspegawai.setTextColor(Color.parseColor(DocumentCuti.colorTOLAK));
                            }else{
                                statuspegawai.setText(DocumentCuti.MESSAGE_MENUNGGU_PEGAWAIAN);
                                statuspegawai.setTextColor(Color.parseColor(DocumentCuti.colorMenunggu));
                            }

                            if(documentCuti.getValidasiAtasan() != null &&  documentCuti.getValidasiAtasan().equals(DocumentCuti.TERIMA)) {
                                statusatasan.setText(DocumentCuti.MESSAGE_TERIMA_ATASAN);
                                statusatasan.setTextColor(Color.parseColor(DocumentCuti.colorTerima));
                            }else if(documentCuti.getValidasiAtasan() != null &&  documentCuti.getValidasiAtasan().equals(DocumentCuti.TOLAK)){
                                statusatasan.setText(DocumentCuti.MESSAGE_TOLAK_ATASAN);
                                statusatasan.setTextColor(Color.parseColor(DocumentCuti.colorTOLAK));
                            }else{
                                statusatasan.setText(DocumentCuti.MESSAGE_MENUNGGU_PERSETUJUAN);
                                statusatasan.setTextColor(Color.parseColor(DocumentCuti.colorMenunggu));
                            }

                            if(documentCuti.getValidasiPejabat() != null &&  documentCuti.getValidasiPejabat().equals(DocumentCuti.TERIMA)) {
                                statuspejabat.setText(DocumentCuti.MESSAGE_TERIMA_PEJABAT);
                                statuspejabat.setTextColor(Color.parseColor(DocumentCuti.colorTerima));
                            }else if(documentCuti.getValidasiPejabat() != null && documentCuti.getValidasiPejabat().equals(DocumentCuti.TOLAK)){
                                statuspejabat.setText(DocumentCuti.MESSAGE_TOLAK_PEJABAT);
                                statuspejabat.setTextColor(Color.parseColor(DocumentCuti.colorTOLAK));
                            }else{
                                statuspejabat.setText(DocumentCuti.MESSAGE_MENUNGGU_PERSETUJUAN);
                                statuspejabat.setTextColor(Color.parseColor(DocumentCuti.colorMenunggu));
                            }


//                            if((documentCuti.getValidasiKepagawaian() != null &&
//                                documentCuti.getValidasiAtasan() !=null && documentCuti.getValidasiPejabat() != null)
//                                &&(documentCuti.getValidasiKepagawaian().equals(DocumentCuti.TERIMA) &&
//                                    documentCuti.getValidasiAtasan().equals(DocumentCuti.TERIMA) &&
//                                    documentCuti.getValidasiPejabat().equals(DocumentCuti.TERIMA))
//                            ){
//                                download.setVisibility(View.VISIBLE);
//                            }else{
//                                download.setVisibility(View.GONE);
//                            }


                            //data atasan
                            if(documentCuti.getValidasiNipAtasan() !=null && documentCuti.getValidasiPejabat() !=null){
                                getAtasanAndPejabat(documentCuti.getValidasiNipAtasan(),documentCuti.getValidasiNipPejabat());

                            }

                        } else {
                            Log.d(TAG, "Current data: null");
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });


    }



    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }
}
