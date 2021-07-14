package com.example.admincollageapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.admincollageapp.Model.NoticeModel;
import com.example.admincollageapp.R;
import com.example.admincollageapp.ebook.PdfViewerActivity;

import java.util.ArrayList;

public class EbookAdapter extends  RecyclerView.Adapter<EbookAdapter.viewHolder>{

    ArrayList<NoticeModel> list;
    Context context;

    public EbookAdapter(ArrayList<NoticeModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ebook_layout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EbookAdapter.viewHolder holder, int position) {

        NoticeModel mymodel= list.get(position);

        holder.title.setText(mymodel.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, PdfViewerActivity.class);
                intent.putExtra("pdfUrl",mymodel.getDownloadUrl());
                context.startActivity(intent);

            }
        });

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(mymodel.getDownloadUrl()));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class viewHolder extends RecyclerView.ViewHolder{
        TextView title ;
        ImageView image;
        public viewHolder(@NonNull  View itemView) {
            super(itemView);
           title = itemView.findViewById(R.id.ebookTextView);
           image = itemView.findViewById(R.id.ebookDownload);
        }
    }
}
