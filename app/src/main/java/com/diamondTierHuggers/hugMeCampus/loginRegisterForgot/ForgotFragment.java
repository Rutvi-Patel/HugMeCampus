package com.diamondTierHuggers.hugMeCampus.loginRegisterForgot;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.diamondTierHuggers.hugMeCampus.R;
import com.diamondTierHuggers.hugMeCampus.databinding.FragmentForgotBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotFragment extends Fragment {

    private FragmentForgotBinding binding;
    EditText etEmail;
    Button continueBtn, LoginBtn;
    private FirebaseAuth auth;


    
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentForgotBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);


        etEmail = (EditText) view.findViewById(R.id.et_email);
        continueBtn = (Button) view.findViewById(R.id.bt_forget);
        LoginBtn = (Button) view.findViewById(R.id.bt_signup);
        auth = FirebaseAuth.getInstance();
        // To show back button in actionbar
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performCodeVerify();
            }
        });
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(ForgotFragment.this).navigate(R.id.action_forgotFragment_to_loginFragment);

            }
        });
    }


    // Checking if the input in form is valid
    boolean validateInput() {
        if (etEmail.getText().toString().isEmpty()){
            etEmail.setError("Enter email");
            return false;
        }
        // checking the proper email format
        if (!isEmailValid(etEmail.getText().toString())) {
            etEmail.setError("Please Enter Valid Email");
            return false;
        }
        return true;
    }

    boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Hook Click Event

    private void resetPassword(final String email) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity().getApplicationContext(), "Reset email instructions sent to " + email, Toast.LENGTH_LONG).show();
                            NavHostFragment.findNavController(ForgotFragment.this).navigate(R.id.action_forgotFragment_to_loginFragment);
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), email + " does not exist", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void performCodeVerify () {
        if (validateInput()) {

            // Input is valid, here send data to your server

            String email = etEmail.getText().toString();
            resetPassword(email);
            // Here you can call you API
            // Check this tutorial to call server api through Google Volley Library https://handyopinion.com

        }
    }

}