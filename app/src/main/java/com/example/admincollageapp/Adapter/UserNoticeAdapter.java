package com.example.admincollageapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admincollageapp.FullImageActivity;
import com.example.admincollageapp.Model.NoticeModel;
import com.example.admincollageapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserNoticeAdapter extends RecyclerView.Adapter<UserNoticeAdapter.viewHolder>{

    ArrayList<NoticeModel> list;
    Context context;

    public UserNoticeAdapter(ArrayList<NoticeModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public viewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_notice_layout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  UserNoticeAdapter.viewHolder holder, int position) {
        NoticeModel model = list.get(position);
        holder.time.setText("Time : "+model.getTime());
        holder.date.setText("Date : "+model.getDate());
        holder.title.setText(model.getTitle());
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

    public class viewHolder extends RecyclerView.ViewHolder{
          TextView date;
          TextView time;
          TextView title;
          ImageView image;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.userdateTextView);
            time = itemView.findViewById(R.id.usertimeTextView);
            title= itemView.findViewById(R.id.userNoticeLayouttext);
            image = itemView.findViewById(R.id.userNoticeLayoutImage);

        }
    }
}
