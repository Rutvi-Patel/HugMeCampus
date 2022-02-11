package com.diamondTierHuggers.hugMeCampus;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.diamondTierHuggers.hugMeCampus.entity.HugMeUser;

public class EditUserProfile extends Fragment {

    private EditUserProfileViewModel mViewModel;
    TextView firstName;
    TextView gender;
    TextView age;
    EditText age2;
    HugMeUser user;
    private View view;

    public static EditUserProfile newInstance() {
        return new EditUserProfile();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_user_profile_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EditUserProfileViewModel.class);
        // TODO: Use the ViewModel
        age = (TextView) view.findViewById(R.id.age);
        int num = Integer.parseInt(age.getText().toString());
        user.setAge(num);

    }

}