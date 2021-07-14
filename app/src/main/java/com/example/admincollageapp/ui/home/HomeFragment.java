package com.example.admincollageapp.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.admincollageapp.R;
import com.example.admincollageapp.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {


     FragmentHomeBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false);

        ArrayList<SlideModel> imagelist = new ArrayList<>();

        imagelist.add(new SlideModel(R.drawable.sliderimage1,ScaleTypes.FIT));
        imagelist.add(new SlideModel(R.drawable.sliderimage2,ScaleTypes.FIT));
        imagelist.add(new SlideModel(R.drawable.sliderimage3, ScaleTypes.FIT));
        imagelist.add(new SlideModel(R.drawable.sliderimage4, ScaleTypes.FIT));

        binding.imageSlider.setImageList(imagelist);

        binding.locationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap();
            }
        });


        return binding.getRoot();

    }

    public void openMap(){

        Uri gmmIntentUri = Uri.parse("google.navigation:q="+"Madan Mohan Malaviya University Of Technology , Gorakhpur Utter Pradesh , India" + "&mode=b");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);

    }
}