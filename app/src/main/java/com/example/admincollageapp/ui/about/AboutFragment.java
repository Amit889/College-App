package com.example.admincollageapp.ui.about;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admincollageapp.Adapter.BranchAdapter;
import com.example.admincollageapp.Model.BranchModel;
import com.example.admincollageapp.R;
import com.example.admincollageapp.databinding.FragmentAboutBinding;

import java.util.ArrayList;


public class AboutFragment extends Fragment {


    FragmentAboutBinding binding;
    private BranchAdapter adapter;
    private ArrayList<BranchModel> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = FragmentAboutBinding.inflate(inflater,container,false);

        list = new ArrayList<>();

        list.add(new BranchModel(R.drawable.ic_baseline_computer,"Computer Science","This department was established in 1984 and with time it has earned the recognition as one of the top Computer Science & Engg. programs in the UP. The department offers B.Tech. in Computer Science & Engineering. "));
        list.add(new BranchModel(R.drawable.ic_baseline_phonelink,"Information Technology","The Department of Information Technology and Computer Application (ITCA) was inaugurated on 24th August 2018 by Hon’ble Chancellor of Uttar Pradesh Shri Ram Naik "));
        list.add(new BranchModel(R.drawable.ic_baseline_electrical,"Electrical Engineering","The department of electrical Engineering was established in 1962. This department has over the years established its reputation as an excellent center for importing high quality technical education to under graduates "));
        list.add(new BranchModel(R.drawable.ic_baseline_settings,"Mechinical Engineering","The department of Mechanical engineering came into existence in the year 1962, the year of inception of the college."));
        list.add(new BranchModel(R.drawable.ic_baseline_miscellaneous,"Electrical and Electronics","Electronics & Communication Engineering Department at MMMUT, Gorakhpur was established in the year 1973 with a vision to build proficient engineers capable of including values to professional excellence"));
        list.add(new BranchModel(R.drawable.ic_baseline_apartment,"Civil Engineering","The Civil Engineering Department established in 1962, in Madan Mohan Malaviya University of Technology, Gorakhpur, is one of the oldest departments of the University, working since its inception"));

        adapter= new BranchAdapter(list,getContext());
        binding.aboutViewPager.setAdapter(adapter);
        
        return binding.getRoot();
    }
}