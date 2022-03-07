package com.diamondTierHuggers.hugMeCampus.chatBox;

import static com.diamondTierHuggers.hugMeCampus.LoginFragment.appUser;
import static com.diamondTierHuggers.hugMeCampus.MainActivity.database;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.diamondTierHuggers.hugMeCampus.databinding.FragmentChatBoxBinding;
import com.diamondTierHuggers.hugMeCampus.entity.HugMeUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link chatBoxFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class chatBoxFragment extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "hugMeUser";
    private HugMeUser mHugmeUser;
    private HugMeUser meUser;
    String chatKey;
    private RecyclerView chatRecyclerView;
    private List<ChatList> chatLists = new ArrayList<>();

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
        meUser = appUser.getAppUser();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChatBoxBinding.inflate(inflater, container, false);
        final  String getName = mHugmeUser.getFirst_name()+ " "+mHugmeUser.getLast_name();
        final String getProfilePic = mHugmeUser.getPictures().profile;
        binding.name.setText(getName);

//        attaching adapter
        chatRecyclerView = binding.recyclerView;
        chatRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setStackFromEnd(true);
        chatRecyclerView.setLayoutManager(linearLayoutManager);

        readMessages("1");



        System.out.println("chatKey value" + chatKey);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final  String getName = mHugmeUser.getFirst_name()+ " "+mHugmeUser.getLast_name();
        final String getProfilePic = mHugmeUser.getPictures().profile;

        chatRecyclerView = binding.recyclerView;
        ImageView backbtn = binding.backbtn;
        EditText messageEditText = binding.messageEditText;
        ImageView profilePic = binding.profilePic;
        Button sendBtn = binding.sendbtn;

        binding.name.setText(getName);
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

                if ((!meUser.getMessage_list().containsKey(mHugmeUser.getUid())) ||
                        (!mHugmeUser.getMessage_list().containsKey(meUser.getUid()))) {
                    meUser.getMessage_list().put(mHugmeUser.getUid(), chatKey);
                    mHugmeUser.getMessage_list().put(meUser.getUid(), chatKey);
                    addingToMessageList();
                }else{
                    chatKey = meUser.getMessage_list().get(mHugmeUser.getUid());
                }

                if (getTextMessage.equals("")) {
                    Toast.makeText(getContext(), "Enter message", Toast.LENGTH_SHORT).show();
                }else{
                    sendMessages(meUser.getUid(), mHugmeUser.getUid(),currentTimeStamp, getTextMessage, "1");
                }

                messageEditText.setText("");

            }
        });
    }


    private void sendMessages(String sender, String receiver, String time, String data, String chatKey){
        HashMap<String, String > h = new HashMap<>();
        h.put("sender", sender);
        h.put("receiver", receiver);
        h.put("time", time);
        h.put("data", data);
        database.getReference().child("Chat").child(chatKey).push().setValue(h);
    }

    private void readMessages(String chatKey){

        database.getReferenceFromUrl("https://hugmecampus-dff8c-default-rtdb.firebaseio.com/Chat").child(chatKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatLists.clear();
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    ChatList chat = snapshot1.getValue(ChatList.class);
                    chatLists.add(chat);
                }
                System.out.println(chatLists);
                chatAdapter = new ChatAdapter(chatLists);
                chatRecyclerView.setAdapter(chatAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void addingToMessageList (){
        database.getReference().child("users").child(meUser.getUid()).child("message_list").setValue(meUser.getMessage_list());

    }


}