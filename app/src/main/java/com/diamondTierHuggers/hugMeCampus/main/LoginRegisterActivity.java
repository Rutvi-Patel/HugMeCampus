package com.diamondTierHuggers.hugMeCampus.main;

import android.Manifest;
import android.content.Intent;
import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.snackbar.Snackbar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.diamondTierHuggers.hugMeCampus.R;
import com.diamondTierHuggers.hugMeCampus.databinding.ActivityLoginRegisterBinding;
import com.diamondTierHuggers.hugMeCampus.databinding.ActivityMainBinding;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashSet;

public class LoginRegisterActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityLoginRegisterBinding binding;
    private FirebaseAnalytics mFirebaseAnalytics;
    public static final FirebaseDatabase database = FirebaseDatabase.getInstance("https://hugmecampus-dff8c-default-rtdb.firebaseio.com/");
    public static final DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityLoginRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://hugmecampus-dff8c-default-rtdb.firebaseio.com/");

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

//        binding.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                mFirebaseAnalytics.logEvent("button_clicked", null);
//            }
//        });


//                FirebaseMessaging.getInstance().getToken()
//                .addOnCompleteListener(new OnCompleteListener<String>() {
//                    @Override
//                    public void onComplete(@NonNull Task<String> task) {
//                        if (!task.isSuccessful()) {
////                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
//                            return;
//                        }
//
//                        // Get new FCM registration token
//                        String token = task.getResult();
//                        System.out.println("TOKEN>>>>:" + token);
//
//                        // Log and toast
////                        String msg = getString(R.string.msg_token_fmt, token);
////                        Log.d(TAG, msg);
////                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
//                    }
//                });
//        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}