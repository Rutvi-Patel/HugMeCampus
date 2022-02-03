package com.diamondTierHuggers.hugMeCampus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MatchmakingUI extends AppCompatActivity {

    private Button acceptButton;
    private Button declineButton;
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
                AcceptListModel model = new AcceptListModel();

                model.isUserAccepted(uid,"test", new BoolDataCallback() {
                    @Override
                    public void getBool(boolean value) {
                        if(value) {
                            Toast.makeText(getApplicationContext(), "It's a match!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Match not found!", Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });

        declineButton = findViewById(R.id.Decline);
        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RejectListModel model = new RejectListModel();
                model.isUserRejected("1", "aUserUID", new BoolDataCallback() {
                    @Override
                    public void getBool(boolean value) {
                        if(value) {
                            Toast.makeText(getApplicationContext(), "It's a match!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Match not found!", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });

    }


}