package com.masud.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.card.MaterialCardView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private MaterialCardView imagePicking, deleteNoticeCardView,uploadImage,showGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showGallery = findViewById(R.id.showGalleryImage);
        showGallery.setOnClickListener(this);


        imagePicking = findViewById(R.id.noticeGallery);
        imagePicking.setOnClickListener(this);

        uploadImage = findViewById(R.id.uploadImageCard);

        uploadImage.setOnClickListener(this);


        deleteNoticeCardView = findViewById(R.id.deleteNoticeId);
        deleteNoticeCardView.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.noticeGallery:
                Intent noticeGalleryIntent = new Intent(MainActivity.this,NoticeActivity.class);
                startActivity(noticeGalleryIntent);
                break;
            case R.id.deleteNoticeId:
                Intent deleteNoticeIntent = new Intent(MainActivity.this,DeleteNoticeActivity.class);
                startActivity(deleteNoticeIntent);
                break;
            case R.id.uploadImageCard:
                Intent uploadImageIntent = new Intent(MainActivity.this,UploadImageActivity.class);
                startActivity(uploadImageIntent);
                break;
                case R.id.showGalleryImage:
                startActivity(new Intent(MainActivity.this,GalleryActivity.class));
                break;



        }
    }
}