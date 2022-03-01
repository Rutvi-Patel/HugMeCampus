package com.diamondTierHuggers.hugMeCampus.data;

import androidx.annotation.NonNull;

import com.diamondTierHuggers.hugMeCampus.entity.HugRating;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

public class HugRatingModel {
    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static final String branch = "hugratings";

    public static String addHugRating(HugRating newRating) {
        DatabaseReference ratingRef = database.getReference(branch);
        database.getReference("users/"+  newRating.reviewee + "/total_rating").setValue(ServerValue.increment(newRating.stars));
        database.getReference("users/"+  newRating.reviewee + "/num_reviews").setValue(ServerValue.increment(1));
        DatabaseReference newRatingRef = ratingRef.push();
        newRatingRef.setValue(newRating);
        return newRatingRef.getKey();
    }

    public static void getHugRating(String ratingUID, HugRatingDataCallback callback) {
        DatabaseReference ref = database.getReference(getHugRatingPath(ratingUID));
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    HugRating rating = snapshot.getValue(HugRating.class);
                    callback.GetHugRating(rating);
                }
                else
                {
                    callback.GetHugRating(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private static String getHugRatingPath(String reviewUID) {

        return branch + "/" + reviewUID;
    }
}
