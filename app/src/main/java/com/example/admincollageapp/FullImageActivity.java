package com.example.admincollageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.admincollageapp.databinding.ActivityFullImageBinding;
import com.squareup.picasso.Picasso;

public class FullImageActivity extends AppCompatActivity {

    ActivityFullImageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  ActivityFullImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String imageurl = getIntent().getStringExtra("image");

        Picasso.get().load(imageurl).placeholder(R.drawable.loding).into(binding.fullImageImageView);
    }
}