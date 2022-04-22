package com.diamondTierHuggers.hugMeCampus.profiles;

import static com.diamondTierHuggers.hugMeCampus.loginRegisterForgot.LoginFragment.appUser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ProfileAdapter extends BaseAdapter {

    private FragmentDisplayUserProfileBinding binding;
    private final Object mLock = new Object();
    private ArrayList<HugMeUser> mOriginalValues;

    private ArrayList<HugMeUser> mObjects;
    private final LayoutInflater mInflater;
    private final Context mContext;
    private final String[] gender = {"Male", "Female"};
    private final String[] emoji = {"💩", "🪨", "🥉", "🥈", "🏅", "💿", "💎"};
    private boolean mNotifyOnChange = true;
    private TextView rating;

    public ProfileAdapter(Context context, ArrayList<HugMeUser> objects) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mObjects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(mInflater, parent, position);
    }

    public void add(HugMeUser object) {
        synchronized (mLock) {
            if (mOriginalValues != null) {
                mOriginalValues.add(object);
            } else {
                mObjects.add(object);
            }
        }
        if (mNotifyOnChange) notifyDataSetChanged();
    }

    public void remove(int object) {
        synchronized (mLock) {
            if (mOriginalValues != null) {
                mOriginalValues.remove(object);
            } else {
                mObjects.remove(object);
            }
        }
        if (mNotifyOnChange) notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        mNotifyOnChange = true;
    }

    public void setNotifyOnChange(boolean notifyOnChange) {
        mNotifyOnChange = notifyOnChange;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public int getCount() {
        return mObjects.size();
    }

    @Override
    public HugMeUser getItem(int position) {
        return mObjects.get(position);
    }

    public int getPosition(HugMeUser item) {
        return mObjects.indexOf(item);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private View createViewFromResource(LayoutInflater inflater, ViewGroup parent, int position) {

        binding = FragmentDisplayUserProfileBinding.inflate(inflater, parent, false);
        View view = binding.getRoot();

        final HugMeUser item = getItem(position);

        ((TextView) view.findViewById(R.id.name)).setText(item.first_name + " " + item.last_name);
        ((TextView) view.findViewById(R.id.info)).setText(item.age + ", " + gender[item.gender] + ", " + emoji[item.hug_count/50]);
        ((TextView) view.findViewById(R.id.bio)).setText(item.bio);


        this.rating = view.findViewById(R.id.averageRating);

        if(item.num_reviews <= 0)
        {
            rating.setText("");
            rating.setClickable(false);
        }
        else
        {
            DecimalFormat df = new DecimalFormat("#.0");
            String avgRatingText = "★ " + df.format(item.total_rating / (float)item.num_reviews);
            rating.setText(avgRatingText);
//            rating.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Bundle bundle = new Bundle();
//                    bundle.putString("uid", item.getUid());
//                    NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_nav_other_profile_to_viewhugs, bundle);
//                }
//            });
        }
        // hug prefs

        if (!item.getHug_preferences().get("short")) {
            view.findViewById(R.id.shortHug).setVisibility(View.GONE);
        }
        if (!item.getHug_preferences().get("medium")) {
            view.findViewById(R.id.mediumHug).setVisibility(View.GONE);
        }
        if (!item.getHug_preferences().get("long")) {
            view.findViewById(R.id.longHug).setVisibility(View.GONE);
        }

        if (!item.getHug_preferences().get("celebratory")) {
            view.findViewById(R.id.celebratory).setVisibility(View.GONE);
        }

        if (!item.getHug_preferences().get("emotional")) {
            view.findViewById(R.id.emotional).setVisibility(View.GONE);
        }

        if (!item.getHug_preferences().get("happy")) {
            view.findViewById(R.id.happy).setVisibility(View.GONE);
        }

        if (!item.getHug_preferences().get("quiet")) {
            view.findViewById(R.id.quiet).setVisibility(View.GONE);
        }

        if (!item.getHug_preferences().get("sad")) {
            view.findViewById(R.id.sad).setVisibility(View.GONE);
        }

        if (!item.getHug_preferences().get("talkative")) {
            view.findViewById(R.id.talkative).setVisibility(View.GONE);
        }

        if (!item.getHug_preferences().get("male")) {
            view.findViewById(R.id.male).setVisibility(View.GONE);
        }

        if (!item.getHug_preferences().get("female")) {
            view.findViewById(R.id.female).setVisibility(View.GONE);
        }

        if (!item.getHug_preferences().get("nonbinary")) {
            view.findViewById(R.id.nonbinary).setVisibility(View.GONE);
        }

        if (!item.getUid().equals(appUser.getAppUser().getUid())) {
            if (item.getHug_preferences().get("short") && appUser.getAppUser().getHug_preferences().get("short")) {
                ((CardView) view.findViewById(R.id.shortHug)).setCardBackgroundColor(0xff03dac5);
                ((TextView)view.findViewById(R.id.shortText)).setTextColor(0xff34223b);
            }
            if (item.getHug_preferences().get("medium") && appUser.getAppUser().getHug_preferences().get("medium")) {
                ((CardView) view.findViewById(R.id.mediumHug)).setCardBackgroundColor(0xff03dac5);
                ((TextView)view.findViewById(R.id.mediumText)).setTextColor(0xff34223b);
            }
            if (item.getHug_preferences().get("long") && appUser.getAppUser().getHug_preferences().get("long")) {
                ((CardView) view.findViewById(R.id.longHug)).setCardBackgroundColor(0xff03dac5);
                ((TextView)view.findViewById(R.id.longText)).setTextColor(0xff34223b);
            }

            if (item.getHug_preferences().get("celebratory") && appUser.getAppUser().getHug_preferences().get("celebratory")) {
                ((CardView) view.findViewById(R.id.celebratory)).setCardBackgroundColor(0xff03dac5);
                ((TextView)view.findViewById(R.id.celebratoryText)).setTextColor(0xff34223b);
            }

            if (item.getHug_preferences().get("emotional") && appUser.getAppUser().getHug_preferences().get("emotional")) {
                ((CardView) view.findViewById(R.id.emotional)).setCardBackgroundColor(0xff03dac5);
                ((TextView)view.findViewById(R.id.emotionalText)).setTextColor(0xff34223b);
            }

            if (item.getHug_preferences().get("happy") && appUser.getAppUser().getHug_preferences().get("happy")) {
                ((CardView) view.findViewById(R.id.happy)).setCardBackgroundColor(0xff03dac5);
                ((TextView)view.findViewById(R.id.happyText)).setTextColor(0xff34223b);
            }

            if (item.getHug_preferences().get("quiet") && appUser.getAppUser().getHug_preferences().get("quiet")) {
                ((CardView) view.findViewById(R.id.quiet)).setCardBackgroundColor(0xff03dac5);
                ((TextView)view.findViewById(R.id.quietText)).setTextColor(0xff34223b);
            }

            if (item.getHug_preferences().get("sad") && appUser.getAppUser().getHug_preferences().get("sad")) {
                ((CardView) view.findViewById(R.id.sad)).setCardBackgroundColor(0xff03dac5);
                ((TextView)view.findViewById(R.id.sadText)).setTextColor(0xff34223b);
            }

            if (item.getHug_preferences().get("talkative") && appUser.getAppUser().getHug_preferences().get("talkative")) {
                ((CardView) view.findViewById(R.id.talkative)).setCardBackgroundColor(0xff03dac5);
                ((TextView)view.findViewById(R.id.talkativeText)).setTextColor(0xff34223b);
            }

            if (item.getHug_preferences().get(appUser.getAppUser().getGenderString()) && appUser.getAppUser().getHug_preferences().get(item.getGenderString())) {
                if (appUser.getAppUser().getGender() == 0) {
                    ((CardView) view.findViewById(R.id.male)).setCardBackgroundColor(0xff03dac5);
                    ((TextView) view.findViewById(R.id.maleText)).setTextColor(0xff34223b);
                }
                else if (appUser.getAppUser().getGender() == 1) {
                    ((CardView) view.findViewById(R.id.female)).setCardBackgroundColor(0xff03dac5);
                    ((TextView) view.findViewById(R.id.femaleText)).setTextColor(0xff34223b);
                }
                else {
                    ((CardView) view.findViewById(R.id.nonbinary)).setCardBackgroundColor(0xff03dac5);
                    ((TextView) view.findViewById(R.id.nonbinaryText)).setTextColor(0xff34223b);
                }
            }

        }


        binding.carousel4.setCarouselListener((CarouselListener) (new CarouselListener() {
            @Override
            public ViewBinding onCreateViewHolder(LayoutInflater layoutInflater, ViewGroup parent) {
                return ItemCustomFixedSizeLayout3Binding.inflate(layoutInflater, parent, false);
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

        // TODO set user pictures here
        List<CarouselItem> list = new ArrayList<>();
        list.add(
                new CarouselItem(
//                        "https://firebasestorage.googleapis.com/v0/b/hugmecampus-dff8c.appspot.com/o/Screen%20Shot%202022-02-13%20at%201.14.13%20PM.png?alt=media&token=ffc414fb-0524-404a-988a-61c5ede309f6"
                        "https://firebasestorage.googleapis.com/v0/b/hugmecampus-dff8c.appspot.com/o/Screen%20Shot%202022-02-13%20at%201.04.37%20PM.png?alt=media&token=d9303bfc-a962-4a6e-bd57-74b740a7bfd9"

//                        R.drawable.bryce_canyon
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

        if (position == mObjects.size() - 1) {
            view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_in_top));
        }

        return view;

    }

}
