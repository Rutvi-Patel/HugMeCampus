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
    private static final String accepted = "accepted_list";
    private static final String requested = "request_list";
    private static final String pending = "pending_list";
    private static final String friend = "friend_list";
    private static final String blocked = "blocked_list";

    public static void insertAcceptedUser(String uid, String uid2) {
        Map<String, Object> updates = new HashMap<>();
        updates.put(getPath(accepted, uid, uid2), true);
        database.getReference().updateChildren(updates);
    }

    public static void insertRequestedUser(String uid, String uid2) {
        Map<String, Object> updates = new HashMap<>();
        updates.put(getPath(requested, uid, uid2), true);
        database.getReference().updateChildren(updates);
    }

    public static void insertPendingUser(String uid, String uid2) {
        Map<String, Object> updates = new HashMap<>();
        updates.put(getPath(pending, uid, uid2), true);
        database.getReference().updateChildren(updates);
    }

    public static void insertFriendUser(String uid, String uid2) {
        Map<String, Object> updates = new HashMap<>();
        updates.put(getPath(friend, uid, uid2), true);
        database.getReference().updateChildren(updates);
    }

    public static void insertBlockedUser(String uid, String uid2) {
        Map<String, Object> updates = new HashMap<>();
        updates.put(getPath(blocked, uid, uid2), true);
        database.getReference().updateChildren(updates);
    }

    public static void isUserAccepted(String uid, String uid2, BoolDataCallback callback) {
        DatabaseReference docRef = database.getReference(getPath(accepted, uid, uid2));
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

    private static String getPath(String branch, String uid, String uid2) {

        return "users/" + uid + "/" + branch + "/" + uid2;
    }

}
