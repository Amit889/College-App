package com.example.admincollageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.admincollageapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    private String email, pass;

    private SharedPreferences sharedPreferences;
    private  SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        sharedPreferences= this.getSharedPreferences("login",MODE_PRIVATE);
        editor= sharedPreferences.edit();
        /**
         * checking weather user is loggedin or not
         * Note :  here false value is default argument so when islogin is not avilable than it return false
         */
        if(sharedPreferences.getString("islogin","false").equals("true")){
            openDash();
        }


        binding.loginButtonTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });
    }

    private void validateData() {
        email= binding.loginEmailTextView.getText().toString().trim();
        pass= binding.loginPasswordTextView.getText().toString().trim();

        if(!email.equals("myadmin@mmmut.ac.in")){
            binding.loginEmailTextView.setError("invalid email");
            binding.loginEmailTextView.requestFocus();
        } else if(!pass.equals("12345678")){
            binding.loginPasswordTextView.setError("invalid password");
            binding.loginPasswordTextView.requestFocus();
        }else{
            editor.putString("islogin","true");
            editor.commit();
            openDash();
        }
    }

    private void openDash() {

        startActivity(new Intent(LoginActivity.this,MainActivity.class));
        // here we use finish method so that on pressing back button from mainactivity we do not come on this activity
        finish();
    }
}