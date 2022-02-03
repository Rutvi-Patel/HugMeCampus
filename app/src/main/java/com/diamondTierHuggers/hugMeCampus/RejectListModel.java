package com.diamondTierHuggers.hugMeCampus;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class RejectListModel {
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static final String branch = "rejected_list";
    public RejectListModel(){

    }


    public void insertRejectedUser(String uid, String uid2) {
        Map<String, Object> updates = new HashMap<>();
        updates.put(getRejectedPath(uid, uid2), true);
        database.getReference().updateChildren(updates);
    }

    public void isUserRejected(String uid, String uid2, BoolDataCallback callback) {
        DatabaseReference docRef = database.getReference(getRejectedPath(uid, uid2));
        docRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    callback.getBool(true);
                } else {
                    callback.getBool(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private String getRejectedPath(String uid, String uid2) {

        return "users/" + uid + "/" + branch + "/" + uid2;
    }

}
