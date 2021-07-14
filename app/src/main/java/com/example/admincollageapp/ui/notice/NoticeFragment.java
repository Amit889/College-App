package com.example.admincollageapp.ui.notice;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.admincollageapp.Adapter.UserNoticeAdapter;
import com.example.admincollageapp.Model.NoticeModel;
import com.example.admincollageapp.R;
import com.example.admincollageapp.databinding.FragmentHomeBinding;
import com.example.admincollageapp.databinding.FragmentNoticeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class NoticeFragment extends Fragment {

    FragmentNoticeBinding binding;
    private UserNoticeAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNoticeBinding.inflate(inflater,container,false);

        ArrayList<NoticeModel> list = new ArrayList<>();

        adapter = new UserNoticeAdapter(list,getContext());
        binding.noticeRecyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        binding.noticeRecyclerView.setLayoutManager(layoutManager);

        FirebaseDatabase.getInstance().getReference().child("Notice").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    NoticeModel model= snapshot1.getValue(NoticeModel.class);
                    //here we write 0 so that latest notice show at top
                    list.add(0,model);
                }
                adapter.notifyDataSetChanged();
                binding.userNoticeProgressbar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {
                Toast.makeText(getContext(), "error in loading data from database", Toast.LENGTH_SHORT).show();
            }
        });
        return binding.getRoot();
    }
}