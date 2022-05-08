package com.diamondTierHuggers.hugMeCampus.ratings;

import android.os.Bundle;
import static com.diamondTierHuggers.hugMeCampus.loginRegisterForgot.LoginFragment.appUser;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.diamondTierHuggers.hugMeCampus.R;
import com.diamondTierHuggers.hugMeCampus.data.HugRatingDataCallback;
import com.diamondTierHuggers.hugMeCampus.data.HugRatingModel;
import com.diamondTierHuggers.hugMeCampus.entity.HugMeUser;
import com.diamondTierHuggers.hugMeCampus.entity.HugRating;
import com.diamondTierHuggers.hugMeCampus.profiles.DisplayUserProfile;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HugRatings#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HugRatings extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Button submitButton;
    private RatingBar ratingBar;
    private EditText textBox;
    private TextView usertxt;
    // TODO: Rename and change types of parameters
    private HugMeUser mHugMeUser;
    private DisplayUserProfile userProfile;

    public HugRatings() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Hugratings.
     */
    // TODO: Rename and change types and number of parameters
    public static HugRatings newInstance(String param1, String param2) {
        HugRatings fragment = new HugRatings();
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
            mHugMeUser = (HugMeUser) getArguments().getSerializable("hugMeUser");
            userProfile = (DisplayUserProfile) getArguments().getSerializable("userprofile");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hugratings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        submitButton = (Button) view.findViewById(R.id.btnSubmit);
        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        textBox = (EditText) view.findViewById(R.id.RatingNotes);
        usertxt = (TextView) view.findViewById(R.id.textview_User);

        usertxt.setText(mHugMeUser.first_name);

        this.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((int)ratingBar.getRating() <= 0) {
                    Toast.makeText(getActivity().getApplicationContext(), "Error: No star rating given.", Toast.LENGTH_LONG).show();
                    HugRatingModel.getRatingObjects("uid100", new HugRatingDataCallback() {
                        @Override
                        public void GetHugRating(HugRating rating) {

                        }

                        @Override
                        public void GetRatingList(ArrayList<HugRating> ratingsList) {
                                if(ratingsList != null)
                                {
                                    for(HugRating rating : ratingsList)
                                    {
                                        System.out.println(rating.stars);
                                        System.out.println(rating.reviewer);
                                        System.out.println(rating.reviewee);
                                        System.out.println(rating.desc);
                                    }
                                }
                        }
                    });
                }
                else
                {
                    HugRating newRating = new HugRating();
                    newRating.stars = (int) ratingBar.getRating();
                    newRating.reviewer = appUser.getAppUser().first_name + " " + appUser.getAppUser().last_name;
                    newRating.reviewee = mHugMeUser.getUid();
                    newRating.desc = textBox.getText().toString();

                    HugRatingModel.addHugRating(newRating);
                    userProfile.UpdateProfileRating(newRating.stars);
                    getActivity().onBackPressed();
                }
            }
        });

    }
}