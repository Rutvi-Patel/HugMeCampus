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

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new MyMessageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chatbox_my_msg_layout, parent, false));
            case 1:
                return new MyLocationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chatbox_my_location_layout, parent, false), mOnItemListener);
            case 2:
                return new OppoMessageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chatbox_oppo_msg_layout, parent, false));
            case 3:
                return new OppoLocationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chatbox_oppo_location_layout, parent, false), mOnItemListener);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatItem list2 = chatItems.get(position);
        System.out.println(list2);

        switch (holder.getItemViewType()) {
            case 0:
                MyMessageViewHolder holder0 = (MyMessageViewHolder) holder;
                holder0.myMsg.setText(list2.getData());
                break;
            case 1:
                MyLocationViewHolder holder1 = (MyLocationViewHolder) holder;
                holder1.myMsg.setText(list2.getName());
                holder1.location_img.setImageURI(Uri.parse(list2.getImage()));
                break;
            case 2:
                OppoMessageViewHolder holder2 = (OppoMessageViewHolder) holder;
                holder2.oppoMsg.setText(list2.getData());
                break;
            case 3:
                OppoLocationViewHolder holder3 = (OppoLocationViewHolder) holder;
                holder3.oppoMsg.setText(list2.getName());
                holder3.location_img_oppo.setImageURI(Uri.parse(list2.getImage()));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return chatItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        ChatItem list2 = chatItems.get(position);

        if (list2.getSender().equals(appUser.getAppUser().getUid())) {
            if (list2.lat == null){
                // my message
                return 0;
            }
            else {
                //my location
                return 1;
            }
        }
        else {
            if (list2.lat == null){
                //oppo message
                return 2;
            }
            else {
                // oppo location
                return 3;
            }
        }
    }

    static class MyMessageViewHolder extends RecyclerView.ViewHolder {
        private TextView myMsg;

        public MyMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            this.myMsg = itemView.findViewById(R.id.myMsg);
        }
    }

    static class OppoMessageViewHolder extends RecyclerView.ViewHolder {
        private TextView oppoMsg;

        public OppoMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            this.oppoMsg = itemView.findViewById(R.id.oppoMsg);
        }
    }

    static class MyLocationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView myMsg;
        private ImageView location_img;
        public OnItemListener onItemListener;

        public MyLocationViewHolder(@NonNull View itemView, OnItemListener onItemListener) {
            super(itemView);
            this.myMsg = itemView.findViewById(R.id.myMsgLocation);
            this.location_img = itemView.findViewById(R.id.location_image);
            this.onItemListener = onItemListener;
            itemView.getRootView().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemListener.onItemClick(getAdapterPosition());
        }

    }

    static class OppoLocationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView oppoMsg;
        private ImageView location_img_oppo;
        public OnItemListener onItemListener;

        public OppoLocationViewHolder(@NonNull View itemView, OnItemListener onItemListener) {
            super(itemView);
            this.oppoMsg = itemView.findViewById(R.id.oppoMsgLocation);
            this.location_img_oppo = itemView.findViewById(R.id.location_image_oppo);
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















