package com.diamondTierHuggers.hugMeCampus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.diamondTierHuggers.hugMeCampus.databinding.FragmentSecondBinding;
import com.diamondTierHuggers.hugMeCampus.queryDB.AppUser;
import com.diamondTierHuggers.hugMeCampus.queryDB.OnGetDataListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SecondFragment extends Fragment {
    public static AppUser appUser = new AppUser();

    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //TODO move to after logging in, queries for the app users data in db, needed for matchmaking and displaying users profile
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://hugmecampus-dff8c-default-rtdb.firebaseio.com/");
        FirebaseAuth auth = FirebaseAuth.getInstance();
        appUser.readData(database.getReference("users").child("uid123"), new OnGetDataListener() {  //.child(auth.getUid()), new OnGetDataListener() {
            @Override
            public void onSuccess(String dataSnapshotValue) {
                System.out.println("created HugMeUser for app user");
            }
        });

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_matchmakingUI);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}