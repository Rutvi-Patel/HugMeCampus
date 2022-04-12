package com.diamondTierHuggers.hugMeCampus;

import static com.diamondTierHuggers.hugMeCampus.LoginFragment.appUser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.diamondTierHuggers.hugMeCampus.data.HugRatingDataCallback;
import com.diamondTierHuggers.hugMeCampus.data.HugRatingModel;
import com.diamondTierHuggers.hugMeCampus.entity.HugRating;

import java.util.ArrayList;


public class HugRatingRecyclerViewAdapter extends RecyclerView.Adapter<HugRatingRecyclerViewAdapter.MyViewHolder> {

    public HugRatingRecyclerViewAdapter(String uid)
    {
        HugRatingModel.getRatingObjects(uid, new HugRatingDataCallback() {
            @Override
            public void GetHugRating(HugRating rating) {

            }

            @Override
            public void GetRatingList(ArrayList<HugRating> ratingsList) {
                if(ratingsList != null)
                {
                    for (HugRating rating: ratingsList) {
                        System.out.println(rating.reviewee);
                    }
                    appUser.savedHugRatings = ratingsList;
                    HugRatingRecyclerViewAdapter.super.notifyDataSetChanged();
                }
                else
                {
                    System.out.println("Failed to load hug ratings from DB.");
                }
            }
        });
    }

    @NonNull
    @Override
    public HugRatingRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View hugRatingView = inflater.inflate(R.layout.fragment_view_hugrating_item, parent, false);

        // Return a new holder instance
        MyViewHolder viewHolder = new MyViewHolder(hugRatingView);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull HugRatingRecyclerViewAdapter.MyViewHolder holder, int position) {
        HugRating review = appUser.savedHugRatings.get(position);
        holder.ratingBar.setRating(review.stars);
        holder.ratingBar.setIsIndicator(true);
        holder.reviewer.setText(review.reviewer);
        holder.desc.setText(review.getDesc());
    }

    @Override
    public int getItemCount() {
        return appUser.savedHugRatings.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView reviewer;
        public TextView desc;
        public RatingBar ratingBar;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public MyViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar2);
            reviewer = (TextView) itemView.findViewById(R.id.reviewer);
            desc = (TextView) itemView.findViewById(R.id.ratingDesc);
        }
    }

}
