package com.coverteam.pta.data.repositorys;


import android.graphics.Bitmap;
import android.net.Uri;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.type.DateTime;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class UploadCloudStorage {

    private  StorageReference userRef;
    private  StorageReference signatureRef;
    public  static String PATH_AVATAR = "avatar/";
    public  static  String PATH_SIGNATURE = "signature/";
    FirebaseStorage storage = FirebaseStorage.getInstance();

    public UploadCloudStorage() {
    }

    public StorageReference getUserRef() {
        return userRef;
    }


    public StorageReference getSignatureRef() {
        return signatureRef;
    }

    public UploadTask uploadImage(Uri uriLocal){
        StorageReference storageRef = storage.getReference();

        userRef = storageRef.child(PATH_AVATAR+uriLocal.getLastPathSegment());
        UploadTask uploadTask = userRef.putFile(uriLocal);
        return  uploadTask;
    }

    public  UploadTask uploadSignature(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        StorageReference storageRef = storage.getReference();

        signatureRef = storageRef.child(PATH_SIGNATURE+ new Date().getTime());
        UploadTask uploadTask = signatureRef.putBytes(data);
        return  uploadTask;

    }
}
