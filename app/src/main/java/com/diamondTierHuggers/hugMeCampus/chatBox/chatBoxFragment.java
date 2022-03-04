package com.diamondTierHuggers.hugMeCampus.chatBox;

import static com.diamondTierHuggers.hugMeCampus.LoginFragment.appUser;
import static com.diamondTierHuggers.hugMeCampus.MainActivity.myRef;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.diamondTierHuggers.hugMeCampus.R;
import com.diamondTierHuggers.hugMeCampus.databinding.FragmentChatBoxBinding;
import com.diamondTierHuggers.hugMeCampus.entity.HugMeUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link chatBoxFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class chatBoxFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "hugMeUser";
    private HugMeUser mHugmeUser;
    String chatKey;
    private RecyclerView chatRecyclerView;
    private final List<chatList> chatLists = new ArrayList<>();

//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private FragmentChatBoxBinding binding;
    private ChatAdapter chatAdapter;


    public chatBoxFragment() {
        // Required empty public constructor
//        mHugmeUser  = h;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
     * @return A new instance of fragment chatBoxFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static chatBoxFragment newInstance(Serializable param1) {
        chatBoxFragment fragment = new chatBoxFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mHugmeUser = (HugMeUser) getArguments().getSerializable(ARG_PARAM1);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChatBoxBinding.inflate(inflater, container, false);

        final  String getName = mHugmeUser.getFirst_name()+ " "+mHugmeUser.getLast_name();
        final String getProfilePic = mHugmeUser.getPictures().profile;

        chatRecyclerView = binding.recyclerView;
        ImageView backbtn = binding.backbtn;
        TextView name = binding.name;
        EditText messageEditText = binding.messageEditText;
        ImageView profilePic = binding.profilePic;
        Button sendBtn = binding.sendbtn;

        chatRecyclerView.setHasFixedSize(true);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        chatAdapter = new ChatAdapter(chatLists, this.getContext());
        chatRecyclerView.setAdapter(chatAdapter);

        myRef.setValue("Chat");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

//                if (chatKey.isEmpty()) {
                    chatKey = "1";
                    if (snapshot.hasChild("Chat")) {
                        chatKey = String.valueOf(snapshot.child("Chat").getChildrenCount() + 1);
                    }
//                }

                if (snapshot.hasChild("Chat")){
                    if (snapshot.child("Chat").child(chatKey).hasChild("messages")){
                        chatLists.clear();

                        for(DataSnapshot msgSnapshot: snapshot.child("Chat").child(chatKey).child("messages").getChildren()){
                            if(msgSnapshot.hasChild("msg") && msgSnapshot.hasChild("uid")){

                                final String messagesTimestamp = msgSnapshot.getKey();
                                final String getuid = msgSnapshot.child("uid").getValue(String.class);
                                final String getMsg = msgSnapshot.child("msg").getValue(String.class);
//                                Long val = Long.parseLong(messagesTimestamp);
//                                Timestamp timestamp = new Timestamp(Long.parseLong(messagesTimestamp));
//                                Date date = new Date(timestamp.getTime());
//                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
//                                SimpleDateFormat simpleTimeFormat = new SimpleDateFormat( "hh:mm aa", Locale.getDefault());

//                                chatList clist = new chatList(getuid, getName, getMsg, simpleDateFormat.format(date), simpleTimeFormat.format(date));
                                chatList clist = new chatList(getuid, getName, getMsg, "date", "time");

                                chatLists.add(clist);

                                chatAdapter.updateChatList(chatLists);

                                chatRecyclerView.scrollToPosition(chatLists.size()-1);
                            }
                        }
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return inflater.inflate(R.layout.fragment_chat_box, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final  String getName = mHugmeUser.getFirst_name()+ " "+mHugmeUser.getLast_name();
        final String getProfilePic = mHugmeUser.getPictures().profile;

        chatRecyclerView = binding.recyclerView;
        ImageView backbtn = binding.backbtn;
        TextView name = binding.name;
        EditText messageEditText = binding.messageEditText;
        ImageView profilePic = binding.profilePic;
        Button sendBtn = binding.sendbtn;

        name.setText(getName);
//        Picasso.get().load(getProfilePic).into(profilePic);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String getTextMessage = messageEditText.getText().toString();
//                get current timestamp
                final String currentTimeStamp = String.valueOf(System.currentTimeMillis()).substring(0,10);

                if ((!appUser.getAppUser().getMessage_list().containsKey(mHugmeUser.getUid())) ||
                        (!mHugmeUser.getMessage_list().containsKey(appUser.getAppUser().getUid()))) {
                    appUser.getAppUser().getMessage_list().put(mHugmeUser.getUid(), chatKey);
                    mHugmeUser.getMessage_list().put(appUser.getAppUser().getUid(), chatKey);
                }

                myRef.child("Chat").child(chatKey).setValue(appUser.getAppUser().getUid());
                myRef.child("Chat").child(chatKey).setValue(mHugmeUser.getUid());
                myRef.child("Chat").child(chatKey).child("messages").child(currentTimeStamp).child("msg").setValue(getTextMessage);

                messageEditText.setText("");



            }
        });
    }
}