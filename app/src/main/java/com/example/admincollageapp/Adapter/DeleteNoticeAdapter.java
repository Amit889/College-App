package com.example.admincollageapp.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admincollageapp.Model.NoticeModel;
import com.example.admincollageapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DeleteNoticeAdapter extends RecyclerView.Adapter<DeleteNoticeAdapter.viewHolder>{

    ArrayList<NoticeModel> list;
    Context context;
    public DeleteNoticeAdapter(Context context,ArrayList<NoticeModel> list) {
        this.context=context;
        this.list=list;


    }

    @Override
    public viewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view=  LayoutInflater.from(context).inflate(R.layout.delete_notice_layout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  DeleteNoticeAdapter.viewHolder holder, int position) {

        NoticeModel model= list.get(position);

        holder.text.setText(model.getTitle());
        Picasso.get().load(model.getDownloadUrl()).placeholder(R.drawable.loding).fit().into(holder.image);


        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder= new AlertDialog.Builder(context);
                builder.setMessage("Are you sure want to delete this messege ?");
                builder.setCancelable(true);

                builder.setPositiveButton(
                        "Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase.getInstance().getReference().child("Notice").child(model.getKey())
                                        .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull  Task<Void> task) {
                                        Toast.makeText(context, model.getTitle()+" deleted Successfully!", Toast.LENGTH_SHORT).show();
                                        notifyItemRemoved(position);
                                    }
                                });
                            }
                        }
                );
                 builder.setNegativeButton(
                         "Cancel",
                         new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                             }
                         }
                 );

                AlertDialog dialog=null;
                 try {
                      dialog = builder.create();
                 }catch (Exception e){
                     e.printStackTrace();
                 }

               if(dialog !=null)  dialog.show();
               
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView  text;
        Button button;
        ImageView image;

        public viewHolder(@NonNull  View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.deleteNoticeLayouttext);
            button= itemView.findViewById(R.id.deleteNoticeLayoutButton);
            image= itemView.findViewById(R.id.deleteNoticeLayoutImage);

        }
    }
}
