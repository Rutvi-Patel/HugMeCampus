package com.diamondTierHuggers.hugMeCampus;

import static com.diamondTierHuggers.hugMeCampus.LoginFragment.appUser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewbinding.ViewBinding;

import com.diamondTierHuggers.hugMeCampus.databinding.FragmentDisplayUserProfileBinding;
import com.diamondTierHuggers.hugMeCampus.databinding.ItemCustomFixedSizeLayout3Binding;

import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.imaginativeworld.whynotimagecarousel.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DisplayUserProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DisplayUserProfile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private final String[] gender = {"Male", "Female"};
    private final String[] emoji = {"poop", "coal", "bronze", "silver", "gold", "platinum", "diamond"};

    private TextView name, info, bio;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentDisplayUserProfileBinding binding;


    public DisplayUserProfile() {
        // Required empty public constructor
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
        binding = FragmentDisplayUserProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
//        return inflater.inflate(R.layout.fragment_display_user_profile, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name = view.findViewById(R.id.name);
        info = view.findViewById(R.id.info);
        bio = view.findViewById(R.id.bio);

        name.setText(appUser.getAppUser().first_name + " " + appUser.getAppUser().last_name);
        info.setText(appUser.getAppUser().age + ", " + gender[appUser.getAppUser().gender] + ", " + emoji[appUser.getAppUser().hug_count/50]);
        bio.setText(appUser.getAppUser().bio);
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

        List<CarouselItem> list = new ArrayList<>();
        list.add(
                new CarouselItem(
                        R.drawable.goldengate
                )
        );
        list.add(
                new CarouselItem(
                        R.drawable.bryce_canyon
                )
        );
        list.add(
                new CarouselItem(
                        R.drawable.cathedral_rock
                )
        );
        list.add(
                new CarouselItem(
                        R.drawable.death_valley
                )
        );
        binding.carousel4.setData(list);

        binding.carousel4.setIndicator(binding.customIndicator);
    }
}