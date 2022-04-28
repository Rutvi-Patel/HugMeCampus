package com.diamondTierHuggers.hugMeCampus.profiles;

import static android.app.Activity.RESULT_OK;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import static com.diamondTierHuggers.hugMeCampus.loginRegisterForgot.LoginFragment.appUser;

import static com.diamondTierHuggers.hugMeCampus.main.LoginRegisterActivity.myRef;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;


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

import com.diamondTierHuggers.hugMeCampus.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfile extends Fragment {

    private StorageReference storageReference;
    private String myUID = appUser.getAppUser().getUid();
    private EditText firstName, lastName, bio;
    private Spinner editGender;
    private int genderChoice, imageChanged;
    private static final String[] paths = {"Male", "Female", "Non-binary"};
    private Spinner editAge;
    private int ageChoice;
    private static final String[] ages = {"17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28",
            "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44",
            "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60",
            "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76",
            "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92",
            "93", "94", "95", "96", "97", "98", "99"};
    private Button saveEditBtn;
    private ImageView imageView, imageView2, imageView3, imageView4;
    private Uri imageUri, imageUri2, imageUri3, imageUri4;
    private StorageReference reference = FirebaseStorage.getInstance().getReference();
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("Image");
    private CheckBox shortHug, mediumHug, longHug, quiet, talkative, celebratory,
            happy, emotional, sad, male, female, nonbinary;

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
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
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
        //Save and Upload buttons
        saveEditBtn = view.findViewById(R.id.save_edits);
        //User information
        firstName = view.findViewById(R.id.edit_firstName);
        lastName = view.findViewById(R.id.edit_lastName);
        bio = view.findViewById(R.id.edit_bio);
        editGender = (Spinner) view.findViewById(R.id.editGender);
        editAge = (Spinner) view.findViewById(R.id.edit_age);
        imageView = view.findViewById(R.id.viewImage);
        imageView2 = view.findViewById(R.id.viewImage2);
        imageView3 = view.findViewById(R.id.viewImage3);
        imageView4 = view.findViewById(R.id.viewImage4);

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

        shortHug.setChecked(true);

        storageReference = FirebaseStorage.getInstance().getReference();

        //Set first and last name for editing
        firstName.setText(appUser.getAppUser().getFirst_name());
        lastName.setText(appUser.getAppUser().getLast_name());
        //set Bio for editing
        bio.setText(appUser.getAppUser().getBio());

        //Edit Gender dropdown setup
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(editGender.getContext(), android.R.layout.simple_spinner_item, paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editGender.setAdapter(adapter);
        //Set gender for editing
        editGender.setSelection(appUser.getAppUser().getGender());

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

        //Edit Age dropdown setup
        ArrayAdapter<String> adapter_2 = new ArrayAdapter<String>(editAge.getContext(), android.R.layout.simple_spinner_item, ages);
        adapter_2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editAge.setAdapter(adapter_2);
        //Set age for editing
        editAge.setSelection(appUser.getAppUser().getAge() - 17);

        editAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("age", (String) parent.getItemAtPosition(position));
                ageChoice = Integer.parseInt((String) editAge.getItemAtPosition(position));
                System.out.println(ageChoice);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (appUser.getAppUser().getPicture("picture1") != null) {
            Picasso.get().load(appUser.getAppUser().getPicture("picture1")).into(imageView);
        }
        if (appUser.getAppUser().getPicture("picture2") != null) {
            Picasso.get().load(appUser.getAppUser().getPicture("picture2")).into(imageView2);
        }
        if (appUser.getAppUser().getPicture("picture3") != null) {
            Picasso.get().load(appUser.getAppUser().getPicture("picture3")).into(imageView3);
        }
        if (appUser.getAppUser().getPicture("picture4") != null) {
            Picasso.get().load(appUser.getAppUser().getPicture("picture4")).into(imageView4);
        }

        //Image Button
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                imageChanged = 0;
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                imageChanged = 1;
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                imageChanged = 2;
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);
            }
        });

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                imageChanged = 3;
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);
            }
        });

        //Save Button
        saveEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save pictures
                saveEditBtn.setClickable(false);

                //User info to String
                String firstNameToString = firstName.getText().toString();
                String lastNameToString = lastName.getText().toString();
                String bioToString = bio.getText().toString();
                //User info being set
                appUser.getAppUser().setFirst_name(firstNameToString);
                appUser.getAppUser().setLast_name(lastNameToString);
                appUser.getAppUser().setBio(bioToString);
                appUser.getAppUser().setAge(ageChoice);
                appUser.getAppUser().setGender(genderChoice);
                //User info updated to database
                myRef.child("users").child(myUID).child("first_name").setValue(firstNameToString);
                myRef.child("users").child(myUID).child("last_name").setValue(lastNameToString);
                myRef.child("users").child(myUID).child("bio").setValue(bioToString);
                myRef.child("users").child(myUID).child("gender").setValue(genderChoice);
                myRef.child("users").child(myUID).child("age").setValue(ageChoice);

                HashMap<String, Boolean> prefs = appUser.getAppUser().getHug_preferences();
                //Hug Preferences updated to database
                if (shortHug.isChecked() != prefs.get("short")) {
                    myRef.child("users").child(myUID).child("hug_preferences").child("short").setValue(shortHug.isChecked());
                    appUser.getAppUser().getHug_preferences().put("short", shortHug.isChecked());
                }

                if (mediumHug.isChecked() != prefs.get("medium")) {
                    myRef.child("users").child(myUID).child("hug_preferences").child("medium").setValue(mediumHug.isChecked());
                    appUser.getAppUser().getHug_preferences().put("medium", mediumHug.isChecked());
                }

                if (longHug.isChecked() != prefs.get("long")) {
                    myRef.child("users").child(myUID).child("hug_preferences").child("long").setValue(longHug.isChecked());
                    appUser.getAppUser().getHug_preferences().put("long", longHug.isChecked());
                }

                if (quiet.isChecked() != prefs.get("quiet")) {
                    myRef.child("users").child(myUID).child("hug_preferences").child("quiet").setValue(quiet.isChecked());
                    appUser.getAppUser().getHug_preferences().put("quiet", quiet.isChecked());
                }

                if (talkative.isChecked() != prefs.get("talkative")) {
                    myRef.child("users").child(myUID).child("hug_preferences").child("talkative").setValue(talkative.isChecked());
                    appUser.getAppUser().getHug_preferences().put("talkative", talkative.isChecked());
                }

                if (celebratory.isChecked() != prefs.get("celebratory")) {
                    myRef.child("users").child(myUID).child("hug_preferences").child("celebratory").setValue(celebratory.isChecked());
                    appUser.getAppUser().getHug_preferences().put("celebratory", celebratory.isChecked());
                }

                if (happy.isChecked() != prefs.get("happy")) {
                    myRef.child("users").child(myUID).child("hug_preferences").child("happy").setValue(happy.isChecked());
                    appUser.getAppUser().getHug_preferences().put("happy", happy.isChecked());
                }

                if (emotional.isChecked() != prefs.get("emotional")) {
                    myRef.child("users").child(myUID).child("hug_preferences").child("emotional").setValue(emotional.isChecked());
                    appUser.getAppUser().getHug_preferences().put("emotional", emotional.isChecked());
                }

                if (sad.isChecked() != prefs.get("sad")) {
                    myRef.child("users").child(myUID).child("hug_preferences").child("sad").setValue(sad.isChecked());
                    appUser.getAppUser().getHug_preferences().put("sad", sad.isChecked());
                }

                if (male.isChecked() != prefs.get("male")) {
                    myRef.child("users").child(myUID).child("hug_preferences").child("male").setValue(male.isChecked());
                    appUser.getAppUser().getHug_preferences().put("male", male.isChecked());
                }

                if (female.isChecked() != prefs.get("female")) {
                    myRef.child("users").child(myUID).child("hug_preferences").child("female").setValue(female.isChecked());
                    appUser.getAppUser().getHug_preferences().put("female", female.isChecked());
                }

                if (nonbinary.isChecked() != prefs.get("nonbinary")) {
                    myRef.child("users").child(myUID).child("hug_preferences").child("nonbinary").setValue(nonbinary.isChecked());
                    appUser.getAppUser().getHug_preferences().put("nonbinary", nonbinary.isChecked());
                }
                //moved to profile view after Save button has been hit
                uploadPicture1();
            }
        });

        //Keep checkbox state
        HashMap<String, CheckBox> checkBoxMap = new HashMap<>();
        checkBoxMap.put("short", shortHug);
        checkBoxMap.put("medium", mediumHug);
        checkBoxMap.put("long", longHug);
        checkBoxMap.put("quiet", quiet);
        checkBoxMap.put("talkative", talkative);
        checkBoxMap.put("celebratory", celebratory);
        checkBoxMap.put("happy", happy);
        checkBoxMap.put("emotional", emotional);
        checkBoxMap.put("sad", sad);
        checkBoxMap.put("male", male);
        checkBoxMap.put("female", female);
        checkBoxMap.put("nonbinary", nonbinary);

        setupCheckBox(checkBoxMap);
    }

    private void setupCheckBox(HashMap<String, CheckBox> boxes) {
        HashMap<String, Boolean> prefs = appUser.getAppUser().getHug_preferences();
        for (String pref : prefs.keySet()) {
            boxes.get(pref).setChecked(prefs.get(pref));
        }
    }

    private void uploadPicture1() {
        Toast.makeText(getActivity().getApplicationContext(), "Saving Changes...", Toast.LENGTH_SHORT).show();
        if (imageUri != null) {
            StorageReference riversRef = storageReference.child("profile Images/" + myUID + "profilePic_1" + ".jpg");
            riversRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            Toast.makeText(getActivity().getApplicationContext(), "Uploaded Successfully!", Toast.LENGTH_SHORT).show();
                            uploadToFirebase(imageUri, "picture1");
                            appUser.getAppUser().setPicture("picture1", imageUri.toString());
                            uploadPicture2();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(getActivity().getApplicationContext(), "Upload Failed!", Toast.LENGTH_SHORT).show();
                            uploadPicture2();
                        }
                    });
        }
        else {
            uploadPicture2();
        }
    }

    private void uploadPicture2() {
        if (imageUri2 != null) {
            StorageReference riversRef = storageReference.child("profile Images/" + myUID + "profilePic_2" + ".jpg");
            riversRef.putFile(imageUri2)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            Toast.makeText(getActivity().getApplicationContext(), "Uploaded Successfully!", Toast.LENGTH_SHORT).show();
                            uploadToFirebase(imageUri2, "picture2");
                            appUser.getAppUser().setPicture("picture2", imageUri2.toString());
                            uploadPicture3();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(getActivity().getApplicationContext(), "Upload Failed!", Toast.LENGTH_SHORT).show();
                            uploadPicture3();
                        }
                    });
        }
        else {
            uploadPicture3();
        }
    }

    private void uploadPicture3() {
        if (imageUri3 != null) {
            StorageReference riversRef = storageReference.child("profile Images/" + myUID + "profilePic_3" + ".jpg");
            riversRef.putFile(imageUri3)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            Toast.makeText(getActivity().getApplicationContext(), "Uploaded Successfully!", Toast.LENGTH_SHORT).show();
                            uploadToFirebase(imageUri3, "picture3");
                            appUser.getAppUser().setPicture("picture3", imageUri3.toString());
                            uploadPicture4();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(getActivity().getApplicationContext(), "Upload Failed!", Toast.LENGTH_SHORT).show();
                            uploadPicture4();
                        }
                    });
        }
        else {
            uploadPicture4();
        }
    }

    private void uploadPicture4() {
        if (imageUri4 != null) {
            StorageReference riversRef = storageReference.child("profile Images/" + myUID + "profilePic_4" + ".jpg");
            riversRef.putFile(imageUri4)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            Toast.makeText(getActivity().getApplicationContext(), "Uploaded Successfully!", Toast.LENGTH_SHORT).show();
                            uploadToFirebase(imageUri4, "picture4");
                            appUser.getAppUser().setPicture("picture4", imageUri4.toString());
                            NavHostFragment.findNavController(EditProfile.this).navigate(R.id.editProfile_to_editUserProfile);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(getActivity().getApplicationContext(), "Upload Failed!", Toast.LENGTH_SHORT).show();
                            NavHostFragment.findNavController(EditProfile.this).navigate(R.id.editProfile_to_editUserProfile);
                        }
                    });
        }
        else {
            NavHostFragment.findNavController(EditProfile.this).navigate(R.id.editProfile_to_editUserProfile);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            if (imageChanged == 0) {
                imageUri = data.getData();
                imageView.setImageURI(imageUri);
            } else if (imageChanged == 1) {
                imageUri2 = data.getData();
                imageView2.setImageURI(imageUri2);
            } else if (imageChanged == 2) {
                imageUri3 = data.getData();
                imageView3.setImageURI(imageUri3);
            } else if (imageChanged == 3) {
                imageUri4 = data.getData();
                imageView4.setImageURI(imageUri4);
            }
        }
    }

    private void uploadToFirebase(Uri uri, String picNum) {
        StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        myRef.child("users").child(myUID).child("pictures").child(picNum).setValue(uri.toString());
                    }
                });
            }
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