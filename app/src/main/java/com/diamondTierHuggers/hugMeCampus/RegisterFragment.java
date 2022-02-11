package com.diamondTierHuggers.hugMeCampus;

import static com.diamondTierHuggers.hugMeCampus.MainActivity.myRef;

import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.diamondTierHuggers.hugMeCampus.databinding.FragmentRegisterBinding;
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

    private FragmentRegisterBinding binding;
    private FirebaseAuth auth;
    EditText emailInput;
    EditText pwdInput;
    Button sendDBButton;
    TextView login;
    Integer gender;

    private View view;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ){

        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        auth = FirebaseAuth.getInstance();
        auth.signOut();

        pwdInput = (EditText) view.findViewById(R.id.editTextTextPassword2);
        emailInput = (EditText) view.findViewById(R.id.editTextTextEmailAddress);
        sendDBButton = (Button) view.findViewById(R.id.btnSignup);
        login = (TextView) view.findViewById(R.id.textViewhaveacccount);


        sendDBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputValidation()) {

                        createUserAccount(emailInput.getText().toString(), pwdInput.getText().toString());
                        setBioValues(binding.textViewFirstName.getText().toString(), binding.textviewLastName.getText().toString(), gender);

                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "All values required", Toast.LENGTH_SHORT).show();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendUserToNextFragment();
            }
        });
    }


    private void setBioValues(String firstName, String lastName, Integer Gender){

        myRef.child("users").child(auth.getUid()).child("last_name").setValue(lastName);
        myRef.child("users").child(auth.getUid()).child("first_name").setValue(firstName);
        myRef.child("users").child(auth.getUid()).child("gender").setValue(Gender);
        myRef.child("users").child(auth.getUid()).child("hug_count").setValue(0);
    }

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
                        NavHostFragment.findNavController(RegisterFragment.this)
                                .navigate(R.id.SecondFragment);
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

    public boolean inputValidation(){
        if (binding.textViewFirstName.getText().toString().isEmpty()|| binding.textviewLastName.getText().toString().isEmpty()||
                binding.editTextTextEmailAddress.getText().toString().isEmpty()|| binding.editTextTextPassword2.getText().toString().isEmpty()
                && (!binding.radioFemale.isSelected() && !binding.radioMale.isSelected())){
                return false;
        }
        return true;
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
                                //
                            if (binding.radioFemale.isSelected()){
                                gender = 1;
                            }else{
                                gender = 0;
                            }

                            setBioValues(binding.textViewFirstName.getText().toString(), binding.textviewLastName.getText().toString(),gender);
                            //
                            NavHostFragment.findNavController(RegisterFragment.this)
                                    .navigate(R.id.SecondFragment);
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

        NavHostFragment.findNavController(RegisterFragment.this).navigate(R.id.SecondFragment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}