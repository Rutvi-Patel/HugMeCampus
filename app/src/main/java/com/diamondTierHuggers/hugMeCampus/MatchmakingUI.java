package com.diamondTierHuggers.hugMeCampus;

import static com.diamondTierHuggers.hugMeCampus.LoginFragment.appUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.diamondTierHuggers.hugMeCampus.entity.HugMeUser;
import com.diamondTierHuggers.hugMeCampus.queryDB.MatchMakingQueue;
import com.diamondTierHuggers.hugMeCampus.queryDB.OnGetDataListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashSet;

public class MatchmakingUI extends AppCompatActivity {

    private Button acceptButton;

    private final String[] gender = {"Male", "Female"};
    private final String[] emoji = {"poop", "coal", "bronze", "silver", "gold", "platinum", "diamond"};

    private TextView name, info, bio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://hugmecampus-dff8c-default-rtdb.firebaseio.com/");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matchmaking_ui);

        MatchMakingQueue mq = new MatchMakingQueue();

        // TODO: start loading screen, maybe new fragment or something or else the rest of the code will run and will error polling from queue before queue is ready

        mq.readData(database.getReference("users").orderByChild("online").equalTo(true), new OnGetDataListener() {
            @Override
            public void onSuccess(String dataSnapshotValue) {
                // TODO: exit loading screen
                System.out.println("finished loading mq");
                name = findViewById(R.id.name);
                info = findViewById(R.id.info);
                bio = findViewById(R.id.bio);
                HugMeUser h = mq.poll();
                System.out.println(h);
                name.setText(h.first_name + " " + h.last_name);
                info.setText(h.age + ", " + gender[h.gender] + ", " + emoji[h.hug_count/50]);
                bio.setText(h.bio);
            }
        });

        acceptButton = findViewById(R.id.Accept);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HugMeUser h = mq.poll();

                name = findViewById(R.id.name);
                info = findViewById(R.id.info);
                bio = findViewById(R.id.bio);

                name.setText(h.first_name + " " + h.last_name);
                info.setText(h.age + ", " + gender[h.gender] + ", " + emoji[h.hug_count/50]);
                bio.setText(h.bio);


//                AcceptListModel accepted = new AcceptListModel();
//                FirebaseAuth auth = FirebaseAuth.getInstance();
//                String uid = "uid123";
//                if(auth.getUid() != null) {
//                    uid = auth.getUid();
//                }
//                accepted.getAcceptedUsersSet(uid, new AcceptedListHashSetData() {
//                    @Override
//                    public void GetUserAcceptList(HashSet<String> set) {
//                        if(set != null && set.contains("joe")) {
//                            Toast.makeText(getApplicationContext(), "It's a match!", Toast.LENGTH_LONG).show();
//                        } else {
//                            Toast.makeText(getApplicationContext(), "Adding user to our match list", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });

            }
        });


    }

}