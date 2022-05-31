package com.coverteam.pta.views.signature;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.coverteam.pta.R;
import com.coverteam.pta.data.models.Users;
import com.coverteam.pta.data.providers.FirestoreCollectionName;
import com.coverteam.pta.data.repositorys.UploadCloudStorage;
import com.coverteam.pta.data.repositorys.UsersRepository;
import com.coverteam.pta.data.repositorys.UsersRepositoryImp;
import com.gk.emon.lovelyLoading.LoadingPopup;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import se.warting.signatureview.views.CustomSignaturePad;
import se.warting.signatureview.views.SignedListener;

public class SignatureActivity extends AppCompatActivity {
    private  Users user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);

        user = getIntent().getParcelableExtra("user");

        if(user == null){
            finish();
        }


        Button mSaveButton = findViewById(R.id.save_button);
        Button mClearButton = findViewById(R.id.clear_button);
        CustomSignaturePad mSignaturePad = findViewById(R.id.signature_pad);

        LoadingPopup.getInstance(SignatureActivity.this)
                .defaultLovelyLoading()
                .build();
        mSignaturePad.setOnSignedListener(new SignedListener() {
            @Override
            public void onStartSigning() {
                Toast.makeText(SignatureActivity.this, "OnStartSigning", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSigned() {
                mSaveButton.setEnabled(true);
                mClearButton.setEnabled(true);
            }

            @Override
            public void onClear() {
                mSaveButton.setEnabled(false);
                mClearButton.setEnabled(false);
            }
        });

        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignaturePad.clear();
            }
        });
       mSaveButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
//               LoadingPopup.showLoadingPopUp();
               Bitmap signatureBitmap = mSignaturePad.getSignatureBitmap();
               String signatureSvg = mSignaturePad.getSignatureSvg(48,48);





//                   SVGImageView

                   UploadCloudStorage cloud = new UploadCloudStorage();
                   cloud.uploadSignature(signatureBitmap).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                       @Override
                       public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                           double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                           System.out.println("Progress Singatur : " +progress);
                       }
                   })

                           .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                               @Override
                               public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                   if (!task.isSuccessful()) {
                                       throw task.getException();
                                   }

                                   // Continue with the task to get the download URL
                                   return cloud.getSignatureRef().getDownloadUrl();
                               }
                           }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                       @Override
                       public void onComplete(@NonNull Task<Uri> task) {
                           if (task.isSuccessful()) {
                               Uri downloadUri = task.getResult();
                               updateSignature(downloadUri.toString(),signatureSvg);

                           } else {
                               // Handle failures
                               // ...
                           }
                       }
                   });

           }
       });
    }

    // update avatar
    private void updateSignature(String uri , String svg) {
        UsersRepository usersRepository = new UsersRepositoryImp(Users.class, FirestoreCollectionName.USERS);
        user.setSignature(uri);
        user.setSignatureSVG(svg);
        usersRepository.update(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignatureActivity.this, "Update Signature", Toast.LENGTH_LONG).show();
                        finish();
                } else {
                    Toast.makeText(SignatureActivity.this, "Gagal Update Foto", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}