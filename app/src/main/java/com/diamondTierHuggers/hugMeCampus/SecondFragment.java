package com.diamondTierHuggers.hugMeCampus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewbinding.ViewBinding;

import com.diamondTierHuggers.hugMeCampus.databinding.FragmentSecondBinding;
import com.diamondTierHuggers.hugMeCampus.databinding.ItemCustomFixedSizeLayout3Binding;
import com.diamondTierHuggers.hugMeCampus.queryDB.AppUser;
import com.diamondTierHuggers.hugMeCampus.queryDB.OnGetDataListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.imaginativeworld.whynotimagecarousel.utils.Utils;


import java.util.ArrayList;
import java.util.List;

public class SecondFragment extends Fragment {
    public static AppUser appUser = new AppUser();

    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //carousel v2
        binding.carousel4.registerLifecycle(getLifecycle());

        binding.carousel4.setCarouselListener((CarouselListener)(new CarouselListener() {
            @Override
            public ViewBinding onCreateViewHolder(LayoutInflater layoutInflater, ViewGroup parent) {
                return (ViewBinding)ItemCustomFixedSizeLayout3Binding.inflate(layoutInflater, parent, false);
            }

            @Override
            public void onBindViewHolder(ViewBinding binding, CarouselItem item, int position) {
                ItemCustomFixedSizeLayout3Binding currentBinding = (ItemCustomFixedSizeLayout3Binding)binding;
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


        //carousel v1
        // Java
//        ImageCarousel carousel = getView().findViewById(R.id.carousel);
//
//// Register lifecycle. For activity this will be lifecycle/getLifecycle() and for fragments it will be viewLifecycleOwner/getViewLifecycleOwner().
//        carousel.registerLifecycle(getLifecycle());
//
//        List<CarouselItem> list = new ArrayList<>();
//
//// Image URL with caption
//        list.add(
//                new CarouselItem(
//                        "https://images.unsplash.com/photo-1532581291347-9c39cf10a73c?w=1080",
//                        "Photo by Aaron Wu on Unsplash"
//                )
//        );
//
//// Just image URL
//        list.add(
//                new CarouselItem(
//                        "https://images.unsplash.com/photo-1534447677768-be436bb09401?w=1080"
//                )
//        );
//
//// Image URL with header
//        Map<String, String> headers = new HashMap<>();
//        headers.put("header_key", "header_value");
//
//        list.add(
//                new CarouselItem(
//                        R.drawable.goldengate,
//                        "golden gate"
//                )
//        );
//
//// Image drawable with caption
//        list.add(
//                new CarouselItem(
//                        R.drawable.death_valley,
//                        "Photo by Kimiya Oveisi on Unsplash"
//                )
//        );
//
//// Just image drawable
//        list.add(
//                new CarouselItem(
//                        R.drawable.bryce_canyon
//                )
//        );
//
//// ...
//
//        carousel.setData(list);

        //TODO move to after logging in, queries for the app users data in db, needed for matchmaking and displaying users profile
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://hugmecampus-dff8c-default-rtdb.firebaseio.com/");
        FirebaseAuth auth = FirebaseAuth.getInstance();
        appUser.readData(database.getReference("users").child("uid123"), new OnGetDataListener() {  //.child(auth.getUid()), new OnGetDataListener() {
            @Override
            public void onSuccess(String dataSnapshotValue) {
                System.out.println("created HugMeUser for app user");
            }
        });

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_matchmakingUI);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}