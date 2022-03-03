package com.diamondTierHuggers.hugMeCampus;

import static android.app.Activity.RESULT_OK;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import static com.diamondTierHuggers.hugMeCampus.LoginFragment.appUser;

import static com.diamondTierHuggers.hugMeCampus.MainActivity.myRef;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.PreferenceManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.diamondTierHuggers.hugMeCampus.entity.Model;
import com.diamondTierHuggers.hugMeCampus.entity.UserPictures;
import com.diamondTierHuggers.hugMeCampus.queryDB.AppUser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.prefs.PreferenceChangeEvent;


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
    private EditText firstName, lastName, bio;
    private Spinner editGender;
    private static final String[] paths = {"Male", "Female", "Non-binary"};
    private int genderChoice;
    private Button uploadBtn, saveEditBtn;
    private ImageView imageView;
    private Uri imageUri;
    private StorageReference reference = FirebaseStorage.getInstance().getReference();
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("Image");
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

        uploadBtn = view.findViewById(R.id.upload_Button);
        saveEditBtn = view.findViewById(R.id.save_edits);
        imageView = view.findViewById(R.id.viewImage);
        editGender = (Spinner)view.findViewById(R.id.editGender);


        //Edit Gender dropdown setup
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(editGender.getContext(), android.R.layout.simple_spinner_item,paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editGender.setAdapter(adapter);

        editGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("gender", (String) parent.getItemAtPosition(position));
                genderChoice = editGender.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Image Button
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);
            }
        });
        //Upload Button
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageUri != null) {
                    uploadToFirebase(imageUri);
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(), "Please Select Image", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //Save Button
        saveEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firstName = view.findViewById(R.id.edit_firstName);
                lastName = view.findViewById(R.id.edit_lastName);
                bio = view.findViewById(R.id.edit_bio);
                editGender = view.findViewById(R.id.editGender);

                String firstNameToString = firstName.getText().toString();
                String lastNameToString = lastName.getText().toString();
                String bioToString = bio.getText().toString();

                appUser.getAppUser().setFirst_name(firstNameToString);
                appUser.getAppUser().setLast_name(lastNameToString);
                appUser.getAppUser().setBio(bioToString);

                myRef.child("users").child(myUID).child("first_name").setValue(firstNameToString);
                myRef.child("users").child(myUID).child("last_name").setValue(lastNameToString);
                myRef.child("users").child(myUID).child("bio").setValue(bioToString);
                //Edit Gender
                myRef.child("users").child(myUID).child("gender").setValue(genderChoice);

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
                male = view.findViewById(R.id.male);
                female = view.findViewById(R.id.female);
                nonbinary = view.findViewById(R.id.nonBinary);


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

                if(male.isChecked()){
                    myRef.child("users").child(myUID).child("hug_preferences").child("male").setValue(true);
                }else{
                    myRef.child("users").child(myUID).child("hug_preferences").child("male").setValue(false);
                }

                if(female.isChecked()){
                    myRef.child("users").child(myUID).child("hug_preferences").child("female").setValue(true);
                }else{
                    myRef.child("users").child(myUID).child("hug_preferences").child("female").setValue(false);
                }

                if(nonbinary.isChecked()){
                    myRef.child("users").child(myUID).child("hug_preferences").child("nonbinary").setValue(true);
                }else{
                    myRef.child("users").child(myUID).child("hug_preferences").child("nonbinary").setValue(false);
                }

                NavHostFragment.findNavController(EditProfile.this).navigate(R.id.editProfile_to_editUserProfile);

                System.out.println(appUser.getAppUser().getUid());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    private void uploadToFirebase(Uri uri) {
        StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Model model = new Model(uri.toString());
                        String modelID = root.push().getKey();
                        root.child(modelID).setValue(model);
                        Toast.makeText(getActivity().getApplicationContext(), "Uploaded Successfully!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
//        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//
//            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity().getApplicationContext(), "Uploading Failed!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private String getFileExtension(Uri mUri) {
        ContentResolver cr = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }

}