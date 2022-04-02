package com.diamondTierHuggers.hugMeCampus.chatBox;

import static com.diamondTierHuggers.hugMeCampus.loginRegisterForgot.LoginFragment.appUser;
import static com.diamondTierHuggers.hugMeCampus.main.LoginRegisterActivity.database;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.diamondTierHuggers.hugMeCampus.R;
import com.diamondTierHuggers.hugMeCampus.databinding.FragmentChatBoxBinding;
import com.diamondTierHuggers.hugMeCampus.entity.HugMeUser;
import com.diamondTierHuggers.hugMeCampus.messages.FcmNotificationsSender;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatBoxFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatBoxFragment extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "hugMeUser";
    private static final String ARG_PARAM2 = "chatKey";
    private HugMeUser mHugmeUser;
    private HugMeUser meUser;
    DatabaseReference chatRef;
    String chatKey;
    ChatItem cl;
    private RecyclerView chatRecyclerView;
    private List<ChatItem> chatItems = new ArrayList<>();

//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private FragmentChatBoxBinding binding;
    private ChatAdapter chatAdapter;


    public ChatBoxFragment() {
        // Required empty public constructor
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
    public static ChatBoxFragment newInstance(Serializable param1, String param2) {
        ChatBoxFragment fragment = new ChatBoxFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mHugmeUser = (HugMeUser) getArguments().getSerializable(ARG_PARAM1);
            chatKey = getArguments().getString("chatKey");
        }
        meUser = appUser.getAppUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        binding = FragmentChatBoxBinding.inflate(inflater, container, false);
        final String getName = mHugmeUser.getFirst_name() + " " + mHugmeUser.getLast_name();
//        final String getProfilePic = mHugmeUser.getPictures().profile;
        binding.name.setText(getName);

//        attaching adapter
        chatRecyclerView = binding.recyclerView;
        chatRecyclerView.setNestedScrollingEnabled(false);
        chatRecyclerView.setHasFixedSize(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setStackFromEnd(true);
        chatRecyclerView.setLayoutManager(linearLayoutManager);
//        ChatAdapter chatAdapter = new com.diamondTierHuggers.hugMeCampus.chatBox.ChatAdapter(chatItems);
//        chatRecyclerView.setAdapter(chatAdapter);

        if (chatKey == null) {
            System.out.println("chatkey is null");
            chatRef = database.getReference().child("chat").push();
            chatKey = chatRef.getRef().toString().split("/")[4];
            DatabaseReference messageRef = database.getReference().child("messages");
            messageRef.child(mHugmeUser.getUid()).child(meUser.getUid()).setValue(chatKey);
            messageRef.child(meUser.getUid()).child(mHugmeUser.getUid()).setValue(chatKey);
        }
        else {
            chatRef = database.getReference().child("chat").child(chatKey);
        }

        chatAdapter = new ChatAdapter(chatKey);
        chatRecyclerView.setAdapter(chatAdapter);
        chatRecyclerView.scrollToPosition(chatAdapter.getItemCount());
//        final String getProfilePic = mHugmeUser.getPictures().profile;

//        ImageView backbtn = binding.backbtn;
        EditText messageEditText = binding.messageEditText;
        ImageView profilePic = binding.profilePic;
        Button sendBtn = binding.sendbtn;
        Button locationBtn = binding.locationbtn;

        binding.name.setText(getName);
//        Picasso.get().load(getProfilePic).into(profilePic);

        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(ChatBoxFragment.this).navigate(R.id.action_chatBoxFragment_to_locationFragment2);
            }
        });
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String getTextMessage = messageEditText.getText().toString();
//                get current timestamp
                final String currentT = String.valueOf(System.currentTimeMillis()).substring(0,10);
                long yourmilliseconds = Long.parseLong(currentT);
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd hh:mm aa");
                Date resultdate = new Date(yourmilliseconds);
                String currentTimeStamp = sdf.format(resultdate);
                System.out.println(currentTimeStamp);

                final  String getmyName = meUser.getFirst_name()+ " "+meUser.getLast_name();

                if (getTextMessage.equals("")) {
                    Toast.makeText(getContext(), "Enter message", Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        FcmNotificationsSender sendn = new FcmNotificationsSender(mHugmeUser.getToken(), getmyName, getTextMessage, getContext(), getActivity());
                        sendn.SendNotifications();
                    }
                    catch (Exception e){
                        System.out.println("Couldn't send notification or some notification error");
                    }
                    cl = new ChatItem(meUser.getUid(), getTextMessage);
                    sendMessages(cl);//,chatKey);
                }
                messageEditText.setText("");

            }
        });

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    private void sendMessages(ChatItem cl){
//        ChatList cl = new ChatList(sender, receiver, time, data);
        System.out.println(chatRef);
        chatRef.child(String.valueOf(System.currentTimeMillis()).substring(0,10)).setValue(cl);
    }


//    private void addingToMessageList (){
//        System.out.println(meUser.getMessage_list());
//        System.out.println(mHugmeUser.getMessage_list());
//        database.getReference().child("users").child(meUser.getUid()).child("message_list").setValue(meUser.getMessage_list())
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                    }
//                });
//
//        database.getReference().child("users").child(mHugmeUser.getUid()).child("message_list").setValue(mHugmeUser.getMessage_list());
//    }


}