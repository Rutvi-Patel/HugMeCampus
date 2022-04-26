package com.diamondTierHuggers.hugMeCampus.matchmaking;

//import static com.diamondTierHuggers.hugMeCampus.LoadMQFragment.mq;
import static com.diamondTierHuggers.hugMeCampus.LoadMQFragment.mq;
import static com.diamondTierHuggers.hugMeCampus.loginRegisterForgot.LoginFragment.appUser;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.diamondTierHuggers.hugMeCampus.R;
import com.diamondTierHuggers.hugMeCampus.data.BoolDataCallback;
import com.diamondTierHuggers.hugMeCampus.data.RejectListModel;
import com.diamondTierHuggers.hugMeCampus.databinding.FragmentMatchMakingBinding;
import com.diamondTierHuggers.hugMeCampus.entity.HugMeUser;
import com.diamondTierHuggers.hugMeCampus.profiles.ProfileAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;

public class MatchMakingFragment extends Fragment {


    private FragmentMatchMakingBinding binding;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private TextView emptyQueueMessageTextView;

    public MatchMakingFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMatchMakingBinding.inflate(inflater, container, false);
        super.onCreate(savedInstanceState);

        View view = binding.getRoot();

        emptyQueueMessageTextView = view.findViewById(R.id.emptyQueueMessageTextView);

        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) view.findViewById(R.id.frame);

        ArrayList al = new ArrayList<HugMeUser>();

        ProfileAdapter arrayAdapter = new ProfileAdapter(this.getContext(), al);

        if (mq.size() > 0) {
            al.add(mq.poll());
            emptyQueueMessageTextView.startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out));
            emptyQueueMessageTextView.setVisibility(View.INVISIBLE);
        }
        else {
            emptyQueueMessageTextView.setVisibility(View.VISIBLE);
            emptyQueueMessageTextView.startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in));
        }

        //set the listener and the adapter
        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                al.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                HugMeUser otherUser = (HugMeUser) dataObject;
                RejectListModel.insertRejectedUser(appUser.getAppUser().getUid(), otherUser.getUid());
                appUser.getAppUser().rejected_list.put(otherUser.getUid(), true);
                if (mq.size() > 0) {
                    al.add(mq.poll());
                }
                else {
                    emptyQueueMessageTextView.setVisibility(View.VISIBLE);
                    emptyQueueMessageTextView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_top));
                }
            }

            @Override
            public void onRightCardExit(Object dataObject) {

                HugMeUser otherUser = (HugMeUser) dataObject;
                appUser.savedHugMeUsers.put(otherUser.getUid(), otherUser);
                appUser.getAppUser().accepted_list.put(otherUser.getUid(), true);
                appUser.acceptListModel.insertAcceptedUser(appUser.getAppUser().getUid(), otherUser.getUid());

                // TODO notify other user if they are on the app that they have a new friend
                if (appUser.getAppUser().request_list.containsKey(otherUser.getUid())) {
                    appUser.getAppUser().request_list.remove(otherUser.getUid());
                    appUser.acceptListModel.removeRequestedPending(appUser.getAppUser().getUid(), otherUser.getUid());
                    appUser.acceptListModel.insertFriendUser(appUser.getAppUser().getUid(), otherUser.getUid());
                    appUser.getAppUser().friend_list.put(otherUser.getUid(), true);
                    appUser.savedHugMeUsers.put(otherUser.getUid(), otherUser);
                }
                else {
                    appUser.acceptListModel.isUserAccepted(otherUser.getUid(), appUser.getAppUser().getUid(), new BoolDataCallback() {
                        @Override
                        public void getBool(boolean value) {
                            if(value)
                            {
                                Toast.makeText(getActivity().getApplicationContext(), "It's a match!!", Toast.LENGTH_SHORT).show();
                                appUser.acceptListModel.insertFriendUser(appUser.getAppUser().getUid(), otherUser.getUid());
                                appUser.getAppUser().friend_list.put(otherUser.getUid(), true);
                                appUser.savedHugMeUsers.put(otherUser.getUid(), otherUser);
                            }

                        }
                    });
                }

                if (mq.size() > 0) {
                    al.add(mq.poll());
                }
                else {
                    emptyQueueMessageTextView.setVisibility(View.VISIBLE);
                    emptyQueueMessageTextView.startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in));
                }
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
            }

            @Override
            public void onScroll(float v) {
            }
        });

        return view;
    }
}