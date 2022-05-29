package com.coverteam.pta.data.repositorys;

import android.util.Log;

import androidx.annotation.NonNull;

import com.coverteam.pta.data.models.AlasanCuti;
import com.coverteam.pta.data.models.DocumentCuti;
import com.coverteam.pta.data.models.Users;
import com.coverteam.pta.data.providers.FirestoreCollectionName;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

public class DocumentCutiRepositoryImp implements DocumentCutiRepository {
    private static final String TAG = "DocumentCutiRepositoryImp";

    private final CollectionReference collectionReference;
    private final String collectionName;
    private FirebaseFirestore db;
    private final Class<DocumentCuti> docCutiClass;

    public DocumentCutiRepositoryImp(String collectionName) {
        this.collectionName = collectionName;
        this.db = FirebaseFirestore.getInstance();
        this.collectionReference = db.collection(this.collectionName);
        this.docCutiClass = DocumentCuti.class;

    }


    @Override
    public CollectionReference documentCollection() {
        return this.collectionReference;
    }

    @Override
    public Task<Void> create(DocumentCuti entity, Users user, String documentName) {
        //document name is uniqued ID for timestamp and id pengaju


        //batch write
        WriteBatch batch = db.batch();

        DocumentReference documentReference = collectionReference.document(documentName);
        Log.i(TAG, "Creating '" + documentName + "' in '" + collectionName + "'.");

        DocumentReference userRefDoc = db.collection(FirestoreCollectionName.USERS).document(entity.getNipPengaju());

        if (entity.getTypeAlasan().equals(AlasanCuti.CUTI_TAHUNAN)) {
            batch.update(userRefDoc, "jumlahMaximalCutiPertahun", user.getJumlahMaximalCutiPertahun() - entity.getListTgl().size());
        }


        batch.set(documentReference, entity);

        return batch.commit();
    }

    @Override
    public Task<Void> update(DocumentCuti entity, Users users) {
        //batch write
        WriteBatch batch = db.batch();

        DocumentReference documentReference = collectionReference.document(entity.getIdDoc());

        batch.set(documentReference, entity);

        // if tolak + pengajuan
        if (entity.getValidasiAtasan() != null &&
                entity.getValidasiPejabat() != null &&
                entity.getValidasiKepagawaian() != null &&

                entity.getValidasiPejabat().equals(DocumentCuti.TOLAK)
                && entity.getValidasiKepagawaian().equals(DocumentCuti.TOLAK)
                && entity.getValidasiAtasan().equals(DocumentCuti.TOLAK)) {
            DocumentReference userRefDoc = db.collection(FirestoreCollectionName.USERS).document(entity.getNipPengaju());

            if (entity.getTypeAlasan().equals(AlasanCuti.CUTI_TAHUNAN)) {
                batch.update(userRefDoc, "jumlahMaximalCutiPertahun", users.getJumlahMaximalCutiPertahun() + entity.getListTgl().size());

            }
        }


        return batch.commit();
    }

    @Override
    public Task<DocumentCuti> get(String id) {
        final String documentName = id;
        DocumentReference documentReference = collectionReference.document(documentName);
        Log.i(TAG, "Getting '" + documentName + "' in '" + collectionName + "'.");

        return documentReference.get().continueWith(new Continuation<DocumentSnapshot, DocumentCuti>() {
            @Override
            public DocumentCuti then(@NonNull Task<DocumentSnapshot> task) throws Exception {
                DocumentSnapshot documentSnapshot = task.getResult();
                if (documentSnapshot.exists()) {
                    return documentSnapshot.toObject(docCutiClass);
                } else {
                    Log.d(TAG, "Document '" + documentName + "' does not exist in '" + collectionName + "'.");
                    return docCutiClass.newInstance();
                }
            }
        });
    }
}
