package com.example.admincollageapp.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.admincollageapp.ForgotPassword.ForgotPasswordActivity;
import com.example.admincollageapp.MainActivity2;
import com.example.admincollageapp.R;
import com.example.admincollageapp.databinding.ActivityUserLoginBinding;
import com.example.admincollageapp.registration.UserRegisterActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class UserLoginActivity extends AppCompatActivity {

    ActivityUserLoginBinding binding;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("logging........");

        if(auth.getCurrentUser()!=null){
            startActivity(new Intent(UserLoginActivity.this, MainActivity2.class));
            finish();
        }

        binding.RegisterHereTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserLoginActivity.this, UserRegisterActivity.class));
            }
        });

        binding.userLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= binding.userLoginEmail.getText().toString().trim();
                String pass = binding.userLoginPassword.getText().toString().trim();

                String messege = checkValidation(email,pass);
                if(!messege.isEmpty()){
                    Toast.makeText(UserLoginActivity.this, messege, Toast.LENGTH_SHORT).show();
                    return;
                }
                 progressDialog.show();
                auth.signInWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        progressDialog.hide();
                        startActivity(new Intent(UserLoginActivity.this,MainActivity2.class));
                        Toast.makeText(UserLoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.hide();
                        Toast.makeText(UserLoginActivity.this, "Error in login", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        binding.loginForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserLoginActivity.this,ForgotPasswordActivity.class));
            }
        });
    }

    private String checkValidation(String email, String pass) {
        if(email.length()<=10||!email.substring(email.length()-10,email.length()).equals("@gmail.com")){
            binding.userLoginEmail.setError("Invalid data");
            return "Please enter valid email";
        }else if(pass.length()<8){
            binding.userLoginPassword.setError("Invalid data");
            return "Enter strong pass (min 8 character) ";
        }

        return "";
    }
}