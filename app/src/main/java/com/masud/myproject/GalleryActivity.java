package com.masud.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.masud.myproject.Adapters.UploadImageAdapter;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {
    private RecyclerView ramadanFest,prayerFest;
    private DatabaseReference databaseReference;
    private ArrayList<UploadImageData> list,list1;
    private UploadImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        prayerFest = findViewById(R.id.prayerFestRV);

        ramadanFest = findViewById(R.id.ramadanFestRV);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("GalleryImages");

        databaseReference.child("Ramadan Fest").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list = new ArrayList<>();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                   UploadImageData data = dataSnapshot.getValue(UploadImageData.class);
                   list.add(data);
                }
                adapter = new UploadImageAdapter(list,GalleryActivity.this);
                ramadanFest.setLayoutManager(new GridLayoutManager(GalleryActivity.this,3));
                ramadanFest.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.child("Prayer Fest").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1 = new ArrayList<>();

                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                 UploadImageData data =   dataSnapshot.getValue(UploadImageData.class);
                 list1.add(data);
                }
                adapter = new UploadImageAdapter(list1,GalleryActivity.this);
                prayerFest.setLayoutManager(new GridLayoutManager(GalleryActivity.this,3));
                prayerFest.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}