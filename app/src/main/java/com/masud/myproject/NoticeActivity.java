package com.masud.myproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NoticeActivity extends AppCompatActivity {
    private MaterialCardView galleryImagePicking;
    private ImageView imagePost;
    private AppCompatButton submitBtn;
    private TextInputLayout textInputLayout;

    private DatabaseReference database;

    String postTitle;

    Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        database = FirebaseDatabase.getInstance().getReference().child("Notice");

        galleryImagePicking = findViewById(R.id.noticeGallery);
        imagePost = findViewById(R.id.imagePost);
        submitBtn = findViewById(R.id.submitBtn);
        textInputLayout = findViewById(R.id.editTextLayout);
        galleryImagePicking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imagePostIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(imagePostIntent, 1);
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postTitle = textInputLayout.getEditText().getText().toString().trim();
                if (postTitle.isEmpty()) {
                    textInputLayout.getEditText().setError("Empty field");
                    textInputLayout.getEditText().requestFocus();
                } else if (imageBitmap == null) {
                    gotoDatabase();
                }
            }

            private void gotoDatabase() {
                String uniqueId = database.push().getKey();

                Calendar calForDate = Calendar.getInstance();
                SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyy ");
                currentDate.format(calForDate.getTime());

                NoticeData noticeData = new NoticeData(postTitle,"04 Dec.2022","7:03 pm",uniqueId,"");
                database.child(uniqueId).setValue(noticeData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(NoticeActivity.this, "Database created", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) ;
        {
            Uri imageUri = data.getData();
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            imagePost.setImageURI(imageUri);

        }
    }
}