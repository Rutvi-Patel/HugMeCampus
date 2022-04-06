package com.diamondTierHuggers.hugMeCampus.messages;
import static com.diamondTierHuggers.hugMeCampus.loginRegisterForgot.LoginFragment.appUser;
import static com.diamondTierHuggers.hugMeCampus.main.LoginRegisterActivity.database;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.diamondTierHuggers.hugMeCampus.R;
import com.diamondTierHuggers.hugMeCampus.databinding.FragmentMessagesBinding;
import com.diamondTierHuggers.hugMeCampus.main.OnGetDataListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class MessagesFragment extends Fragment implements com.diamondTierHuggers.hugMeCampus.messages.MessagesAdapter.OnItemListener {

    // TODO: Rename and change types of parameters
    private String name;
    private FragmentMessagesBinding binding;
    private RecyclerView messagesRecyclerView;
    private String chatKey;
    MessagesAdapter adapter;

    public MessagesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentMessagesBinding.inflate(inflater, container, false);
        View view = inflater.inflate(R.layout.fragment_messages, container, false);
        RecyclerView recyclerView = (RecyclerView) view;

        messagesRecyclerView = binding.messagesRecyclerView;
        name = appUser.getAppUser().first_name+" "+ appUser.getAppUser().last_name;

        messagesRecyclerView.setHasFixedSize(true);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        System.out.println(appUser.getAppUser().getUid());

//        Set<String> messagesLists = appUser.getAppUser().getFriend_list().keySet();
        LinearLayoutManager manager = new LinearLayoutManager(this.getContext());
//            recyclerView.setLayoutManager(manager);
//            recyclerView.setHasFixedSize(true);
        adapter = new MessagesAdapter(this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    public void readData(Query ref, String uid, final OnGetDataListener listener) {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    chatKey = dataSnapshot.child(uid).getValue().toString();
                }
                listener.onSuccess("");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("ERROR: retrieving chat data");
            }

        });
    }

    @Override
    public void onItemClick(int position) {
        String uid = adapter.getItem(position).getUid();
        readData(database.getReference("messages").child(appUser.getAppUser().getUid()).orderByKey().equalTo(uid), uid, new OnGetDataListener() {
            @Override
            public void onSuccess(String dataSnapshotValue) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("hugMeUser", adapter.getItem(position));
                bundle.putString("chatKey", chatKey);
                NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_nav_chat_to_chatBoxFragment, bundle);

            }
        });

    }
}