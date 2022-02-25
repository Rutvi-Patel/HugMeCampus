package com.diamondTierHuggers.hugMeCampus;

import static com.diamondTierHuggers.hugMeCampus.queryDB.AppUser.mq;

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

import com.diamondTierHuggers.hugMeCampus.databinding.FragmentLoginBinding;
import com.diamondTierHuggers.hugMeCampus.queryDB.AppUser;
import com.diamondTierHuggers.hugMeCampus.queryDB.OnGetDataListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    TextView createNewAccount, forgotPassword;
    EditText inputPassword, inputEmail;
    Button loginBtn,  profileBtn;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    ProgressDialog progressDialog;
    public static AppUser appUser = new AppUser();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        createNewAccount = view.findViewById(R.id.textViewhaveacccount);
        inputEmail = view.findViewById(R.id.editTextTextEmailAddress);
        inputPassword = view.findViewById(R.id.editTextTextPassword2);
        loginBtn = view.findViewById(R.id.btnSignup);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(getActivity());
        forgotPassword = view.findViewById(R.id.textViewForgotPassword);
        mUser = mAuth.getCurrentUser();
        profileBtn = view.findViewById(R.id.profile);


        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://hugmecampus-dff8c-default-rtdb.firebaseio.com/");
                FirebaseAuth auth = FirebaseAuth.getInstance();
                appUser.readData(database.getReference("users").child("uid123"), new OnGetDataListener() {  //.child(auth.getUid()), new OnGetDataListener() {
                    @Override
                    public void onSuccess(String dataSnapshotValue) {
                        System.out.println("created HugMeUser for app user");

                        // TODO move this to on click from navigation then start loading screen
                        mq.readData(database.getReference("users").orderByChild("online").equalTo(true), new OnGetDataListener() {
                            @Override
                            public void onSuccess(String dataSnapshotValue) {
                                System.out.println("finished loading mq");
                                NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.action_SecondFragment_to_profileActivity);

                            }
                        });
                    }
                });
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
                if (inputValidation()){
                        performLogin();
                    }else{
                        if (inputEmail.getText().toString().isEmpty())
                        inputEmail.setError("Enter email");
                        else{
                            inputPassword.setError("Enter password");
                        }
                    }
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

    public boolean inputValidation(){
        if (binding.editTextTextEmailAddress.getText().toString().isEmpty()|| binding.editTextTextPassword2.getText().toString().isEmpty()){
            return false;
        }
        return true;
    }
    private void performLogin(){
        String email = inputEmail.getText().toString();
        mAuth.signOut();
        String password = inputPassword.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    if (!mAuth.getCurrentUser().isEmailVerified()) {
                        inputEmail.setError("Verify your email");
                        mAuth.signOut();
                    } else {
                        //TODO move to after logging in, queries for the app users data in db, needed for matchmaking and displaying users profile
                        FirebaseDatabase database = FirebaseDatabase.getInstance("https://hugmecampus-dff8c-default-rtdb.firebaseio.com/");
                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        appUser.readData(database.getReference("users").child("uid123"), new OnGetDataListener() {  //.child(auth.getUid()), new OnGetDataListener() {
                            @Override
                            public void onSuccess(String dataSnapshotValue) {
                                System.out.println("created HugMeUser for app user");

                                //TODO move this to on click from navigation then start loading screen
                                mq.readData(database.getReference("users").orderByChild("online").equalTo(true), new OnGetDataListener() {
                                    @Override
                                    public void onSuccess(String dataSnapshotValue) {
                                        System.out.println("finished loading mq");
                                        Toast.makeText(getActivity().getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                        NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.action_SecondFragment_to_profileActivity);
                                    }
                                });
                            }
                        });
                        //task.getResult().getUser();

                    }

                } else {
                    progressDialog.dismiss();

                    if (task.getException().toString().contains("InvalidCredentialsException")) {
                        Toast.makeText(getActivity().getApplicationContext(), "Enter valid credentials", Toast.LENGTH_SHORT).show();
                    } else if (task.getException().toString().contains("TooManyRequestsException")) {
                        Toast.makeText(getActivity().getApplicationContext(), "Unusual activity. Try again later.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
