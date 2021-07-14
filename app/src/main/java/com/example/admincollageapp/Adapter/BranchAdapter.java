package com.example.admincollageapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.admincollageapp.Model.BranchModel;
import com.example.admincollageapp.R;

import java.util.ArrayList;

public class BranchAdapter extends PagerAdapter {

    private ArrayList<BranchModel> list ;
    private Context context;

    public BranchAdapter(ArrayList<BranchModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.branch_item_layout,container,false);

        ImageView icon;
        TextView title, description;

        icon = view.findViewById(R.id.branchImage);
        title = view.findViewById(R.id.branchTitle);
        description = view.findViewById(R.id.branchdescription);

        icon.setImageResource(list.get(position).getImage());
        title.setText(list.get(position).getTitle());
        description.setText(list.get(position).getDescription());

        container.addView(view,0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);

    }
}
