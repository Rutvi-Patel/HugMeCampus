package com.diamondTierHuggers.hugMeCampus.chatBox;

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

import com.diamondTierHuggers.hugMeCampus.R;
import com.diamondTierHuggers.hugMeCampus.databinding.FragmentChatBoxBinding;
import com.diamondTierHuggers.hugMeCampus.entity.HugMeUser;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link chatBoxFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class chatBoxFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private HugMeUser user;
    private FragmentChatBoxBinding binding;


    public chatBoxFragment(HugMeUser  hugMeUser) {
        // Required empty public constructor
        user  = hugMeUser;
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
    public static chatBoxFragment newInstance(HugMeUser user) {
        chatBoxFragment fragment = new chatBoxFragment(user);
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, "user");
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
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChatBoxBinding.inflate(inflater, container, false);
        return inflater.inflate(R.layout.fragment_chat_box, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final  String getName = user.getFirst_name()+ " "+user.getLast_name();
        final String getProfilePic = user.getPictures().profile;

        ImageView backbtn = binding.backbtn;
        TextView name = binding.name;
        EditText messageEditText = binding.messageEditText;
        ImageView profilePic = binding.profilePic;
        Button sendBtn = binding.sendbtn;

        name.setText(getName);
        Picasso.get().load(getProfilePic).into(profilePic);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}