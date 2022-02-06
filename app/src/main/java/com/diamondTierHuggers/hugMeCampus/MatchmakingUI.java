package com.diamondTierHuggers.hugMeCampus;

import static com.diamondTierHuggers.hugMeCampus.queryDB.AppUser.mq;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.diamondTierHuggers.hugMeCampus.entity.HugMeUser;
import com.google.firebase.database.FirebaseDatabase;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import java.util.ArrayList;


public class MatchmakingUI extends AppCompatActivity {

    private Button acceptButton;
    private Button declineButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://hugmecampus-dff8c-default-rtdb.firebaseio.com/");
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

        //add the view via xml or programmatically
        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);

        ArrayList al = new ArrayList<HugMeUser>();

        al.add(mq.poll());
        al.add(mq.poll());


        ProfileAdapter arrayAdapter = new ProfileAdapter(this, al );

        //set the listener and the adapter
        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                System.out.println("LIST removed object!");
                al.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
//                Toast.makeText(this, "Left!", Toast.LENGTH_SHORT).show();
                System.out.println("LEFT");
                al.add(mq.poll());
            }

            @Override
            public void onRightCardExit(Object dataObject) {
//                Toast.makeText(MyActivity.this, "Right!", Toast.LENGTH_SHORT).show();
                System.out.println("RIGHT");
                al.add(mq.poll());
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
//                al.add("XML ".concat(String.valueOf(i)));
//                arrayAdapter.notifyDataSetChanged();
//                Log.d("LIST", "notified");
//                i++;
                System.out.println("ABOUT TO EMPTY");
            }

            @Override
            public void onScroll(float v) {
                System.out.println("SCROLL");
            }
        });

        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
//                makeToast(MyActivity.this, "Clicked!");
                System.out.println("CLICKED");
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