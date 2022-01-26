package com.diamondTierHuggers.hugMeCampus.matchmaking;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.PriorityQueue;


public class MatchMakingQueue {

    private PriorityQueue<HugMeUser> mQueue = new PriorityQueue<HugMeUser>(50, new HugMeUserComparator());

    public MatchMakingQueue() {

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://hugmecampus-dff8c-default-rtdb.firebaseio.com/");
        DatabaseReference myRef = database.getReference("users");


//        Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
//        myRef.orderByChild("userEmail").equalTo(user.getEmail()).addListenerForSingleValueEvent(new ValueEventListener()) {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//                    User user1 = userSnapshot.getValue(User.class);
//                }
//            }
//        }
    }

    public String poll(){
        this.mQueue.poll();
        // TODO get next user from db and add to queue
    }
}
