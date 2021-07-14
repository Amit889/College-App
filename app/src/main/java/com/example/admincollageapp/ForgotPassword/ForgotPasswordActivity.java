package com.example.admincollageapp.ForgotPassword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.admincollageapp.Login.UserLoginActivity;
import com.example.admincollageapp.R;
import com.example.admincollageapp.databinding.ActivityForgotPasswordBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    ActivityForgotPasswordBinding binding;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Wait....");
        auth = FirebaseAuth.getInstance();

        binding.backtologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgotPasswordActivity.this, UserLoginActivity.class));
                finish();
            }
        });
        binding.forgotLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= binding.forgotEmail.getText().toString().trim();

                String messege = checkValidity(email);
                if(!messege.isEmpty()){

                    Toast.makeText(ForgotPasswordActivity.this, messege, Toast.LENGTH_SHORT).show();
                    return;
                }
                 progressDialog.show();
                auth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        binding.forgotEmail.setText("");
                        progressDialog.dismiss();
                        Toast.makeText(ForgotPasswordActivity.this, "Check your email", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(ForgotPasswordActivity.this, "Error please check email", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private String checkValidity(String email) {
        if(email.length()<=10||!email.substring(email.length()-10,email.length()).equals("@gmail.com")){
            binding.forgotEmail.setError("Invalid data");
            return "Please enter valid email";
        }

        return "";
    }
}