package com.diamondTierHuggers.hugMeCampus;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.diamondTierHuggers.hugMeCampus.databinding.ActivityMainBinding;
import com.diamondTierHuggers.hugMeCampus.databinding.ActivityMatchmakingUiBinding;
import com.google.api.Context;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashSet;

public class MatchmakingUI extends AppCompatActivity {

    private Button acceptButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matchmaking_ui);

        acceptButton = findViewById(R.id.Accept);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AcceptListModel accepted = new AcceptListModel();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String uid = "uid123";
                if(auth.getUid() != null) {
                    uid = auth.getUid();
                }
                accepted.getAcceptedUsersSet(uid, new AcceptedListHashSetData() {
                    @Override
                    public void GetUserAcceptList(HashSet<String> set) {
                        if(set != null && set.contains("joe")) {
                            Toast.makeText(getApplicationContext(), "It's a match!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Adding user to our match list", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });


    }


}