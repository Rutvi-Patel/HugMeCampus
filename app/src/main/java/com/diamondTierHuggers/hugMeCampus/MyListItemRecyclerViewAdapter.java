package com.diamondTierHuggers.hugMeCampus;

import static com.diamondTierHuggers.hugMeCampus.LoginFragment.appUser;
import static com.diamondTierHuggers.hugMeCampus.MainActivity.database;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
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

    public void readData(Query ref, String uid, int requestPending, final OnGetDataListener listener) {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot user : dataSnapshot.getChildren()) {
                        HugMeUser h = user.getValue(HugMeUser.class);
                        h.setUid(uid);
                        appUser.savedHugMeUsers.put(uid, h);
                        addItem(h);
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
                if (appUser.savedHugMeUsers.containsKey(uid)) {
                    addItem(appUser.savedHugMeUsers.get(uid));
                }
                else {
                    readData(database.getReference("users").orderByKey().equalTo(uid), uid, 0, new OnGetDataListener() {
                        @Override
                        public void onSuccess(String dataSnapshotValue) {
                            MyListItemRecyclerViewAdapter.super.notifyDataSetChanged();
                        }
                    });
                }
            }
        }
        else {
            for (String uid : appUser.getAppUser().request_list.keySet()) {
                if (appUser.savedHugMeUsers.containsKey(uid)) {
                    addItem(appUser.savedHugMeUsers.get(uid));
                }
                else {
                    readData(database.getReference("users").orderByKey().equalTo(uid), uid, 1, new OnGetDataListener() {
                        @Override
                        public void onSuccess(String dataSnapshotValue) {
                            MyListItemRecyclerViewAdapter.super.notifyDataSetChanged();
                        }
                    });
                }
            }
            for (String uid : appUser.getAppUser().pending_list.keySet()) {
                if (appUser.savedHugMeUsers.containsKey(uid)) {
                    addItem(appUser.savedHugMeUsers.get(uid));
                }
                else {
                    readData(database.getReference("users").orderByKey().equalTo(uid), uid, 2, new OnGetDataListener() {
                        @Override
                        public void onSuccess(String dataSnapshotValue) {
                            MyListItemRecyclerViewAdapter.super.notifyDataSetChanged();
                        }
                    });
                }
            }
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
        holder.set();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mProfileName, mRequestPendingText;
        public HugMeUser mItem;
        public OnItemListener onItemListener;
        public CardView mRequestPendingCard;

        public ViewHolder(FragmentItemBinding binding, OnItemListener onItemListener) {
            super(binding.getRoot());
            this.onItemListener = onItemListener;
            this.mProfileName = binding.getRoot().findViewById(R.id.profile_name);
            this.mRequestPendingText = binding.getRoot().findViewById(R.id.request_pending);
            this.mRequestPendingCard = binding.getRoot().findViewById(R.id.request_pending_card);
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemListener.onItemClick(getAdapterPosition());
        }

        public void set() {
            this.mProfileName.setText(this.mItem.first_name + " " + this.mItem.last_name);
            if (mItem.getFriendRequestPending() == 2) {
                this.mRequestPendingText.setText("Accept Request");
                this.mRequestPendingText.setTextColor(0xff34223b);
                this.mRequestPendingCard.setCardBackgroundColor(0xff03dac5);
            }
            else if (mItem.getFriendRequestPending() == 1) {
                this.mRequestPendingText.setText("Pending");
                this.mRequestPendingText.setTextColor(0xffffffff);
                this.mRequestPendingCard.setCardBackgroundColor(0xff34223b);
            }
        }
    }

    public interface OnItemListener {
        void onItemClick(int position);
    }
}