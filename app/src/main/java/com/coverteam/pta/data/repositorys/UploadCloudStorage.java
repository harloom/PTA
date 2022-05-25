package com.coverteam.pta.data.repositorys;


import android.net.Uri;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadCloudStorage {

    final String nip;
    private  StorageReference userRef;
    public  static String PATH_AVATAR = "avatar/";
    FirebaseStorage storage = FirebaseStorage.getInstance();

    public UploadCloudStorage(String nip) {
        this.nip = nip;
    }

    public StorageReference getUserRef() {
        return userRef;
    }

    public UploadTask uploadImage(Uri uriLocal){
        StorageReference storageRef = storage.getReference();

        userRef = storageRef.child(PATH_AVATAR+uriLocal.getLastPathSegment());
        UploadTask uploadTask = userRef.putFile(uriLocal);
        return  uploadTask;
    }
}
