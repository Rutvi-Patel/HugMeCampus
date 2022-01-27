package com.diamondTierHuggers.hugMeCampus;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class AcceptListModel {

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    public AcceptListModel(){

    }

    public void getAcceptedUsersSet(String uid, AcceptedListHashSetData callback) {
        DatabaseReference docReference = database.getReference(getAcceptedPath(uid));
        docReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()) {
                    DataSnapshot doc = task.getResult();
                    if(doc.exists()){
                        callback.GetUserAcceptList(new HashSet((List<String>) doc.getValue()));
                    } else {
                        Log.d(TAG, "No data");
                    }

                } else {
                    try {
                        throw task.getException();
                    } catch(Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        });

    }

    private String getAcceptedPath(String uid) {

        return "users/" + uid + "/accepted_list";
    }
}
