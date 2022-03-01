package com.diamondTierHuggers.hugMeCampus;

import static com.diamondTierHuggers.hugMeCampus.queryDB.AppUser.mq;
import static com.diamondTierHuggers.hugMeCampus.LoginFragment.appUser;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.diamondTierHuggers.hugMeCampus.data.AcceptListModel;
import com.diamondTierHuggers.hugMeCampus.data.BoolDataCallback;
import com.diamondTierHuggers.hugMeCampus.data.RejectListModel;
import com.diamondTierHuggers.hugMeCampus.databinding.FragmentMatchMakingBinding;
import com.diamondTierHuggers.hugMeCampus.entity.HugMeUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;

public class MatchMakingFragment extends Fragment {


    private FragmentMatchMakingBinding binding;
    private FirebaseAuth auth = FirebaseAuth.getInstance();

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

//        Button acceptButton = view.findViewById(R.id.Accept);
//        acceptButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        //add the view via xml or programmatically
        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) view.findViewById(R.id.frame);

        ArrayList al = new ArrayList<HugMeUser>();

        al.add(mq.poll());
        al.add(mq.poll());


        ProfileAdapter arrayAdapter = new ProfileAdapter(this.getContext(), al );

        //set the listener and the adapter
        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                System.out.println("LIST removed object!");
                al.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
//                Toast.makeText(this, "Left!", Toast.LENGTH_SHORT).show();
                System.out.println("LEFT");
                HugMeUser otherUser = (HugMeUser) dataObject;
                RejectListModel.insertRejectedUser(appUser.getAppUser().getUid(), otherUser.getUid());
                appUser.getAppUser().rejected_list.put(otherUser.getUid(), true);
                al.add(mq.poll());
            }

            @Override
            public void onRightCardExit(Object dataObject) {
//                Toast.makeText(MyActivity.this, "Right!", Toast.LENGTH_SHORT).show();
                System.out.println("RIGHT");
                HugMeUser otherUser = (HugMeUser) dataObject;
                appUser.getAppUser().accepted_list.put(otherUser.getUid(), true);
                AcceptListModel.isUserAccepted(appUser.getAppUser().getUid(), otherUser.getUid(), new BoolDataCallback() {
                    @Override
                    public void getBool(boolean value) {
                        if(value)
                        {
                            Toast.makeText(getActivity().getApplicationContext(), "It's a match!!", Toast.LENGTH_SHORT).show();
                        }

                        AcceptListModel.insertAcceptedUser(appUser.getAppUser().getUid(), otherUser.getUid());
                    }
                });
                al.add(mq.poll());
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                //TODO make sure if there are no more users to be matched with we display message, otherwise null pointer


                // Ask for more data here
//                al.add("XML ".concat(String.valueOf(i)));
//                arrayAdapter.notifyDataSetChanged();
//                Log.d("LIST", "notified");
//                i++;
//                System.out.println("ABOUT TO EMPTY");
            }

            @Override
            public void onScroll(float v) {
                System.out.println("SCROLL");
            }
        });

        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
//                makeToast(MyActivity.this, "Clicked!");
                System.out.println("CLICKED");
            }
        });
        return view;
    }
}