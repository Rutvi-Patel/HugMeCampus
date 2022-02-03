package com.diamondTierHuggers.hugMeCampus;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.diamondTierHuggers.hugMeCampus.databinding.FragmentSecondBinding;
import com.diamondTierHuggers.hugMeCampus.queryDB.AppUser;
import com.diamondTierHuggers.hugMeCampus.queryDB.OnGetDataListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class LoginFragment extends Fragment {

    private FragmentSecondBinding binding;
    TextView createNewAccount, forgotPassword;
    EditText inputPassword, inputEmail;
    Button loginBtn, matchmakingBtn, profileBtn;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    ProgressDialog progressDialog;
    public static AppUser appUser = new AppUser();



    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
            super.onViewCreated(view, savedInstanceState);

            createNewAccount = view.findViewById(R.id.textViewhaveacccount);
            inputEmail = view.findViewById(R.id.editTextTextEmailAddress);
            inputPassword = view.findViewById(R.id.editTextTextPassword2);
            loginBtn = view.findViewById(R.id.btnSignup);
            mAuth = FirebaseAuth.getInstance();
            progressDialog = new ProgressDialog(getActivity());
            forgotPassword = view.findViewById(R.id.textViewForgotPassword);
            mUser = mAuth.getCurrentUser();
            matchmakingBtn = view.findViewById(R.id.matchmaking);
            profileBtn = view.findViewById(R.id.profile);


        matchmakingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://hugmecampus-dff8c-default-rtdb.firebaseio.com/");
                FirebaseAuth auth = FirebaseAuth.getInstance();
                appUser.readData(database.getReference("users").child("uid123"), new OnGetDataListener() {  //.child(auth.getUid()), new OnGetDataListener() {
                    @Override
                    public void onSuccess(String dataSnapshotValue) {
                        System.out.println("created HugMeUser for app user");
                        NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.action_SecondFragment_to_matchmakingUI);
                    }
                });

            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.action_SecondFragment_to_displayUserProfile2);
            }
        });


        createNewAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.action_SecondFragment_to_FirstFragment);
                }
            });

            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    performLogin();
                }
            });

            forgotPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.action_SecondFragment_to_forgotFragment);
                }
            });
        }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void performLogin(){
        String email = inputEmail.getText().toString();
        mAuth.signOut();
        String password = inputPassword.getText().toString();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        progressDialog.dismiss();
                        if (!mAuth.getCurrentUser().isEmailVerified()){
                            inputEmail.setError("Verify your email");
                            mAuth.signOut();
                        }
                        else {
                            //TODO move to after logging in, queries for the app users data in db, needed for matchmaking and displaying users profile
                            FirebaseDatabase database = FirebaseDatabase.getInstance("https://hugmecampus-dff8c-default-rtdb.firebaseio.com/");
                            FirebaseAuth auth = FirebaseAuth.getInstance();
                            appUser.readData(database.getReference("users").child("uid123"), new OnGetDataListener() {  //.child(auth.getUid()), new OnGetDataListener() {
                                @Override
                                public void onSuccess(String dataSnapshotValue) {
                                    System.out.println("created HugMeUser for app user");
                                }
                            });
                            Toast.makeText(getActivity().getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                            NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.action_SecondFragment_to_displayUserProfile2);
                        }

                    }else {
                        progressDialog.dismiss();

                        if (task.getException().toString().contains("InvalidCredentialsException")){
                            Toast.makeText(getActivity().getApplicationContext(), "Enter valid credentials", Toast.LENGTH_SHORT).show();
                        }else if(task.getException().toString().contains("TooManyRequestsException")){
                            Toast.makeText(getActivity().getApplicationContext(), "Unusual activity. Try again later.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getActivity().getApplicationContext(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }
