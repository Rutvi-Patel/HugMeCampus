package com.diamondTierHuggers.hugMeCampus;

import android.os.Bundle;

import static com.diamondTierHuggers.hugMeCampus.LoginFragment.appUser;

import static com.diamondTierHuggers.hugMeCampus.MainActivity.myRef;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.diamondTierHuggers.hugMeCampus.queryDB.AppUser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String myUID = appUser.getAppUser().getUid();
    private EditText firstName, lastName, age, gender, bio;
    private CheckBox shortHug,mediumHug,longHug, quiet,talkative,celebratory,
            happy,emotional,sad,male,female,nonbinary;

    public EditProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static EditProfile newInstance(String param1, String param2) {
        EditProfile fragment = new EditProfile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button saveEditBtn = view.findViewById(R.id.save_edits);

        saveEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firstName = view.findViewById(R.id.edit_firstName);
                lastName = view.findViewById(R.id.edit_lastName);
                bio = view.findViewById(R.id.edit_bio);

                String firstNameToString = firstName.getText().toString();
                String lastNameToString = lastName.getText().toString();
                String bioToString = bio.getText().toString();

                appUser.getAppUser().setFirst_name(firstNameToString);
                appUser.getAppUser().setLast_name(lastNameToString);
                appUser.getAppUser().setBio(bioToString);

                myRef.child("users").child(myUID).child("first_name").setValue(firstNameToString);
                myRef.child("users").child(myUID).child("last_name").setValue(lastNameToString);
                myRef.child("users").child(myUID).child("bio").setValue(bioToString);

                //Hug Preferences
                shortHug = view.findViewById(R.id.shortHug);
                mediumHug = view.findViewById(R.id.mediumHug);
                longHug = view.findViewById(R.id.longHug);
                quiet = view.findViewById(R.id.quiet);
                talkative = view.findViewById(R.id.talkative);
                celebratory = view.findViewById(R.id.celebratory);
                happy = view.findViewById(R.id.happy);
                emotional = view.findViewById(R.id.emotional);
                sad = view.findViewById(R.id.sad);
//                male = view.findViewById(R.id.male);
//                female = view.findViewById(R.id.female);
//                nonbinary = view.findViewById(R.id.nonbinary);


                if(shortHug.isChecked()){
                    myRef.child("users").child(myUID).child("hug_preferences").child("short").setValue(true);
//                    boolean checked = PreferenceManager.getDefaultSharedPreferences(shortHug.getContext()).getBoolean("shortHug",false);
//                    PreferenceManager.getDefaultSharedPreferences(shortHug.getContext()).edit().putBoolean("shortHug",checked).apply();
//                    shortHug.setChecked(checked);
                }else{
                    myRef.child("users").child(myUID).child("hug_preferences").child("short").setValue(false);
                }

                if(mediumHug.isChecked()){
                    myRef.child("users").child(myUID).child("hug_preferences").child("medium").setValue(true);
                }else{
                    myRef.child("users").child(myUID).child("hug_preferences").child("medium").setValue(false);
                }

                if(longHug.isChecked()){
                    myRef.child("users").child(myUID).child("hug_preferences").child("long").setValue(true);
                }else{
                    myRef.child("users").child(myUID).child("hug_preferences").child("long").setValue(false);
                }

                if(quiet.isChecked()){
                    myRef.child("users").child(myUID).child("hug_preferences").child("celebratory").setValue(true);
                }else{
                    myRef.child("users").child(myUID).child("hug_preferences").child("celebratory").setValue(false);
                }

                if(talkative.isChecked()){
                    myRef.child("users").child(myUID).child("hug_preferences").child("emotional").setValue(true);
                }else{
                    myRef.child("users").child(myUID).child("hug_preferences").child("emotional").setValue(false);
                }

                if(celebratory.isChecked()){
                    myRef.child("users").child(myUID).child("hug_preferences").child("quiet").setValue(true);
                }else{
                    myRef.child("users").child(myUID).child("hug_preferences").child("quiet").setValue(false);
                }

                if(happy.isChecked()){
                    myRef.child("users").child(myUID).child("hug_preferences").child("talkative").setValue(true);
                }else{
                    myRef.child("users").child(myUID).child("hug_preferences").child("talkative").setValue(false);
                }

                if(emotional.isChecked()){
                    myRef.child("users").child(myUID).child("hug_preferences").child("happy").setValue(true);
                }else{
                    myRef.child("users").child(myUID).child("hug_preferences").child("happy").setValue(false);
                }

                if(sad.isChecked()){
                    myRef.child("users").child(myUID).child("hug_preferences").child("sad").setValue(true);
                }else{
                    myRef.child("users").child(myUID).child("hug_preferences").child("sad").setValue(false);
                }

//                if(male.isChecked()){
//                    myRef.child("users").child(myUID).child("hug_preferences").child("male").setValue(true);
//                }else{
//                    myRef.child("users").child(myUID).child("hug_preferences").child("male").setValue(false);
//                }
//
//                if(female.isChecked()){
//                    myRef.child("users").child(myUID).child("hug_preferences").child("female").setValue(true);
//                }else{
//                    myRef.child("users").child(myUID).child("hug_preferences").child("female").setValue(false);
//                }
//
//                if(nonbinary.isChecked()){
//                    myRef.child("users").child(myUID).child("hug_preferences").child("nonbinary").setValue(true);
//                }else{
//                    myRef.child("users").child(myUID).child("hug_preferences").child("nonbinary").setValue(false);
//                }
                NavHostFragment.findNavController(EditProfile.this).navigate(R.id.editProfile_to_editUserProfile);

                //System.out.println(appUser.getAppUser().getUid());
            }
        });
    }
}