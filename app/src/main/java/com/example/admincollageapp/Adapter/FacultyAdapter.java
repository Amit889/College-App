package com.example.admincollageapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admincollageapp.Faculty.UpdateTeacher;
import com.example.admincollageapp.Model.TeacherModel;
import com.example.admincollageapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FacultyAdapter extends  RecyclerView.Adapter<FacultyAdapter.viewHolder>{

    ArrayList<TeacherModel> list;
    Context context;

    public FacultyAdapter(ArrayList<TeacherModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.faculty_item_layout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacultyAdapter.viewHolder holder, int position) {

        TeacherModel mymodel= list.get(position);

        holder.name.setText(mymodel.getName());
        holder.post.setText(mymodel.getPost());
        holder.email.setText(mymodel.getEmail());
        holder.category.setText(mymodel.getBranch());
        Picasso.get().load(mymodel.getDownloadUrl()).placeholder(R.drawable.loding).into(holder.image);

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, UpdateTeacher.class);
                intent.putExtra("name",mymodel.getName());
                intent.putExtra("post",mymodel.getPost());
                intent.putExtra("email",mymodel.getEmail());
                intent.putExtra("downloadUrl",mymodel.getDownloadUrl());
                intent.putExtra("uniqueKey",mymodel.getUniqueKey());
                 context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class viewHolder extends RecyclerView.ViewHolder{
           TextView name ;
           TextView post;
           TextView category;
           TextView email;
           ImageView image;
           Button button;
        public viewHolder(@NonNull  View itemView) {
            super(itemView);
          name= itemView.findViewById(R.id.facultyLayoutName);
          post= itemView.findViewById(R.id.facultyLayoutPost);
          category= itemView.findViewById(R.id.facultyLayoutCategory);
          email= itemView.findViewById(R.id.facultyLayoutEmail);
          image = itemView.findViewById(R.id.facultyLayoutProfileimage);
          button = itemView.findViewById(R.id.facultyLayoutButton);
        }
    }
}
