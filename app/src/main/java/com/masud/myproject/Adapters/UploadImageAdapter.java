package com.masud.myproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.masud.myproject.R;
import com.masud.myproject.UploadImageData;

import java.util.ArrayList;

public class UploadImageAdapter extends RecyclerView.Adapter<UploadImageAdapter.UploadImageViewHolder> {
    private ArrayList<UploadImageData> list;
    private Context context;

    public UploadImageAdapter(ArrayList<UploadImageData> list, Context context){

        this.list = list;
        this.context = context;
    }



    @NonNull
    @Override
    public UploadImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_layout_gallery,parent,false);
        return new UploadImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UploadImageViewHolder holder, int position) {
        UploadImageData data = list.get(position);
        try {
            Glide.with(context).load(data.getImageUrl()).into(holder.galleryImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class UploadImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView galleryImageView;

        public UploadImageViewHolder(@NonNull View itemView) {
            super(itemView);

            galleryImageView = itemView.findViewById(R.id.myImage);
        }
    }
}
