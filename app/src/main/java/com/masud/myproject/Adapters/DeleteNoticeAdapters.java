package com.masud.myproject.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.masud.myproject.NoticeData;
import com.masud.myproject.R;

import java.util.ArrayList;

public class DeleteNoticeAdapters extends RecyclerView.Adapter<DeleteNoticeAdapters.NoticeViewHolder> {
    private ArrayList<NoticeData> list;
    private Context context;

    public DeleteNoticeAdapters(ArrayList<NoticeData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(context).inflate(R.layout.notice_sample_layout,parent,false);
        return new NoticeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewHolder holder, @SuppressLint("RecyclerView") int position) {
        NoticeData data = list.get(position);

        holder.dateText.setText(data.getPostDate());
        holder.timeText.setText(data.getPostTime());
        holder.postText.setText(data.getPostTitle());

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("Notice").child(data.getPostKey()).
                        removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(context, "Deleted successfully!", Toast.LENGTH_SHORT).show();
                            }
                        });
                notifyItemRemoved(position);
            }

        });

        Glide.with(context).load(data.getPostImage()).placeholder(R.drawable.ic_avatar).into(holder.postImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NoticeViewHolder extends RecyclerView.ViewHolder {

        private ImageView profileImage, postImage;
        private TextView nameText, dateText, timeText, postText;
        private AppCompatButton deleteBtn;

        public NoticeViewHolder(@NonNull View itemView) {
            super(itemView);

            profileImage = itemView.findViewById(R.id.circleImageProfile);
            postImage = itemView.findViewById(R.id.postImage);

            nameText = itemView.findViewById(R.id.nameTxt);
            dateText = itemView.findViewById(R.id.dateTxt);
            timeText = itemView.findViewById(R.id.timeTxt);
            postText = itemView.findViewById(R.id.postTxt);

            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }

}
