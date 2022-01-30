package com.diamondTierHuggers.hugMeCampus;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.diamondTierHuggers.hugMeCampus.matchmaking.MatchMakingQueue;
import com.diamondTierHuggers.hugMeCampus.matchmaking.OnGetDataListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashSet;

public class MatchmakingUI extends AppCompatActivity {

    private Button acceptButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://hugmecampus-dff8c-default-rtdb.firebaseio.com/");
        DatabaseReference myRef = database.getReference("users");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matchmaking_ui);
        MatchMakingQueue mq = new MatchMakingQueue();

        // TODO: start loading screen, maybe new fragment or something or else the rest of the code will run and will error polling from queue before queue is ready

        mq.readData(myRef.orderByChild("age"), new OnGetDataListener() {
            @Override
            public void onSuccess(String dataSnapshotValue) {
                // TODO: exit loading screen
                System.out.println("finished loading mq");
            }
        });

        acceptButton = findViewById(R.id.Accept);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(mq.poll().toString());
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