package com.diamondTierHuggers.hugMeCampus.matchmaking;

import static com.diamondTierHuggers.hugMeCampus.main.LoginRegisterActivity.database;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diamondTierHuggers.hugMeCampus.R;
import com.diamondTierHuggers.hugMeCampus.main.OnGetDataListener;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class LoadMQFragment extends Fragment {

    public static MatchMakingQueue mq;
    private boolean matchmakingReady = false;

    public LoadMQFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_load_m_q, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GifImageView logoView = (GifImageView) view.findViewById(R.id.logoImageView);
        ((GifDrawable) logoView.getDrawable()).setLoopCount(0);

        new CountDownTimer(3000, 3000) {

            public void onTick(long millisUntilFinished) {
                // You don't need to use this.
            }

            public void onFinish() {
                // Put the code to stop the GIF here.
                if (matchmakingReady) {
                    matchmakingReady = false;
                    NavHostFragment.findNavController(LoadMQFragment.this).navigate(R.id.action_nav_load_mq_to_nav_matchmaking);
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

}