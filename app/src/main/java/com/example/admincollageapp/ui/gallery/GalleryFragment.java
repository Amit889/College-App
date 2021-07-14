package com.example.admincollageapp.ui.gallery;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.admincollageapp.Adapter.ImageAdapter;
import com.example.admincollageapp.Model.ImageModel;
import com.example.admincollageapp.Model.TeacherModel;
import com.example.admincollageapp.R;
import com.example.admincollageapp.databinding.FragmentGalleryBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    FragmentGalleryBinding binding;
    private ImageAdapter adapter1,adapter2,adapter3,adapter4;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGalleryBinding.inflate(inflater,container,false);

        ArrayList<ImageModel> convolist = new ArrayList<>();
        ArrayList<ImageModel> indlist = new ArrayList<>();
        ArrayList<ImageModel> anulist = new ArrayList<>();
        ArrayList<ImageModel> othlist = new ArrayList<>();

        /**
        set values to convocation
         */
        adapter1 = new ImageAdapter(convolist,getContext());
        binding.convocationRecyclerView.setAdapter(adapter1);

        GridLayoutManager layoutManager1 = new GridLayoutManager(getContext(),3);
        binding.convocationRecyclerView.setLayoutManager(layoutManager1);

        /**
         * set value to Independence day
         */

        adapter2 = new ImageAdapter(indlist,getContext());
        binding.independenceRecyclerView.setAdapter(adapter2);

        GridLayoutManager layoutManager2 = new GridLayoutManager(getContext(),3);
        binding.independenceRecyclerView.setLayoutManager(layoutManager2);
        /**
        set value to Annual function
         */
        adapter3 = new ImageAdapter(anulist,getContext());
        binding.annualRecyclerView.setAdapter(adapter3);

        GridLayoutManager layoutManager3 = new GridLayoutManager(getContext(),3);
        binding.annualRecyclerView.setLayoutManager(layoutManager3);
        /**
        set value to other
         */
        adapter4 = new ImageAdapter(othlist,getContext());
        binding.otherRecyclerView.setAdapter(adapter4);

        GridLayoutManager layoutManager4 = new GridLayoutManager(getContext(),3);
        binding.otherRecyclerView.setLayoutManager(layoutManager4);

        FirebaseDatabase.getInstance().getReference().child("Gallery").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                convolist.clear();
                indlist.clear();
                anulist.clear();
                othlist.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    ImageModel model = snapshot1.getValue(ImageModel.class);
                    String category = model.getCategory();
                    if(category==null) continue;
                    if(category.equals("Convocation")){
                        convolist.add(model);
                    }else if(category.equals("Independence Day")){
                        indlist.add(model);
                    }else if(category.equals("Annual Function")){
                        anulist.add(model);
                    }else{
                        othlist.add(model);
                    }
                }
                if(!convolist.isEmpty()){
                    binding.convocationTextView.setText("Convocation");
                }
                if(!indlist.isEmpty()){
                    binding.independenceTextView.setText("Independence Day");
                }
                if(!anulist.isEmpty()){
                    binding.annualTextView.setText("Annual Function");
                }
                if(!othlist.isEmpty()){
                   binding.otherTextView.setText("Other Event");
                }
                adapter1.notifyDataSetChanged();
                adapter2.notifyDataSetChanged();
                adapter3.notifyDataSetChanged();
                adapter4.notifyDataSetChanged();

                binding.galleryProgressbar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error in loading image from database", Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }
}