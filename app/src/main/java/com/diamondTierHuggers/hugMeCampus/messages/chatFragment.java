package com.diamondTierHuggers.hugMeCampus.messages;
import static com.diamondTierHuggers.hugMeCampus.LoginFragment.appUser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.diamondTierHuggers.hugMeCampus.R;
import com.diamondTierHuggers.hugMeCampus.databinding.FragmentChatBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link chatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class chatFragment extends Fragment implements com.diamondTierHuggers.hugMeCampus.messages.MessagesAdapter.OnItemListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String email;
    private String name;
    private FragmentChatBinding binding;
    private RecyclerView messagesRecyclerView;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private Set<String> messagesLists;
    MessagesAdapter adapter;

    public chatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment chatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static chatFragment newInstance(String param1, String param2) {
        chatFragment fragment = new chatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentChatBinding.inflate(inflater, container, false);
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        RecyclerView recyclerView = (RecyclerView) view;

        messagesRecyclerView = binding.messagesRecyclerView;
        email = appUser.getAppUser().getEmail().toString();
        name = appUser.getAppUser().first_name+" "+ appUser.getAppUser().last_name;

        messagesRecyclerView.setHasFixedSize(true);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        System.out.println(appUser.getAppUser().getUid());

        messagesLists = appUser.getAppUser().getFriend_list().keySet();
        LinearLayoutManager manager = new LinearLayoutManager(this.getContext());
//            recyclerView.setLayoutManager(manager);
//            recyclerView.setHasFixedSize(true);
        adapter = new MessagesAdapter(this);
        recyclerView.setAdapter(adapter);

//        return binding.getRoot();

        String token = "f2IAcLLQSdC8V9bx_e8Amv:APA91bF7JvPnNq7cgr-v7rpDVk4ho6n4BmS8x8MfS6t17Fgm4aQDGax1t2dUGQa96w6zkljrJkWPCImHBoKdcj9pxdlBlbYIpGTWRieTky2YLcGWtvkYJ64ann6DwXE9mmmVn_tdBAGu";

        FcmNotificationsSender sendn = new FcmNotificationsSender(token, "Test4", "Hello World" , this.getContext(), this.getActivity());
        sendn.SendNotifications();
        return view;
    }

    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("hugMeUser", adapter.getItem(position));
        NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_nav_chat_to_chatBoxFragment, bundle);


    }
}