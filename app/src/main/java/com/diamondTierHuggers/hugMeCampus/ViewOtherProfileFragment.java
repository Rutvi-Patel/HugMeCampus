package com.diamondTierHuggers.hugMeCampus;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.diamondTierHuggers.hugMeCampus.databinding.FragmentViewOtherProfileBinding;
import com.diamondTierHuggers.hugMeCampus.entity.HugMeUser;

import java.io.Serializable;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewOtherProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewOtherProfileFragment extends Fragment {

    private final String[] gender = {"Male", "Female"};
    private final String[] emoji = {"poop", "coal", "bronze", "silver", "gold", "platinum", "diamond"};

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "hugMeUser";
    private HugMeUser mHugMeUser;

    public ViewOtherProfileFragment() {
        // Required empty public constructor
    }

    public ViewOtherProfileFragment(HugMeUser h) {
        // Required empty public constructor
        mHugMeUser = h;
    }

    public static ViewOtherProfileFragment newInstance(Serializable param1) {
        ViewOtherProfileFragment fragment = new ViewOtherProfileFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mHugMeUser = (HugMeUser) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View binding = inflater.inflate(R.layout.fragment_view_other_profile, container, false);
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_view, new DisplayUserProfile(mHugMeUser));
        fragmentTransaction.commit();
        return binding;
    }
}