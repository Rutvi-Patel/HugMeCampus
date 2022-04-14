package com.diamondTierHuggers.hugMeCampus.search;

import static com.diamondTierHuggers.hugMeCampus.loginRegisterForgot.LoginFragment.appUser;
import static com.diamondTierHuggers.hugMeCampus.main.LoginRegisterActivity.database;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.diamondTierHuggers.hugMeCampus.R;
import com.diamondTierHuggers.hugMeCampus.data.AcceptListModel;
import com.diamondTierHuggers.hugMeCampus.entity.HugMeUser;
import com.diamondTierHuggers.hugMeCampus.main.OnGetDataListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    EditText usernameTxt;
    Button searchBtn;
    Pattern pattern = Pattern.compile("[a-z]+\\.[a-z]+[0-9]*", Pattern.CASE_INSENSITIVE);
    AcceptListModel acceptListModel = new AcceptListModel();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);

        searchBtn = view.findViewById(R.id.search_btn);
        usernameTxt = view.findViewById(R.id.username_search_text);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBtn.setClickable(false);
                performCodeVerify();
            }
        });
    }

    public boolean isValidUsername(String username) {
        //regex [a-z]+\.[a-z]+[0-9]*
        return pattern.matcher(username).matches();
    }

    // Checking if the input in form is valid
    boolean validateInput(String username) {
        if (username.isEmpty()){
            usernameTxt.setError("Enter username to search and add friend");
            return false;
        }
        // checking the proper email format
        if (!isValidUsername(username)) {
            usernameTxt.setError("Please Enter Valid Username");
            return false;
        }
        return true;
    }

    public void readData(Query ref, final OnGetDataListener listener) {

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot user : dataSnapshot.getChildren()) {
                        HugMeUser h = user.getValue(HugMeUser.class);
                        String hUid = user.getKey();
                        if (!appUser.getAppUser().blocked_list.containsKey(hUid) && !h.blocked_list.containsKey(appUser.getAppUser().getUid())) {
                            // other user already added appuser
                            if (h.getPending_list().containsKey(appUser.getAppUser().getUid())) {
                                acceptListModel.insertFriendUser(appUser.getAppUser().getUid(), hUid);
                                acceptListModel.insertFriendUser(hUid, appUser.getAppUser().getUid());
                                appUser.acceptListModel.removeRequestedPending(appUser.getAppUser().getUid(), hUid);
                                appUser.savedHugMeUsers.put(hUid, h);
                                appUser.getAppUser().friend_list.put(hUid, true);
                                Toast.makeText(getActivity().getApplicationContext(), "Added Friend", Toast.LENGTH_SHORT).show();
                            }
                            else if (!appUser.getAppUser().getUid().equals(hUid) && !appUser.getAppUser().friend_list.containsKey(hUid) && !appUser.getAppUser().getPending_list().containsKey(hUid)
                                    && !h.rejected_list.containsKey(appUser.getAppUser().getUid())) {
                                acceptListModel.insertRequestedUser(hUid, appUser.getAppUser().getUid());
                                acceptListModel.insertPendingUser(appUser.getAppUser().getUid(), hUid);
                                appUser.getAppUser().pending_list.put(hUid, true);
                                h.setUid(hUid);
                                appUser.savedHugMeUsers.put(hUid, h);
                                Toast.makeText(getActivity().getApplicationContext(), "Sent friend request", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getActivity().getApplicationContext(), "Could not send request", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(getActivity().getApplicationContext(), "Could not find user", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(), "Could not find user", Toast.LENGTH_SHORT).show();
                }
                listener.onSuccess("");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("ERROR: retrieving user data from db for matchmaking");
            }

        });
    }

    public void performCodeVerify () {
        String username = usernameTxt.getText().toString().toLowerCase();
        if (validateInput(username)) {
            readData(database.getReference("users").orderByChild("email").equalTo(username + "@student.csulb.edu"), new OnGetDataListener() {
                @Override
                public void onSuccess(String dataSnapshotValue) {
                    searchBtn.setClickable(true);
                }
            });
        }
        else {
            searchBtn.setClickable(true);
        }
    }

}