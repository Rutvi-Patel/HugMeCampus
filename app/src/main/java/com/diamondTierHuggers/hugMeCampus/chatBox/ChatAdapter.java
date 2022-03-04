package com.diamondTierHuggers.hugMeCampus.chatBox;
import static com.diamondTierHuggers.hugMeCampus.LoginFragment.appUser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.diamondTierHuggers.hugMeCampus.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {


    private List<chatList> chatLists;
    private final Context context;

    public ChatAdapter(List<chatList> chatLists, Context context) {
        this.chatLists = chatLists;
        this.context = context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chatbox_adapter_layout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        chatList list2 = chatLists.get(position);
        if (list2.getUid().equals(appUser.getAppUser().getUid())){
            holder.myLayout.setVisibility(View.VISIBLE);
            holder.oppoLayout.setVisibility((View.GONE));

            holder.myMsg.setText(list2.getMessage());
            holder.myTime.setText(list2.getDate()+" "+list2.getTime());
        }
        else{
            holder.myLayout.setVisibility(View.GONE);
            holder.oppoLayout.setVisibility(View.VISIBLE);

            holder.oppoMsg.setText(list2.getMessage());
            holder.oppoTime.setText(list2.getDate()+" "+list2.getTime());
        }
    }

    @Override
    public int getItemCount() {
        return chatLists.size();
    }

    public void updateChatList(List<chatList> chatLists){
        this.chatLists = chatLists;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout oppoLayout, myLayout;
        private TextView oppoMsg, myMsg;
        private TextView oppoTime, myTime;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.myLayout = itemView.findViewById(R.id.myLayout);
            this.oppoLayout = itemView.findViewById(R.id.oppLayout);
            this.oppoMsg = itemView.findViewById(R.id.opponentMsg);
            this.myMsg = itemView.findViewById(R.id.myMsg);
            this.myTime = itemView.findViewById(R.id.myTime);
            this.oppoTime = itemView.findViewById(R.id.oppTime);


        }
    }

}

