package com.example.admincollageapp.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.admincollageapp.Login.UserLoginActivity;
import com.example.admincollageapp.LoginActivity;
import com.example.admincollageapp.MainActivity2;
import com.example.admincollageapp.Model.UserModel;
import com.example.admincollageapp.databinding.ActivityUserRegisterBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class UserRegisterActivity extends AppCompatActivity {

   ActivityUserRegisterBinding binding;
   private FirebaseAuth auth;
   private FirebaseDatabase database;
   private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        auth= FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        // Check if user is signed in (non-null) and update UI accordingly.
        if(auth.getCurrentUser()!=null){
            startActivity(new Intent(UserRegisterActivity.this,MainActivity2.class));
            finish();
        }
        // initialising progress dialogue
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Creating new User.......");

        binding.loginHereTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserRegisterActivity.this, UserLoginActivity.class));
                finish();
            }
        });


        binding.registerLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.registerUserName.getText().toString().trim();
                String email= binding.registerUserEmail.getText().toString().trim();
                String pass = binding.registerUserPassword.getText().toString().trim();

                String messege= checkValidation(name,email,pass);
                if(!messege.isEmpty()){
                    Toast.makeText(UserRegisterActivity.this, messege, Toast.LENGTH_SHORT).show();
                    return;
                }
                progressDialog.show();
                auth.createUserWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        String key=  database.getReference() .child("User").push().getKey();

                        UserModel model =new UserModel(name,email,pass,key);

                        database.getReference().child("User").child(key)
                                .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                progressDialog.dismiss();
                                Toast.makeText(UserRegisterActivity.this, "Successfully store in database", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(UserRegisterActivity.this, MainActivity2.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(UserRegisterActivity.this, "error in uploading in database", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(UserRegisterActivity.this, "Error in registering user", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    private String checkValidation(String name, String email, String pass) {
        if(name.length()<=4){
            binding.registerUserName.setError("Invalid data");
            return "Please enter valid user name";
        }else if(email.length()<=10||!email.substring(email.length()-10,email.length()).equals("@gmail.com")){
           binding.registerUserEmail.setError("Invalid data");
            return "Please enter valid email";
        }else if(pass.length()<8){
            binding.registerUserPassword.setError("Invalid data");
            return "Enter strong pass (min 8 character) ";
        }

        return "";
    }


}