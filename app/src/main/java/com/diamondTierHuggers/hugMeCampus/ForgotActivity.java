package com.diamondTierHuggers.hugMeCampus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.diamondTierHuggers.hugMeCampus.databinding.FragmentSecondBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotActivity extends AppCompatActivity {

    private FragmentSecondBinding binding;
    EditText etEmail;
    Button continueBtn;
    private FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        viewInitializations();

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performCodeVerify();
            }
        });
    }

    void viewInitializations() {
        etEmail = findViewById(R.id.et_email);
        continueBtn = findViewById(R.id.bt_forget);
        // To show back button in actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // Checking if the input in form is valid
    boolean validateInput() {

        if (etEmail.getText().toString().equals("")) {
            etEmail.setError("Please Enter Email");
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

    public void performCodeVerify () {
        if (validateInput()) {

            // Input is valid, here send data to your server

            String email = etEmail.getText().toString();

            auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(ForgotActivity.this,"Email send to Register Email Address",Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(ForgotActivity.this, "Error: Could not send verification email.", Toast.LENGTH_LONG).show();
                    }
                }
            });


            Intent intent = new Intent(this, LoginFragment.class);
            startActivity(intent);
            // Here you can call you API
            // Check this tutorial to call server api through Google Volley Library https://handyopinion.com

        }
    }

}