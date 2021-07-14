package com.example.admincollageapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.admincollageapp.Model.TeacherModel;
import com.example.admincollageapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserFacultyAdapter extends  RecyclerView.Adapter<UserFacultyAdapter.viewHolder>{

    ArrayList<TeacherModel> list;
    Context context;

    public UserFacultyAdapter(ArrayList<TeacherModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_faculty_item_layout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserFacultyAdapter.viewHolder holder, int position) {

        TeacherModel mymodel= list.get(position);

        holder.name.setText(mymodel.getName());
        holder.post.setText(mymodel.getPost());
        holder.email.setText(mymodel.getEmail());
        holder.category.setText(mymodel.getBranch());
        Picasso.get().load(mymodel.getDownloadUrl()).placeholder(R.drawable.loding).fit().into(holder.image);




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

        public viewHolder(@NonNull  View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.userfacultyLayoutName);
            post= itemView.findViewById(R.id.userfacultyLayoutPost);
            category= itemView.findViewById(R.id.userfacultyLayoutCategory);
            email= itemView.findViewById(R.id.userfacultyLayoutEmail);
            image = itemView.findViewById(R.id.userfacultyLayoutProfileimage);

        }
    }
}

