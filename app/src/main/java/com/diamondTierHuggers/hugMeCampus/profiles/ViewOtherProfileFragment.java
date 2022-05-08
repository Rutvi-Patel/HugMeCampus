package com.diamondTierHuggers.hugMeCampus.profiles;

import static com.diamondTierHuggers.hugMeCampus.loginRegisterForgot.LoginFragment.appUser;
import static com.diamondTierHuggers.hugMeCampus.main.LoginRegisterActivity.database;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import com.diamondTierHuggers.hugMeCampus.R;
import com.diamondTierHuggers.hugMeCampus.entity.HugMeUser;
import com.diamondTierHuggers.hugMeCampus.main.OnGetDataListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewOtherProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewOtherProfileFragment extends Fragment {

    private final String[] gender = {"Male", "Female"};
    private final String[] emoji = {"poop", "coal", "bronze", "silver", "gold", "platinum", "diamond"};
    private String chatKey;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "hugMeUser";
    private HugMeUser mHugMeUser;

    private DisplayUserProfile displayUserProfile;

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

    public void readData(Query ref, final OnGetDataListener listener) {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    chatKey = dataSnapshot.child(mHugMeUser.getUid()).getValue().toString();
                }
                listener.onSuccess("");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("ERROR: retrieving chat data");
            }

        });
    }

    private static boolean isFABOpen = false;
    private void showFABMenu(FloatingActionButton fab1, FloatingActionButton fab2){
        isFABOpen=true;
        fab1.animate().translationY(-getResources().getDimension(R.dimen.standard_1));
        fab2.animate().translationY(-getResources().getDimension(R.dimen.standard_2));
    }

    private void closeFABMenu(FloatingActionButton fab1, FloatingActionButton fab2){
        isFABOpen=false;
        fab1.animate().translationY(0);
        fab2.animate().translationY(0);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton button = getView().findViewById(R.id.userButton);
        FloatingActionButton messageButton = getView().findViewById(R.id.messageButton);
        FloatingActionButton rateUserButton = getView().findViewById(R.id.rateUserButton);
        FloatingActionButton fab = getView().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen){
                    showFABMenu(rateUserButton, button);
                }else{
                    closeFABMenu(rateUserButton, button);
                }
            }
        });
        if (mHugMeUser.getFriendRequestPending() >= 1) {
            messageButton.setVisibility(View.GONE);
        }

        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readData(database.getReference("messages").child(appUser.getAppUser().getUid()).orderByKey().equalTo(mHugMeUser.getUid()), new OnGetDataListener() {
                    @Override
                    public void onSuccess(String dataSnapshotValue) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("hugMeUser", mHugMeUser);
                        bundle.putString("chatKey", chatKey);
                        NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_nav_other_profile_to_chatBoxFragment, bundle);            }
                });
            }
        });

        rateUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("hugMeUser", mHugMeUser);
                bundle.putSerializable("userprofile", displayUserProfile);
                NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_nav_other_profile_to_hugratings, bundle);

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

//                        System.out.println(item.getNumericShortcut());
                        if (item.getNumericShortcut() == '0') {
                            // TODO notify other user to remove this user from any lists/messages
                            //block user
                            if  (mHugMeUser.getFriendRequestPending() == 0) {
                                // friends
                                appUser.acceptListModel.removeFriend(mHugMeUser.getUid(), appUser.getAppUser().getUid());
                                appUser.getAppUser().friend_list.remove(mHugMeUser.getUid());
                            }
                            else if (mHugMeUser.getFriendRequestPending() == 1) {
                                // pending friend's approval
                                appUser.acceptListModel.removeRequestedPending(mHugMeUser.getUid(), appUser.getAppUser().getUid());
                                appUser.getAppUser().pending_list.remove(mHugMeUser.getUid());
                            }
                            else if (mHugMeUser.getFriendRequestPending() == 2) {
                                // pending your approval
                                appUser.acceptListModel.removeRequestedPending(appUser.getAppUser().getUid(), mHugMeUser.getUid());
                                appUser.getAppUser().request_list.remove(mHugMeUser.getUid());
                            }
                            appUser.getAppUser().blocked_list.put(mHugMeUser.getUid(), true);
                            appUser.acceptListModel.insertBlockedUser(appUser.getAppUser().getUid(), mHugMeUser.getUid());
                            appUser.savedHugMeUsers.remove(mHugMeUser.getUid());
                            // TODO remove any messages
                            getFragmentManager().popBackStackImmediate();
                        }
                        else {
                            //add friend
                            // TODO notify other user if they are on the app that they have a new friend
                            appUser.acceptListModel.insertFriendUser(appUser.getAppUser().getUid(), mHugMeUser.getUid());
                            appUser.acceptListModel.insertFriendUser(mHugMeUser.getUid(), appUser.getAppUser().getUid());
                            appUser.acceptListModel.removeRequestedPending(appUser.getAppUser().getUid(), mHugMeUser.getUid());
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
        displayUserProfile = new DisplayUserProfile(mHugMeUser);
        fragmentTransaction.replace(R.id.fragment_container_view, displayUserProfile);
        fragmentTransaction.commit();
        return binding;
    }
}