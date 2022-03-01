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
import android.view.animation.AnimationUtils;
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
        FloatingActionButton messageButton = getView().findViewById(R.id.messageButton);

        if (mHugMeUser.getFriendRequestPending() >= 1) {
            messageButton.setVisibility(View.GONE);
        }

        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("button pressed");
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getContext(), button);
                popupMenu.getMenuInflater().inflate(R.menu.add_friend_block_menu, popupMenu.getMenu());

                if (mHugMeUser.getFriendRequestPending() <= 1) {
                    popupMenu.getMenu().findItem(R.id.add_friend).setVisible(false);
                }

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getNumericShortcut() == 0) {
                            //block user
                            switch (mHugMeUser.getFriendRequestPending()) {
                                case 0:
                                    // friends
                                    appUser.getAppUser().friend_list.remove(mHugMeUser.getUid());
                                    appUser.getAppUser().blocked_list.put(mHugMeUser.getUid(), true);
                                case 1:
                                    // pending friend's approval
                                    // TODO remove other user from appUser's request_list on DB
                                    // TODO remove appuser from other users pending_list on DB
                                case 2:
                                    // pending your approval
                                    // TODO remove appUser from other user's request_list on DB
                                    // TODO remove other user from app users pending_list on DB
                            }
                            appUser.getAppUser().blocked_list.put(mHugMeUser.getUid(), true);
                            appUser.acceptListModel.insertBlockedUser(appUser.getAppUser().getUid(), mHugMeUser.getUid());
                            appUser.savedHugMeUsers.remove(mHugMeUser.getUid());
                            // TODO remove from friends on db for both users
                            // TODO go back to previous screen
                            // TODO remove any messages
                        }
                        else {
                            //add friend
                            appUser.acceptListModel.insertFriendUser(appUser.getAppUser().getUid(), mHugMeUser.getUid());
                            appUser.acceptListModel.insertFriendUser(mHugMeUser.getUid(), appUser.getAppUser().getUid());
                            //TODO remove from pending list of other user and request list of app user
                            appUser.getAppUser().friend_list.put(mHugMeUser.getUid(), true);
                            appUser.getAppUser().request_list.remove(mHugMeUser.getUid());
                            messageButton.setVisibility(View.VISIBLE);
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