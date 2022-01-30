package com.diamondTierHuggers.hugMeCampus.queryDB;

import androidx.annotation.NonNull;

import com.diamondTierHuggers.hugMeCampus.entity.HugMeUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AppUser {

    private HugMeUser appUser;

    public void readData(Query ref, final OnGetDataListener listener) {

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    appUser = dataSnapshot.getValue(HugMeUser.class);
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
