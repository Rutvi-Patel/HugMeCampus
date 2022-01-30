package com.diamondTierHuggers.hugMeCampus.queryDB;

import static com.diamondTierHuggers.hugMeCampus.SecondFragment.appUser;

import androidx.annotation.NonNull;
import com.diamondTierHuggers.hugMeCampus.entity.HugMeUser;
import com.diamondTierHuggers.hugMeCampus.entity.HugMeUserComparator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.PriorityQueue;


public class MatchMakingQueue {

    private PriorityQueue<HugMeUser> mQueue = new PriorityQueue<HugMeUser>(50, new HugMeUserComparator());
    private int mCount = 0;

    public void readData(Query ref, final OnGetDataListener listener) {

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot user : dataSnapshot.getChildren()) {
                        System.out.println(user.getKey());
                        HugMeUser h = user.getValue(HugMeUser.class);
                        if (h.online) {
                            h.setUid(user.getKey());
                            h.calculateMatchScore(appUser.getAppUser().hug_preferences);
                            mQueue.add(h);
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

    public HugMeUser poll(){
        if (mQueue.isEmpty()) {
            return null;
        }
        mCount += 1;
        if (mCount == 5) {
            mCount = 0;
            // TODO get next 5 users from db and add to queue
        }
        return this.mQueue.poll();
    }
}
