package com.coverteam.pta.data.repositorys;

import com.coverteam.pta.data.models.DocumentCuti;
import com.coverteam.pta.data.models.Users;
import com.coverteam.pta.data.providers.BaseCallback;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;

public interface DocumentCutiRepository {
    CollectionReference documentCollection();


    Task<Void> create(DocumentCuti entity,Users user, String documentName);

    Task<Void> update(DocumentCuti entity ,Users user);

    Task<DocumentCuti> get(String id);
}
