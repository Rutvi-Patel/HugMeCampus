package com.diamondTierHuggers.hugMeCampus.matchmaking;

//import static com.diamondTierHuggers.hugMeCampus.matchmaking.LoadMQFragment.mq;
import static com.diamondTierHuggers.hugMeCampus.main.LoginRegisterActivity.database;
import static com.diamondTierHuggers.hugMeCampus.loginRegisterForgot.LoginFragment.appUser;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import android.os.CountDownTimer;
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
import com.diamondTierHuggers.hugMeCampus.main.OnGetDataListener;
import com.diamondTierHuggers.hugMeCampus.profiles.ProfileAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class MatchMakingFragment extends Fragment {


    private FragmentMatchMakingBinding binding;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private TextView emptyQueueMessageTextView;
    private boolean matchmakingReady = false;
    private ArrayList<HugMeUser> al;
    private ProfileAdapter arrayAdapter;
    private SwipeFlingAdapterView flingContainer;
    public static MatchMakingQueue mq;

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


        flingContainer = (SwipeFlingAdapterView) view.findViewById(R.id.frame);

        al = new ArrayList<HugMeUser>();

        arrayAdapter = new ProfileAdapter(this.getContext(), al);

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

        if (mq == null) {
//            view.findViewById(R.id.loadFragment).setVisibility(View.VISIBLE);
            GifImageView logoView = (GifImageView) view.findViewById(R.id.logoImageViewMatchmaking);
            ((GifDrawable) logoView.getDrawable()).setLoopCount(0);

            new CountDownTimer(3000, 3000) {

                public void onTick(long millisUntilFinished) {
                    // You don't need to use this.
                }

                public void onFinish() {
                    // Put the code to stop the GIF here.
                    if (matchmakingReady) {
                        matchmakingReady = false;
                        runMatchmaking(view);
//                        NavHostFragment.findNavController(LoadMQFragment.this).navigate(R.id.action_nav_load_mq_to_nav_matchmaking);
                    }
                    else {
                        this.start();
                    }
                }

            }.start();

            mq = new MatchMakingQueue();
            mq.readData(database.getReference("users"), new OnGetDataListener() {
                @Override
                public void onSuccess(String dataSnapshotValue) {
                    matchmakingReady = true;
                }
            });
        }
        else {
//            view.findViewById(R.id.logoImageViewMatchmaking).setVisibility(View.INVISIBLE);
            runMatchmaking(view);
//            view.findViewById(R.id.loadFragment).setVisibility(View.INVISIBLE);
        }

        return view;
    }

    private void runMatchmaking(View view) {
        view.findViewById(R.id.logoImageViewMatchmaking).setVisibility(View.INVISIBLE);
        emptyQueueMessageTextView.setVisibility(View.INVISIBLE);
        view.setBackgroundColor(getResources().getColor(R.color.background_purple));
        if (mq.size() > 0) {
            al.add(mq.poll());
            arrayAdapter.notifyDataSetChanged();
            emptyQueueMessageTextView.setVisibility(View.INVISIBLE);
        }
        else {
            emptyQueueMessageTextView.setVisibility(View.VISIBLE);
            emptyQueueMessageTextView.startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in));
        }


    }
}