package com.diamondTierHuggers.hugMeCampus.chatBox;

import static com.diamondTierHuggers.hugMeCampus.loginRegisterForgot.LoginFragment.appUser;
import static com.diamondTierHuggers.hugMeCampus.main.LoginRegisterActivity.database;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.diamondTierHuggers.hugMeCampus.R;
import com.diamondTierHuggers.hugMeCampus.main.OnGetDataListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {


    private List<ChatItem> chatItems = new ArrayList<>();

    public ChatAdapter(String chatKey) {
//        readData(database.getReference("chat").orderByKey().equalTo(chatKey), new OnGetDataListener() {
//            @Override
//            public void onSuccess(String dataSnapshotValue) {
//                ChatAdapter.super.notifyDataSetChanged();
//            }
//
//        });

        database.getReference("chat").child(chatKey).orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                System.out.println(snapshot);
                ChatItem c = snapshot.getValue(ChatItem.class);
                chatItems.add(c);
                ChatAdapter.super.notifyDataSetChanged();
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

//        for (String uid : appUser.getAppUser().friend_list.keySet()) {
//            if (appUser.savedHugMeUsers.containsKey(uid)) {
//                addItem(appUser.savedHugMeUsers.get(uid));
//            }
//            else {
//                readData(database.getReference("users").orderByKey().equalTo(uid), uid, new OnGetDataListener() {
//                    @Override
//                    public void onSuccess(String dataSnapshotValue) {
//                        MyListItemRecyclerViewAdapter.super.notifyDataSetChanged();
//                    }
//                });
//            }
//        }
    }

    public void readData(Query ref, final OnGetDataListener listener) {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot chat : dataSnapshot.getChildren()) {
                        for (DataSnapshot chatItem : chat.getChildren()) {
                            ChatItem c = chatItem.getValue(ChatItem.class);
                            chatItems.add(c);
                        }
                    }
                }
                listener.onSuccess("");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("ERROR: retrieving chat data");
            }

        });
    }
//    private final Context context;

//    public ChatAdapter(List<ChatItem> chatItems) {
//        this.chatItems = chatItems;
////        this.context = context;
//    }\




    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chatbox_adapter_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ChatItem list2 = chatItems.get(position);

        if (list2.getSender().equals(appUser.getAppUser().getUid())){
            holder.myLayout.setVisibility(View.VISIBLE);
            holder.oppoLayout.setVisibility((View.GONE));
            holder.myMsg.setText(list2.getData());
//            holder.myTime.setText(list2.getTime());
        }
        else{
            holder.myLayout.setVisibility(View.GONE);
            holder.oppoLayout.setVisibility(View.VISIBLE);
            holder.oppoMsg.setText(list2.getData());
//            holder.oppoTime.setText(list2.getTime());
        }
//        holder.lastMessage.setText(list2.getData());
    }

    @Override
    public int getItemCount() {
        return chatItems.size();
    }

//    public void updateChatList(ChatItem chatItem){
//        this.chatItems.add(chatItem);
//        notifyDataSetChanged();
//    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout oppoLayout, myLayout;
        private TextView oppoMsg, myMsg, lastMessage;
        private TextView oppoTime, myTime;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.myLayout = itemView.findViewById(R.id.myLayout);
            this.oppoLayout = itemView.findViewById(R.id.oppLayout);
            this.oppoMsg = itemView.findViewById(R.id.opponentMsg);
            this.myMsg = itemView.findViewById(R.id.myMsg);
//            this.myTime = itemView.findViewById(R.id.myTime);
//            this.oppoTime = itemView.findViewById(R.id.oppTime);
            this.lastMessage = itemView.findViewById(R.id.last_message);
        }
    }

}















