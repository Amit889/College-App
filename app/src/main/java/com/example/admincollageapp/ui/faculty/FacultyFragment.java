package com.example.admincollageapp.ui.faculty;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.example.admincollageapp.Adapter.FacultyAdapter;
import com.example.admincollageapp.Adapter.UserFacultyAdapter;
import com.example.admincollageapp.Model.TeacherModel;
import com.example.admincollageapp.R;
import com.example.admincollageapp.databinding.FragmentFacultyBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FacultyFragment extends Fragment {

    FragmentFacultyBinding binding;
    private UserFacultyAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFacultyBinding.inflate(inflater,container,false);

        ArrayList<TeacherModel> list = new ArrayList<>();

        adapter=new UserFacultyAdapter(list,getContext());
        binding.userFacultyRecyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        binding.userFacultyRecyclerView.setLayoutManager(layoutManager);

        FirebaseDatabase.getInstance().getReference().child("Teacher").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    TeacherModel model = snapshot1.getValue(TeacherModel.class);
                    list.add(model);
                }
                adapter.notifyDataSetChanged();
                binding.facultyProgressbar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return binding.getRoot();
    }
}