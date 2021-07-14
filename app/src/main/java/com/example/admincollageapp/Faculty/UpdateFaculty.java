package com.example.admincollageapp.Faculty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.admincollageapp.Adapter.FacultyAdapter;
import com.example.admincollageapp.Model.TeacherModel;
import com.example.admincollageapp.R;
import com.example.admincollageapp.databinding.ActivityUpdateFacultyBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UpdateFaculty extends AppCompatActivity {

    ActivityUpdateFacultyBinding binding;
    private  FacultyAdapter adapter;
    private FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateFacultyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //Initialising FirebaseDataBase
        database= FirebaseDatabase.getInstance();
        binding.updateFacultyFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UpdateFaculty.this,AddTeacher.class);
                startActivity(intent);
            }
        });

        ArrayList<TeacherModel> list = new ArrayList();

        adapter=new FacultyAdapter(list,this);
        binding.updateFacultyRecyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.updateFacultyRecyclerView.setLayoutManager(layoutManager);




        database.getReference().child("Teacher").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    TeacherModel model = snapshot1.getValue(TeacherModel.class);
                    list.add(model);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();
    }
}