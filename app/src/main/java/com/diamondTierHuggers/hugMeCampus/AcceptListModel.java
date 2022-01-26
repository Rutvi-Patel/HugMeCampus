package com.diamondTierHuggers.hugMeCampus;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class AcceptListModel {

    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private static final String acceptedListPath = "AcceptedList/test";
    private DocumentReference docRef = firestore.document(acceptedListPath);
    public boolean isAccepted;
    public AcceptListModel(){

    }


    /*
        Checks if "uid1" user 1 is in "uid2" user2's accept list
     */
    public boolean checkUserIsAccepted(String uid1, String uid2) {
        HashSet<String> acceptedSet = new HashSet<>();
        boolean result = false;
        getAcceptedUsers(uid2, new FirestoreCallback() {
            @Override
            public void GetUserAcceptList(HashSet<String> set) {
                isAccepted = set.contains(uid1);
            }
        });

        return isAccepted;
    }


    private void getAcceptedUsers(String uid, FirestoreCallback callback) {
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if(doc.exists()){
                        Map<String, Object> data = doc.getData();
                        HashSet<String> set = new HashSet<>();
                        for(Object obj : (List<Object>)data.get(uid)) {
                            set.add((String) obj);
                        }
                        callback.GetUserAcceptList(set);
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

    private interface FirestoreCallback {
        void GetUserAcceptList(HashSet<String> set);
    }

}
