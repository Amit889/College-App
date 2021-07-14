 package com.example.admincollageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.admincollageapp.Faculty.UpdateFaculty;
import com.example.admincollageapp.Login.EntryActivity;
import com.example.admincollageapp.databinding.ActivityMainBinding;

 public class MainActivity extends AppCompatActivity {
      ActivityMainBinding binding;
     private SharedPreferences sharedPreferences;
     private  SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences= this.getSharedPreferences("login",MODE_PRIVATE);
        editor= sharedPreferences.edit();
        /**
         * checking weather user  is by mistake (due to some bug) loggedin or not if yes than send it to login
         * Activity
         * Note :  here false value is default argument so when islogin is not avilable than it return false
         */
        if(sharedPreferences.getString("islogin","false").equals("false")){
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
        }

        binding.cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Upload_Notice_Activity.class);
                startActivity(intent);
            }
        });

        binding.cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Upload_Image_Activity.class);
                startActivity(intent);
            }
        });
        binding.cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Upload_Pdf_Activity.class);
                startActivity(intent);
            }
        });
        binding.cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, UpdateFaculty.class);
                startActivity(intent);
            }
        });
        binding.cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, DeleteNoticeActivity.class);
                startActivity(intent);
            }
        });
        binding.cardView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("islogin","false");
                editor.commit();
                Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

     @Override
     public void onBackPressed() {
         startActivity(new Intent(MainActivity.this, EntryActivity.class));
         finish();
     }
 }