package com.diamondTierHuggers.hugMeCampus.location;

import static com.diamondTierHuggers.hugMeCampus.loginRegisterForgot.LoginFragment.appUser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.diamondTierHuggers.hugMeCampus.databinding.FragmentLocationBinding;

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

    public LocationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentLocationBinding.inflate(inflater, container, false);
//        View view = inflater.inflate(R.layout.fragment_location, container, false);
//        RecyclerView recyclerView = (RecyclerView) view;
        locationRecyclerView = binding.locationRecyclerView;
        name = appUser.getAppUser().first_name+" "+ appUser.getAppUser().last_name;

        locationRecyclerView.setHasFixedSize(true);
        locationRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        System.out.println(appUser.getAppUser().getUid());
        adapter = new LocationAdapter(this);
        locationRecyclerView.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onItemClick(int position) {

    }
}