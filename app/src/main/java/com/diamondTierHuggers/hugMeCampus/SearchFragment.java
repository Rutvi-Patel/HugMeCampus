package com.diamondTierHuggers.hugMeCampus;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

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
                //TODO enable btn on complete
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

    public void performCodeVerify () {
        String username = usernameTxt.getText().toString();
        if (validateInput(username)) {
            // TODO get uid from username
            // TODO check if uid is in friends list, accept, block, or other user's block and reject
            // TODO add uid to accept list on db and local,
            // TODO add uid to request list of other users db

        }
    }

}