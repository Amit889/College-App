package com.example.admincollageapp.ebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.admincollageapp.Adapter.EbookAdapter;
import com.example.admincollageapp.Model.NoticeModel;
import com.example.admincollageapp.R;
import com.example.admincollageapp.databinding.ActivityEbookBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EbookActivity extends AppCompatActivity {

    ActivityEbookBinding binding;
    EbookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  ActivityEbookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<NoticeModel> list = new ArrayList<>();

        adapter = new EbookAdapter(list,this);
        binding.ebookRecyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.ebookRecyclerView.setLayoutManager(layoutManager);


        FirebaseDatabase.getInstance().getReference().child("Pdf").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    NoticeModel  model = snapshot1.getValue(NoticeModel.class);
                    list.add(model);
                }
                adapter.notifyDataSetChanged();
                binding.shimmerViewContainer.stopShimmer();
                binding.simmerLinearLayout.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EbookActivity.this, "Error in loading pdf from database", Toast.LENGTH_SHORT).show();
            }
        });
    }
}