package com.diamondTierHuggers.hugMeCampus.profiles;

import static com.diamondTierHuggers.hugMeCampus.loginRegisterForgot.LoginFragment.appUser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewbinding.ViewBinding;

import com.diamondTierHuggers.hugMeCampus.R;
import com.diamondTierHuggers.hugMeCampus.databinding.FragmentDisplayUserProfileBinding;
import com.diamondTierHuggers.hugMeCampus.databinding.ItemCustomFixedSizeLayout3Binding;
import com.diamondTierHuggers.hugMeCampus.entity.HugMeUser;

import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.imaginativeworld.whynotimagecarousel.utils.Utils;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DisplayUserProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DisplayUserProfile extends Fragment implements Serializable {

    private final String[] gender = {"Male", "Female", "Non-Binary"};
    private final String[] emoji = {"💩", "🪨", "🥉", "🥈", "🏅", "💿", "💎"};

    private TextView name, info, bio, rating;

    private final HugMeUser mHugMeUser;

    private FragmentDisplayUserProfileBinding binding;


    public DisplayUserProfile() {
        // Required empty public constructor
        this.mHugMeUser = appUser.getAppUser();
    }

    public DisplayUserProfile(HugMeUser hugMeUser) {
        this.mHugMeUser = hugMeUser;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DisplayUserProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static DisplayUserProfile newInstance(String param1, String param2) {
        DisplayUserProfile fragment = new DisplayUserProfile();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDisplayUserProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void UpdateProfileRating(int newRating)
    {
        DecimalFormat df = new DecimalFormat("#.0");
        mHugMeUser.total_rating += newRating;
        String avgRatingText = "★ " + df.format(mHugMeUser.total_rating / (float)++mHugMeUser.num_reviews);
        rating.setText(avgRatingText);
    }

    public void setProfile(HugMeUser h) {
        this.name = getView().findViewById(R.id.name);
        this.info = getView().findViewById(R.id.info);
        this.bio = getView().findViewById(R.id.bio);
        this.rating = getView().findViewById(R.id.averageRating);

        if(mHugMeUser.num_reviews <= 0)
        {
            rating.setText("");
            rating.setClickable(false);
        }
        else
        {
            DecimalFormat df = new DecimalFormat("#.0");
            String avgRatingText = "★ " + df.format(mHugMeUser.total_rating / (float)mHugMeUser.num_reviews);
            rating.setText(avgRatingText);
            rating.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("uid", mHugMeUser.getUid());
                    NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_nav_other_profile_to_viewhugs, bundle);
                }
            });
        }

        name.setText(h.first_name + " " + h.last_name);
        info.setText(h.age + ", " + gender[h.gender] + ", " + emoji[h.hug_count/50]);
        bio.setText(h.bio);

        // hug prefs

        if (!mHugMeUser.getHug_preferences().get("short")) {
            getView().findViewById(R.id.shortHug).setVisibility(View.GONE);
        }
        if (!mHugMeUser.getHug_preferences().get("medium")) {
            getView().findViewById(R.id.mediumHug).setVisibility(View.GONE);
        }
        if (!mHugMeUser.getHug_preferences().get("long")) {
            getView().findViewById(R.id.longHug).setVisibility(View.GONE);
        }

        if (!mHugMeUser.getHug_preferences().get("celebratory")) {
            getView().findViewById(R.id.celebratory).setVisibility(View.GONE);
        }

        if (!mHugMeUser.getHug_preferences().get("emotional")) {
            getView().findViewById(R.id.emotional).setVisibility(View.GONE);
        }

        if (!mHugMeUser.getHug_preferences().get("happy")) {
            getView().findViewById(R.id.happy).setVisibility(View.GONE);
        }

        if (!mHugMeUser.getHug_preferences().get("quiet")) {
            getView().findViewById(R.id.quiet).setVisibility(View.GONE);
        }

        if (!mHugMeUser.getHug_preferences().get("sad")) {
            getView().findViewById(R.id.sad).setVisibility(View.GONE);
        }

        if (!mHugMeUser.getHug_preferences().get("talkative")) {
            getView().findViewById(R.id.talkative).setVisibility(View.GONE);
        }

        if (!mHugMeUser.getHug_preferences().get("male")) {
            getView().findViewById(R.id.male).setVisibility(View.GONE);
        }

        if (!mHugMeUser.getHug_preferences().get("female")) {
            getView().findViewById(R.id.female).setVisibility(View.GONE);
        }

        if (!mHugMeUser.getHug_preferences().get("nonbinary")) {
            getView().findViewById(R.id.nonbinary).setVisibility(View.GONE);
        }

        if (!mHugMeUser.getUid().equals(appUser.getAppUser().getUid())) {
            if (mHugMeUser.getHug_preferences().get("short") && appUser.getAppUser().getHug_preferences().get("short")) {
                ((CardView) getView().findViewById(R.id.shortHug)).setCardBackgroundColor(0xff03dac5);
                ((TextView) getView().findViewById(R.id.shortText)).setTextColor(0xff34223b);
            }
            if (mHugMeUser.getHug_preferences().get("medium") && appUser.getAppUser().getHug_preferences().get("medium")) {
                ((CardView) getView().findViewById(R.id.mediumHug)).setCardBackgroundColor(0xff03dac5);
                ((TextView) getView().findViewById(R.id.mediumText)).setTextColor(0xff34223b);
            }
            if (mHugMeUser.getHug_preferences().get("long") && appUser.getAppUser().getHug_preferences().get("long")) {
                ((CardView) getView().findViewById(R.id.longHug)).setCardBackgroundColor(0xff03dac5);
                ((TextView) getView().findViewById(R.id.longText)).setTextColor(0xff34223b);
            }

            if (mHugMeUser.getHug_preferences().get("celebratory") && appUser.getAppUser().getHug_preferences().get("celebratory")) {
                ((CardView) getView().findViewById(R.id.celebratory)).setCardBackgroundColor(0xff03dac5);
                ((TextView) getView().findViewById(R.id.celebratoryText)).setTextColor(0xff34223b);
            }

            if (mHugMeUser.getHug_preferences().get("emotional") && appUser.getAppUser().getHug_preferences().get("emotional")) {
                ((CardView) getView().findViewById(R.id.emotional)).setCardBackgroundColor(0xff03dac5);
                ((TextView) getView().findViewById(R.id.emotionalText)).setTextColor(0xff34223b);
            }

            if (mHugMeUser.getHug_preferences().get("happy") && appUser.getAppUser().getHug_preferences().get("happy")) {
                ((CardView) getView().findViewById(R.id.happy)).setCardBackgroundColor(0xff03dac5);
                ((TextView) getView().findViewById(R.id.happyText)).setTextColor(0xff34223b);
            }

            if (mHugMeUser.getHug_preferences().get("quiet") && appUser.getAppUser().getHug_preferences().get("quiet")) {
                ((CardView) getView().findViewById(R.id.quiet)).setCardBackgroundColor(0xff03dac5);
                ((TextView) getView().findViewById(R.id.quietText)).setTextColor(0xff34223b);
            }

            if (mHugMeUser.getHug_preferences().get("sad") && appUser.getAppUser().getHug_preferences().get("sad")) {
                ((CardView) getView().findViewById(R.id.sad)).setCardBackgroundColor(0xff03dac5);
                ((TextView) getView().findViewById(R.id.sadText)).setTextColor(0xff34223b);
            }

            if (mHugMeUser.getHug_preferences().get("talkative") && appUser.getAppUser().getHug_preferences().get("talkative")) {
                ((CardView) getView().findViewById(R.id.talkative)).setCardBackgroundColor(0xff03dac5);
                ((TextView) getView().findViewById(R.id.talkativeText)).setTextColor(0xff34223b);
            }

            if (mHugMeUser.getHug_preferences().get(appUser.getAppUser().getGenderString()) && appUser.getAppUser().getHug_preferences().get(mHugMeUser.getGenderString())) {
                if (appUser.getAppUser().getGender() == 0) {
                    ((CardView) getView().findViewById(R.id.male)).setCardBackgroundColor(0xff03dac5);
                    ((TextView) getView().findViewById(R.id.maleText)).setTextColor(0xff34223b);
                }
                else if (appUser.getAppUser().getGender() == 1) {
                    ((CardView) getView().findViewById(R.id.female)).setCardBackgroundColor(0xff03dac5);
                    ((TextView) getView().findViewById(R.id.femaleText)).setTextColor(0xff34223b);
                }
                else {
                    ((CardView) getView().findViewById(R.id.nonbinary)).setCardBackgroundColor(0xff03dac5);
                    ((TextView) getView().findViewById(R.id.nonbinaryText)).setTextColor(0xff34223b);
                }
            }
        }


        //carousel v2
        binding.carousel4.registerLifecycle(getLifecycle());


        binding.carousel4.setCarouselListener((CarouselListener) (new CarouselListener() {
            @Override
            public ViewBinding onCreateViewHolder(LayoutInflater layoutInflater, ViewGroup parent) {
                return (ViewBinding) ItemCustomFixedSizeLayout3Binding.inflate(layoutInflater, parent, false);
            }

            @Override
            public void onBindViewHolder(ViewBinding binding, CarouselItem item, int position) {
                ItemCustomFixedSizeLayout3Binding currentBinding = (ItemCustomFixedSizeLayout3Binding) binding;
                currentBinding.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Utils.setImage(currentBinding.imageView, item, R.drawable.ic_wb_cloudy_with_padding);
            }

            @Override
            public void onClick(int position, CarouselItem carouselItem) {
                DefaultImpls.onClick(this, position, carouselItem);
            }

            @Override
            public void onLongClick(int position, CarouselItem carouselItem) {
                DefaultImpls.onLongClick(this, position, carouselItem);
            }


        }));


//        System.out.println(mHugMeUser.getPictures());

        List<CarouselItem> list = new ArrayList<>();

        for (String pic : mHugMeUser.getPictures().keySet()) {
            list.add(
                    new CarouselItem(
                            mHugMeUser.getPicture(pic)
                    )
            );
        }

//        list.add(
//                new CarouselItem(
//                        "https://firebasestorage.googleapis.com/v0/b/hugmecampus-dff8c.appspot.com/o/Screen%20Shot%202022-02-13%20at%201.04.37%20PM.png?alt=media&token=d9303bfc-a962-4a6e-bd57-74b740a7bfd9"
//                )
//        );
//        list.add(
//                new CarouselItem(
//                        R.drawable.bryce_canyon
//                )
//        );
//        list.add(
//                new CarouselItem(
//                        R.drawable.cathedral_rock
//                )
//        );
//        list.add(
//                new CarouselItem(
//                        R.drawable.death_valley
//                )
//        );
        binding.carousel4.setData(list);

        binding.carousel4.setIndicator(binding.customIndicator);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setProfile(mHugMeUser);
    }
}