package com.diamondTierHuggers.hugMeCampus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.diamondTierHuggers.hugMeCampus.databinding.FragmentSecondBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    TextView createNewAccount;
    EditText inputPassword, inputEmail;
    Button loginBtn;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;


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

            createNewAccount = view.findViewById(R.id.textViewCreateNewAccount);
            inputEmail = view.findViewById(R.id.editTextTextEmailAddress);
            inputPassword = view.findViewById(R.id.editTextTextPassword2);
            loginBtn = view.findViewById(R.id.btnLogin);
            mAuth = FirebaseAuth.getInstance();
            progressDialog = new ProgressDialog(getActivity());



        createNewAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_SecondFragment_to_FirstFragment);
                }
            });

            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    performLogin();
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
        String password = inputPassword.getText().toString();

        if (!email.toLowerCase().endsWith("@student.csulb.edu")){
            inputEmail.setError("Enter correct email");
        }else if(password.isEmpty()|| password.length()<6){
            inputPassword.setError("Enter correct password");
        }else{
            progressDialog.setMessage("Please wait to Login..");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        progressDialog.dismiss();
                        Toast.makeText(getActivity().getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                        NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_SecondFragment_to_profileFragment4);

                    }else
                    {
                        Toast.makeText(getActivity().getApplicationContext(),""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}