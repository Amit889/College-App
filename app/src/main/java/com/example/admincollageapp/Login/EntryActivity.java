package com.example.admincollageapp.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

import com.example.admincollageapp.LoginActivity;
import com.example.admincollageapp.MainActivity;
import com.example.admincollageapp.MainActivity2;
import com.example.admincollageapp.R;
import com.example.admincollageapp.databinding.ActivityEntryBinding;
import com.example.admincollageapp.registration.UserRegisterActivity;

public class EntryActivity extends AppCompatActivity {

    ActivityEntryBinding binding;
  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEntryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent= new Intent(EntryActivity.this, LoginActivity.class);
               startActivity(intent);
            }
        });
        binding.userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EntryActivity.this, UserRegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);

    }
}

