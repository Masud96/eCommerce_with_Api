package com.masud.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.masud.myproject.Adapters.DeleteNoticeAdapters;

import java.util.ArrayList;

public class DeleteNoticeActivity extends AppCompatActivity {
    private RecyclerView deleteRV;
    private DeleteNoticeAdapters adapters;
    private ArrayList<NoticeData> dataArrayList;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_notice);
        deleteRV = findViewById(R.id.deleteNoticeRV);

        reference = FirebaseDatabase.getInstance().getReference().child("Notice");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                dataArrayList = new ArrayList<>();
               for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                 NoticeData myNoticeData =  dataSnapshot.getValue(NoticeData.class);
                 dataArrayList.add(0,myNoticeData);
               }
               adapters = new DeleteNoticeAdapters(dataArrayList,DeleteNoticeActivity.this);
               deleteRV.setLayoutManager(new LinearLayoutManager(DeleteNoticeActivity.this));
               deleteRV.setHasFixedSize(true);
               deleteRV.setAdapter(adapters);





            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}