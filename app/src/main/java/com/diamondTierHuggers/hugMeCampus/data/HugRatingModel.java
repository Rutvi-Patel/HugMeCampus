package com.diamondTierHuggers.hugMeCampus.data;

import androidx.annotation.NonNull;

import com.diamondTierHuggers.hugMeCampus.entity.HugRating;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HugRatingModel {
    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static final String branch = "hugratings";

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

    public static String addHugRating(HugRating newRating) {
        DatabaseReference ratingRef = database.getReference(branch);
        DatabaseReference newRatingRef = ratingRef.push();
        newRatingRef.setValue(newRating);

        Map<String, Object> updates = new HashMap<>();
        database.getReference("users/"+  newRating.reviewee + "/total_rating").setValue(ServerValue.increment(newRating.stars));
        database.getReference("users/"+  newRating.reviewee + "/num_reviews").setValue(ServerValue.increment(1));
        updates.put("users/"+  newRating.reviewee + "/ratings_list/" + newRatingRef.getKey(), true);
        database.getReference().updateChildren(updates);

        return newRatingRef.getKey();
    }
    public static void getRatingObjects(String uid, HugRatingDataCallback callback) {
        DatabaseReference ratingRef = database.getReference(branch);
        ratingRef.orderByChild("reviewee").equalTo(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful())
                {
                    DataSnapshot result = task.getResult();
                    if(result.exists())
                    {
                        ArrayList<HugRating> ratingsList = new ArrayList<>();
                        for(DataSnapshot rating : result.getChildren())
                        {
                            ratingsList.add(rating.getValue(HugRating.class));
                        }

                        if(ratingsList.isEmpty())
                        {
                            callback.GetRatingList(null);
                        } else {
                            callback.GetRatingList(ratingsList);
                        }
                    }
                }
                else
                {
                    callback.GetRatingList(null);
                }
            }
        });
    }
    private static String getHugRatingPath(String reviewUID) {

        return branch + "/" + reviewUID;
    }
}
