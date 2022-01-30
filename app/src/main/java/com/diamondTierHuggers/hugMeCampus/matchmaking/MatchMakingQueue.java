package com.diamondTierHuggers.hugMeCampus.matchmaking;

import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.PriorityQueue;


public class MatchMakingQueue {

    private PriorityQueue<HugMeUser> mQueue = new PriorityQueue<HugMeUser>(50, new HugMeUserComparator());

    public MatchMakingQueue() {

//        FirebaseDatabase database = FirebaseDatabase.getInstance("https://hugmecampus-dff8c-default-rtdb.firebaseio.com/");
//        DatabaseReference myRef = database.getReference("users");
//
//
//        readData(myRef.orderByChild("age"), new OnGetDataListener() {
//            @Override
//            public void onSuccess(String dataSnapshotValue) {
//
//                // This is where you can handle the snapshot's value! (log it, add it
//                // to a list, etc.)
////                System.out.println(dataSnapshotValue);
////                finshedLoading = true;
//            }
//        });
//        System.out.println(mQueue.toString());

    }

    public void readData(Query ref, final OnGetDataListener listener) {

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot user : dataSnapshot.getChildren()) {
                        mQueue.add(user.getValue(HugMeUser.class));
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

    public String poll(){
        return this.mQueue.poll().toString();
        // TODO get next user from db and add to queue
    }
}
