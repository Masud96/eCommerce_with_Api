package com.masud.myproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class UploadImageActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 1;
    private Button uploadImageBtn;
    private MaterialCardView pickGalleryImage;
    private ImageView galleryImage;
    Bitmap imageBitmap;
    private Spinner imageCategory;
    private String category,downloadUrl;
    private StorageReference storageReference, myStorage;
    private UploadTask uploadTask;
    private DatabaseReference databaseReference;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Database");
        dialog.setMessage("Uploading...");


        storageReference = FirebaseStorage.getInstance().getReference().child("Gallery");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("GalleryImages");

        galleryImage = findViewById(R.id.galleryImageView);

        uploadImageBtn = findViewById(R.id.UploadImageBtn);
        pickGalleryImage = findViewById(R.id.pickGalleryImage);

        imageCategory = findViewById(R.id.imageCategory);
        String[] items = {"Select category","Ramadan Fest","Prayer Fest","Sports Event","Cultural Event","Get together","Others"};

        imageCategory.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,items));

        imageCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category = imageCategory.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        uploadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageBitmap == null){
                    Toast.makeText(UploadImageActivity.this, "Select any image to upload.", Toast.LENGTH_SHORT).show();
                }else if(category.equals("Select category")){
                    Toast.makeText(UploadImageActivity.this, "Please select any of the category.", Toast.LENGTH_SHORT).show();
                }else{
                    gotoStorage();
                }
            }
        });

        pickGalleryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickIntent, REQUEST_CODE);
            }
        });


    }

    private void gotoStorage(){
        dialog.show();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG,50,outputStream);
        byte[] finalImage = outputStream.toByteArray();

       myStorage = storageReference.child(finalImage+"."+"jpg"+System.currentTimeMillis());
        uploadTask = myStorage.putBytes(finalImage);

        uploadTask.addOnCompleteListener(UploadImageActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            myStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl = String.valueOf(uri);
                                    gotoDatabase();
                                }
                            });
                        }
                    });

                }
            }
        });





    }
    private void gotoDatabase(){
        String uniqueId = databaseReference.push().getKey();

        Calendar calForDate = Calendar.getInstance();
        calForDate.getTime();

        UploadImageData data = new UploadImageData(downloadUrl,uniqueId);

        databaseReference.child(category).child(uniqueId).setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                dialog.dismiss();
                Toast.makeText(UploadImageActivity.this, "Uploaded successfully!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK){
          Uri imageUri = data.getData();

            try {
                 imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);

                galleryImage.setImageBitmap(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }



        }

    }
}