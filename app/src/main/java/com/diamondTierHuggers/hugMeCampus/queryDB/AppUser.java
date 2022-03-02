package com.diamondTierHuggers.hugMeCampus.queryDB;

import androidx.annotation.NonNull;

import com.diamondTierHuggers.hugMeCampus.data.AcceptListModel;
import com.diamondTierHuggers.hugMeCampus.entity.HugMeUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AppUser {

    private HugMeUser appUser;
    public static MatchMakingQueue mq;
    public static HashMap<String, HugMeUser> savedHugMeUsers = new HashMap<>();
    public static AcceptListModel acceptListModel = new AcceptListModel();


    public void readData(Query ref, final OnGetDataListener listener) {

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    appUser = dataSnapshot.getValue(HugMeUser.class);
//                    FirebaseAuth auth = FirebaseAuth.getInstance();
//                    appUser.setUid(auth.getUid());
                    appUser.setUid("uid123");
                    mq = new MatchMakingQueue();
                }
                listener.onSuccess("");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("ERROR: retrieving user data from db for matchmaking");
            }

        });
    }

    public HugMeUser getAppUser() {
        return appUser;
    }
}
