package com.diamondTierHuggers.hugMeCampus.messages;

import static com.diamondTierHuggers.hugMeCampus.LoginFragment.appUser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.diamondTierHuggers.hugMeCampus.R;
import com.diamondTierHuggers.hugMeCampus.entity.HugMeUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MyViewHolder> {


    private final List<String> messagesLists;
    private final Context context;

    public MessagesAdapter(Set<String> messagesLists, Context context) {
        this.messagesLists = new ArrayList<>(messagesLists);
        this.context = context;
    }
    @NonNull
    @Override
    public MessagesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.messages_adapter_layout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesAdapter.MyViewHolder holder, int position) {
        String list2 = messagesLists.get(position);
        HugMeUser user = appUser.savedHugMeUsers.get(list2);
        System.out.println("rutvi" + user.toString());


        holder.name.setText(user.getFirst_name()+" "+user.getLast_name());
//        holder.profilePic.setImageDrawable(user.getPictures().profile);
        Picasso.get().load(user.getPictures().profile).into(holder.profilePic);

    }

    @Override
    public int getItemCount() {
        messagesLists.size();
        return 0;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView profilePic;
        private TextView name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            profilePic = itemView.findViewById(R.id.list_item_profile_photo);
            name = itemView.findViewById(R.id.profile_name);
        }
    }

}

