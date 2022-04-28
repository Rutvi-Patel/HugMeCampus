package com.diamondTierHuggers.hugMeCampus.location;

import static com.diamondTierHuggers.hugMeCampus.loginRegisterForgot.LoginFragment.appUser;
import static com.diamondTierHuggers.hugMeCampus.main.LoginRegisterActivity.database;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.diamondTierHuggers.hugMeCampus.R;
import com.diamondTierHuggers.hugMeCampus.databinding.FragmentLocationBinding;

import java.util.HashMap;

/**
 * A fragment representing a list of Items.
 */
public class LocationFragment extends Fragment implements com.diamondTierHuggers.hugMeCampus.location.LocationAdapter.OnItemListener {

    // TODO: Rename and change types of parameters
    private String name;
    private FragmentLocationBinding binding;
    private RecyclerView locationRecyclerView;
    private String chatKey;
    LocationAdapter adapter;
    String messageID;

    public LocationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            messageID = savedInstanceState.getString("messageID");
//            System.out.println(messageID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentLocationBinding.inflate(inflater, container, false);
//        View view = inflater.inflate(R.layout.fragment_location, container, false);
//        RecyclerView recyclerView = (RecyclerView) view;
        locationRecyclerView = binding.locationRecyclerView;
        locationRecyclerView.setHasFixedSize(true);
        locationRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
//        System.out.println(appUser.getAppUser().getUid());
        adapter = new LocationAdapter(this);
        locationRecyclerView.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onItemClick(int position) {

        String currentTime = String.valueOf(System.currentTimeMillis()).substring(0, 10);
//        System.out.println(messageID);
//        System.out.println(currentTime);
//        System.out.println(adapter.getItem(position).getClass());
//        System.out.println(adapter.getItem(position));

//        HashMap<String, String> ld = new HashMap<>();
//        ld.put("sender",adapter.getItem(position).sender);
//        ld.put("name",adapter.getItem(position).name);
//        ld.put("coord",adapter.getItem(position).coord);
//        ld.put("img", adapter.getItem(position).image);
//        System.out.println(ld);
        database.getReference().child("chat").child(messageID).child(currentTime).setValue(adapter.getItem(position));
        getParentFragment().getView().findViewById(R.id.locationFragment).setVisibility(View.GONE);


    }

//    @Override
    public void setMessageID(String id){
        messageID = id;
    }

}