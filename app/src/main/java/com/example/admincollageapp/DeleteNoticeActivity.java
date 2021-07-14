package com.example.admincollageapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.admincollageapp.Adapter.DeleteNoticeAdapter;
import com.example.admincollageapp.Model.NoticeModel;
import com.example.admincollageapp.databinding.ActivityDeleteNoticeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeleteNoticeActivity extends AppCompatActivity {

    ActivityDeleteNoticeBinding binding;
    private DeleteNoticeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDeleteNoticeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<NoticeModel>  list=new ArrayList<>();

        adapter= new DeleteNoticeAdapter(this,list);
        binding.deleteNoticeRecyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        binding.deleteNoticeRecyclerView.setLayoutManager(layoutManager);

        FirebaseDatabase.getInstance().getReference().child("Notice").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot snapshot1: snapshot.getChildren()){
                    NoticeModel model = snapshot1.getValue(NoticeModel.class);
                    list.add(model);
                }
                adapter.notifyDataSetChanged();

                binding.deleteNoticeProgressbar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {
                Toast.makeText(DeleteNoticeActivity.this,"Error in Loading data from database",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }



}