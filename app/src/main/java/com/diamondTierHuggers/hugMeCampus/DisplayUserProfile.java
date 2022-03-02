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
import com.diamondTierHuggers.hugMeCampus.entity.HugMeUser;

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

    private final String[] gender = {"Male", "Female"};
    private final String[] emoji = {"💩", "🪨", "🥉", "🥈", "🏅", "💿", "💎"};

    private TextView name, info, bio;

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

    public void setProfile(HugMeUser h) {
        this.name = getView().findViewById(R.id.name);
        this.info = getView().findViewById(R.id.info);
        this.bio = getView().findViewById(R.id.bio);

        name.setText(h.first_name + " " + h.last_name);
        info.setText(h.age + ", " + gender[h.gender] + ", " + emoji[h.hug_count/50]);
        bio.setText(h.bio);
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
                        "https://firebasestorage.googleapis.com/v0/b/hugmecampus-dff8c.appspot.com/o/Screen%20Shot%202022-02-13%20at%201.04.37%20PM.png?alt=media&token=d9303bfc-a962-4a6e-bd57-74b740a7bfd9"
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

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setProfile(mHugMeUser);
    }
}