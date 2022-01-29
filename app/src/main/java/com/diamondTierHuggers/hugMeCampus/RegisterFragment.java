package com.diamondTierHuggers.hugMeCampus;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.diamondTierHuggers.hugMeCampus.databinding.FragmentFirstBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterFragment extends Fragment {

    private FragmentFirstBinding binding;
    private FirebaseAuth auth;
    EditText emailInput;
    EditText pwdInput;

    private View view;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        auth = FirebaseAuth.getInstance();

//        FirebaseDatabase database = FirebaseDatabase.getInstance("https://hugmecampus-dff8c-default-rtdb.firebaseio.com/");
//        DatabaseReference myRef = database.getReference("UserInput");

        pwdInput = (EditText) view.findViewById(R.id.pwdInput);
        emailInput = (EditText) view.findViewById(R.id.emailInput);

        binding.sendDBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUserAccount(emailInput.getText().toString(), pwdInput.getText().toString());
            }
        });

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendUserToNextFragment();
//                NavHostFragment.findNavController(FirstFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }

//    private void signInUser(String email, String pwd) {
//        auth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful()) {
//                    //needs to be implemented
//                    sendUserToNextFragment();
//                    Toast.makeText(getActivity().getApplicationContext(), "Signin Successful", Toast.LENGTH_SHORT).show();
//
//                } else {
//                    //needs to be implemented
//                    Toast.makeText(getActivity(),""+task.getException(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }

    private void sendEmailVerification() {
        FirebaseUser user = auth.getCurrentUser();
        if(user == null) {
            Toast.makeText(getActivity().getApplicationContext(), "USER IS NULL", Toast.LENGTH_LONG).show();
        }
        if(user != null && !user.isEmailVerified()) {
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(getActivity().getApplicationContext(), "Email Verification sent.", Toast.LENGTH_LONG).show();
                        sendUserToNextFragment();
                        pwdInput.getText().clear();
                        emailInput.getText().clear();
                    } else {
                        try {
                            throw task.getException();
                        } catch (Exception e) {
                            Toast.makeText(getActivity().getApplicationContext(), "Error: Could not send verification email.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });


        }
    }

    private boolean createUserAccount(String email, String pwd) {
        final boolean[] success = {false};

        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if(email.toLowerCase().endsWith("@student.csulb.edu")) {

                auth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(getActivity().getApplicationContext(), "Account Created.", Toast.LENGTH_LONG).show();
                            success[0] = true;
                            sendEmailVerification();
                            NavHostFragment.findNavController(FirstFragment.this)
                                    .navigate(R.id.action_FirstFragment_to_SecondFragment);
                        } else {
                            try {
                                throw task.getException();

                            } catch (FirebaseAuthWeakPasswordException e) {
                                pwdInput.setError("Weak password.");
                                Toast.makeText(getActivity().getApplicationContext(), "Error: Weak password.", Toast.LENGTH_LONG).show();
                            } catch(FirebaseAuthUserCollisionException e) {
                                emailInput.setError("User already exists under this email.");
                                Toast.makeText(getActivity().getApplicationContext(), "Error: User already exists.", Toast.LENGTH_LONG).show();
                            }
                            catch (Exception e) {
                                Toast.makeText(getActivity().getApplicationContext(), "Error: Could not create account.", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });

            } else {
                emailInput.setError("Must be a CSULB student email.");
                Toast.makeText(getActivity().getApplicationContext(), "Error: Must use a CSULB student email!", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Error: Incorrect email format.", Toast.LENGTH_LONG).show();
        }

        return success[0];
    }

    private void sendUserToNextFragment() {

        NavHostFragment.findNavController(RegisterFragment.this).navigate(R.id.action_FirstFragment_to_SecondFragment);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}