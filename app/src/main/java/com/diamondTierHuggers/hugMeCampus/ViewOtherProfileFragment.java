package com.diamondTierHuggers.hugMeCampus;

import static com.diamondTierHuggers.hugMeCampus.LoginFragment.appUser;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.diamondTierHuggers.hugMeCampus.databinding.FragmentViewOtherProfileBinding;
import com.diamondTierHuggers.hugMeCampus.entity.HugMeUser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
        FloatingActionButton button = getView().findViewById(R.id.userButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getContext(), button);
                popupMenu.getMenuInflater().inflate(R.menu.add_friend_block_menu, popupMenu.getMenu());

                if (mHugMeUser.isFriend()) {
                    popupMenu.getMenu().findItem(R.id.add_friend).setVisible(false);
                }

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getNumericShortcut() == 0) {
                            //block user
                            switch (mHugMeUser.getRequestPending()) {
                                case 0:
                                    // friends
                                    appUser.getAppUser().friend_list.remove(mHugMeUser.getUid());
                                case 1:
                                    // pending your approval
                                    // TODO check these todo logics, its late at night
                                    // TODO remove other user from appUser's request_list on DB
                                case 2:
                                    // pending friend's approval
                                    // TODO remove appUser from other user's request_list on DB
                            }
                            appUser.getAppUser().blocked_list.put(mHugMeUser.getUid(), true);
                            appUser.savedHugMeUsers.remove(mHugMeUser.getUid());
                            // TODO update db, remove from friend list on db, add to block list on db
                            // TODO go back to previous screen
                        }
                        else {
                            //add friend
                            appUser.getAppUser().friend_list.put(mHugMeUser.getUid(), true);
                            // TODO update db, put friend in both lists
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
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