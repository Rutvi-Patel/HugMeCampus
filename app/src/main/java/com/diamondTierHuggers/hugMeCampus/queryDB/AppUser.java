package com.diamondTierHuggers.hugMeCampus.queryDB;

import androidx.annotation.NonNull;

import com.diamondTierHuggers.hugMeCampus.data.AcceptListModel;
import com.diamondTierHuggers.hugMeCampus.entity.HugMeUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

public class AppUser {

    private HugMeUser appUser;
    public static MatchMakingQueue mq;
    public static HashMap<String, HugMeUser> savedHugMeUsers = new HashMap<>();
    public static AcceptListModel acceptListModel = new AcceptListModel();


    public AppUser() {

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
//                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        System.out.println("TOKEN>>>>:" + token);

                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
//                        Log.d(TAG, msg);
//                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
    }

    public void readData(Query ref, final OnGetDataListener listener) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    appUser = dataSnapshot.getValue(HugMeUser.class);
//                    FirebaseAuth auth = FirebaseAuth.getInstance();
//                    appUser.setUid(auth.getUid());
                    appUser.setUid(auth.getUid());
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
