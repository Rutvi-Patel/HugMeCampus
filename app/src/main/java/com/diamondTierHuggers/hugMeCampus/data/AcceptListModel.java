package com.diamondTierHuggers.hugMeCampus.data;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AcceptListModel {

    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static final String branch = "accepted_list";

    public static void insertAcceptedUser(String uid, String uid2) {
        Map<String, Object> updates = new HashMap<>();
        updates.put(getAcceptedPath(uid, uid2), true);
        database.getReference().updateChildren(updates);
    }

    public static void isUserAccepted(String uid, String uid2, BoolDataCallback callback) {
        DatabaseReference docRef = database.getReference(getAcceptedPath(uid, uid2));
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

    private static String getAcceptedPath(String uid, String uid2) {

        return "users/" + uid + "/" + branch + "/" + uid2;
    }

}
