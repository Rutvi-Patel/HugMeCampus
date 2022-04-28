package com.diamondTierHuggers.hugMeCampus.chatBox;

import static com.diamondTierHuggers.hugMeCampus.loginRegisterForgot.LoginFragment.appUser;
import static com.diamondTierHuggers.hugMeCampus.main.LoginRegisterActivity.database;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.diamondTierHuggers.hugMeCampus.R;
import com.diamondTierHuggers.hugMeCampus.main.OnGetDataListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {


    private List<ChatItem> chatItems = new ArrayList<>();
    private OnItemListener mOnItemListener;

    public List<ChatItem> getChatItems() {
        return chatItems;
    }

    public ChatAdapter(String chatKey, OnItemListener onItemListener) {
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

        mOnItemListener = onItemListener;

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
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chatbox_adapter_layout, parent, false), mOnItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ChatItem list2 = chatItems.get(position);
        System.out.println(list2.toString());
        if (list2.getSender().equals(appUser.getAppUser().getUid())){
            holder.oppoMsg.setVisibility(View.GONE);
            if (list2.lat != null){
                holder.locationViewOpp.setVisibility(View.GONE);
                holder.myMsg.setText(list2.getName());
                holder.location_img.setImageURI(Uri.parse(list2.getImage()));
            }else {
                holder.locationViewOpp.setVisibility(View.GONE);
                holder.locationView.setVisibility(View.GONE);
                holder.myMsg.setText(list2.getData());
            }
        }
        else{
            holder.myMsg.setVisibility(View.GONE);
            if (list2.lat != null){
                holder.locationView.setVisibility(View.GONE);
                holder.oppoMsg.setText(list2.getName());
                holder.location_img_oppo.setImageURI(Uri.parse(list2.getImage()));
            }
            else {
                holder.locationViewOpp.setVisibility(View.GONE);
                holder.locationView.setVisibility(View.GONE);
                holder.oppoMsg.setText(list2.getData());
            }
        }
    }

    @Override
    public int getItemCount() {
        return chatItems.size();
    }

//    public void updateChatList(ChatItem chatItem){
//        this.chatItems.add(chatItem);
//        notifyDataSetChanged();
//    }

    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private LinearLayout oppoLayout, myLayout, mylocationlay, opplocationlay;
        private TextView oppoMsg, myMsg, lastMessage, location_text, link;
        private ImageView location_img, location_img_oppo;
        private CardView locationView, locationViewOpp;
        public OnItemListener onItemListener;

        public MyViewHolder(@NonNull View itemView, ChatAdapter.OnItemListener onItemListener) {
            super(itemView);
//            this.myLayout = itemView.findViewById(R.id.myLayout);
//            this.oppoLayout = itemView.findViewById(R.id.oppLayout);
            this.oppoMsg = itemView.findViewById(R.id.myMsg_opp);
            this.myMsg = itemView.findViewById(R.id.myMsg);
            this.lastMessage = itemView.findViewById(R.id.last_message);
            this.location_text = itemView.findViewById(R.id.location_textView);
            this.link = itemView.findViewById(R.id.link);
            this.location_img = itemView.findViewById(R.id.location_image);
            this.location_img_oppo = itemView.findViewById(R.id.location_image_opp);
            this.locationView = itemView.findViewById(R.id.location_view);
            this.locationViewOpp = itemView.findViewById(R.id.location_view_opp);
//            this.mylocationlay = itemView.findViewById(R.id.mylocationLayout);
//            this.opplocationlay= itemView.findViewById(R.id.oppLocationLayout);
            this.onItemListener = onItemListener;
            itemView.getRootView().setOnClickListener(this);
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















