package com.diamondTierHuggers.hugMeCampus;

import static com.diamondTierHuggers.hugMeCampus.LoginFragment.appUser;
import static com.diamondTierHuggers.hugMeCampus.MainActivity.database;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.diamondTierHuggers.hugMeCampus.entity.HugMeUser;
import com.diamondTierHuggers.hugMeCampus.databinding.FragmentItemBinding;
import com.diamondTierHuggers.hugMeCampus.queryDB.OnGetDataListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyListItemRecyclerViewAdapter extends RecyclerView.Adapter<MyListItemRecyclerViewAdapter.ViewHolder> {

    private List<HugMeUser> mValues = new ArrayList<>();
    private OnItemListener mOnItemListener;
    private int position = 0;

    public HugMeUser getItem(int i) {
        return mValues.get(i);
    }

    public void addItem(HugMeUser h) {
        mValues.add(h);
    }

    public void readData(Query ref, final OnGetDataListener listener) {

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot user : dataSnapshot.getChildren()) {
                        addItem(user.getValue(HugMeUser.class));
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

    public MyListItemRecyclerViewAdapter(OnItemListener onItemListener, boolean friends_list) {

        if (friends_list) {
            for (String uid : appUser.getAppUser().friend_list.keySet()) {
                readData(database.getReference("users").orderByKey().equalTo(uid), new OnGetDataListener() {
                    @Override
                    public void onSuccess(String dataSnapshotValue) {
                        System.out.println("found friends :)");
                        MyListItemRecyclerViewAdapter.super.notifyDataSetChanged();
                    }
                });
            }
        }
        else {

        }
        mOnItemListener = onItemListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), mOnItemListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.setName();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mProfileName;
        public HugMeUser mItem;
        public OnItemListener onItemListener;

        public ViewHolder(FragmentItemBinding binding, OnItemListener onItemListener) {
            super(binding.getRoot());
            this.onItemListener = onItemListener;
            this.mProfileName = binding.getRoot().findViewById(R.id.profile_name);
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemListener.onItemClick(getAdapterPosition());
        }

        public void setName() {
            this.mProfileName.setText(this.mItem.first_name + " " + this.mItem.last_name);
        }
    }

    public interface OnItemListener {
        void onItemClick(int position);
    }
}