package com.coverteam.pta.data.repositorys;

import android.util.Log;

import androidx.annotation.NonNull;

import com.coverteam.pta.data.models.Users;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UsersRepositoryImp<Users extends   Identifiable<String>> implements UsersRepository<Users, String>
{
    private static final String TAG = "UsersRepositoryImp";

    private final Class<Users> usersClass;

    private final CollectionReference collectionReference;
    private final String collectionName;


    /**
     * Initializes the repository storing the data in the given collection. Should be from {FirestoreCollections}.
     */
    public UsersRepositoryImp(Class<Users> usersClass, String collectionName) {
        this.collectionName = collectionName;
        this.usersClass = usersClass;

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        this.collectionReference = db.collection(this.collectionName);
    }


    @Override
    public Task<Boolean> exists(final String id) {
        DocumentReference documentReference = collectionReference.document(id);
        Log.i(TAG, "Checking existence of '" + id + "' in '" + collectionName + "'.");

        return documentReference.get().continueWith(new Continuation<DocumentSnapshot, Boolean>() {
            @Override
            public Boolean then(@NonNull Task<DocumentSnapshot> task) {
                Log.d(TAG,"Checking if '" + id + "' exists in '" + collectionName +"'.");
                return task.getResult().exists();
            }
        });
    }

    @Override
    public Task<Users> get(String id) {
        final String documentName = id;
        DocumentReference documentReference = collectionReference.document(documentName);
        Log.i(TAG, "Getting '" + documentName + "' in '" + collectionName + "'.");

        return documentReference.get().continueWith(new Continuation<DocumentSnapshot, Users>() {
            @Override
            public Users then(@NonNull Task<DocumentSnapshot> task) throws Exception {
                DocumentSnapshot documentSnapshot = task.getResult();
                if (documentSnapshot.exists()) {
                    return documentSnapshot.toObject(usersClass);
                } else {
                    Log.d(TAG, "Document '" + documentName + "' does not exist in '" + collectionName + "'.");
                    return usersClass.newInstance();
                }
            }
        });
    }

    @Override
    public Task<Void> create(Users entity) {
        final String documentName = entity.getEntityKey();
        DocumentReference documentReference = collectionReference.document(documentName);
        Log.i(TAG, "Creating '" + documentName + "' in '" + collectionName + "'.");
        return documentReference.set(entity).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "There was an error creating '" + documentName + "' in '" + collectionName + "'!", e);
            }
        });
    }

    @Override
    public Task<Void> update(Users entity) {
        final String documentName = entity.getEntityKey();
        DocumentReference documentReference = collectionReference.document(documentName);
        Log.i(TAG, "Updating '" + documentName + "' in '" + collectionName + "'.");

        return documentReference.set(entity).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "There was an error updating '" + documentName + "' in '" + collectionName + "'.", e);
            }
        });
    }

    @Override
    public Task<Void> delete(String id) {

        // not fitur
        return null;
    }
}
