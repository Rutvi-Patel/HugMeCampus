package com.diamondTierHuggers.hugMeCampus.messages;

import static com.diamondTierHuggers.hugMeCampus.loginRegisterForgot.LoginFragment.appUser;
import static com.diamondTierHuggers.hugMeCampus.main.LoginRegisterActivity.database;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.diamondTierHuggers.hugMeCampus.R;
import com.diamondTierHuggers.hugMeCampus.databinding.FragmentMessagesAdapterLayoutBinding;
import com.diamondTierHuggers.hugMeCampus.entity.HugMeUser;
import com.diamondTierHuggers.hugMeCampus.main.OnGetDataListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

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

    public MessagesAdapter(OnItemListener onItemListener) {
//        System.out.println("begin reload messages");

            database.getReference().child("messages").child(appUser.getAppUser().getUid()).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    if (snapshot.exists()){
                            String uid = snapshot.getKey();
//                            System.out.println(uid);
                            if (appUser.savedHugMeUsers.containsKey(uid)) {
                                addItem(appUser.savedHugMeUsers.get(uid));
                                MessagesAdapter.super.notifyDataSetChanged();
                            }
                            else {
                                readData(database.getReference("users").orderByKey().equalTo(uid), uid, 0, new OnGetDataListener() {
                                    @Override
                                    public void onSuccess(String dataSnapshotValue) {
                                        MessagesAdapter.super.notifyDataSetChanged();
                                    }
                                });
                            }
                    }

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



        mOnItemListener = onItemListener;
    }

//    @Override
//    public void onViewAttachedToWindow(ViewHolder holder) {
//        database.getReference().child("messages").child(appUser.getAppUser().getUid()).addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                if (snapshot.exists()){
//                    String uid = snapshot.getKey();
////                            System.out.println(uid);
//                    if (appUser.savedHugMeUsers.containsKey(uid)) {
//                        addItem(appUser.savedHugMeUsers.get(uid));
//                    }
//                    else {
//                        readData(database.getReference("users").orderByKey().equalTo(uid), uid, 0, new OnGetDataListener() {
//                            @Override
//                            public void onSuccess(String dataSnapshotValue) {
//                                MessagesAdapter.super.notifyDataSetChanged();
//                            }
//                        });
//                    }
//                }
//
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        System.out.println(mValues);
//    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentMessagesAdapterLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), mOnItemListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        HugMeUser user = mValues.get(position);
        String stri = user.first_name + " " + user.last_name;
        holder.mProfileName.setText(stri);
//        final String ProfilePic = user.getPictures().profile;

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mProfileName, lastMessage;
        public HugMeUser mItem;
        public OnItemListener onItemListener;
        ImageView mProfilePic;
//        private LinearLayout rootLayout;

        public ViewHolder(FragmentMessagesAdapterLayoutBinding binding, OnItemListener onItemListener) {
            super(binding.getRoot());
            this.onItemListener = onItemListener;
            this.mProfileName = binding.getRoot().findViewById(R.id.profile_name);
            this.mProfilePic = binding.getRoot().findViewById(R.id.list_item_profile_photo);
//            this.rootLayout = binding.getRoot().findViewById(R.id.rootLayout);
            this.lastMessage = binding.getRoot().findViewById(R.id.last_message);
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemListener.onItemClick(getAdapterPosition());
        }

    }

    public interface OnItemListener {
        void onItemClick(int position);
    }

}