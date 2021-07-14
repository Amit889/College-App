package com.example.admincollageapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admincollageapp.FullImageActivity;
import com.example.admincollageapp.Model.ImageModel;

import com.example.admincollageapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.viewHolder> {

    ArrayList<ImageModel> list;
    Context context;

    public ImageAdapter(ArrayList<ImageModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ImageAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_layout,parent,false);
        return new ImageAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.viewHolder holder, int position) {

        ImageModel model = list.get(position);
        Picasso.get().load(model.getDownloadUrl()).placeholder(R.drawable.loding).fit().into(holder.image);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, FullImageActivity.class);
                intent.putExtra("image",model.getDownloadUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class viewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        public viewHolder(@NonNull  View itemView) {
            super(itemView);
           image = itemView.findViewById(R.id.imageLayoutImageView);
        }
    }
}
