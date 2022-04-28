package com.diamondTierHuggers.hugMeCampus.matchmaking;

import static com.diamondTierHuggers.hugMeCampus.loginRegisterForgot.LoginFragment.appUser;

import androidx.annotation.NonNull;

import com.diamondTierHuggers.hugMeCampus.entity.HugMeUser;
import com.diamondTierHuggers.hugMeCampus.entity.HugMeUserComparator;
import com.diamondTierHuggers.hugMeCampus.main.OnGetDataListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.PriorityQueue;


public class MatchMakingQueue {

    private PriorityQueue<HugMeUser> mQueue = new PriorityQueue<HugMeUser>(50, new HugMeUserComparator());
    private String appUserUid = appUser.getAppUser().getUid();
    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://hugmecampus-dff8c-default-rtdb.firebaseio.com/");

    public void readData(Query ref, final OnGetDataListener listener) {
        System.out.println(appUser.getAppUser());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot user : dataSnapshot.getChildren()) {
                        HugMeUser h = user.getValue(HugMeUser.class);
                        String hUid = user.getKey();
                        try {
                            if (!appUserUid.equals(hUid) && !appUser.getAppUser().rejected_list.containsKey(hUid) && !appUser.getAppUser().accepted_list.containsKey(hUid) && !appUser.getAppUser().blocked_list.containsKey(hUid)
                                    && !appUser.getAppUser().request_list.containsKey(hUid) && !appUser.getAppUser().pending_list.containsKey(hUid) && !h.rejected_list.containsKey(appUserUid) && !h.blocked_list.containsKey(appUserUid)) {
                                h.setUid(hUid);
                                h.calculateMatchScore(appUser.getAppUser().hug_preferences);
                                mQueue.add(h);
                                System.out.println("added " + h);
                            }
                        }
                        catch (Exception e) {
                            System.out.println(e);
                            System.out.println(appUserUid);
                            System.out.println(hUid);
                        }
                    }
                }
                listener.onSuccess("");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("ERROR: retrieving user data from db for matchmaking");
            }

        });
    }

    @Override
    public String toString() {
        return "MatchMakingQueue{" +
                "mQueue=" + mQueue.toString() +
                '}';
    }

    public int size() {
        return mQueue.size();
    }

    public HugMeUser poll(){
        if (mQueue.isEmpty()) {
            return null;
        }
        return this.mQueue.poll();
    }
}
