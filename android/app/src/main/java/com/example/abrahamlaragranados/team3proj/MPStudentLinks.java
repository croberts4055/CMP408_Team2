package com.example.abrahamlaragranados.team3proj;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MPStudentLinks extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserLinkAdapter adapter;

    private FirebaseDatabase database;

    private ArrayList<UserLink> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpstudent_links);

        database = FirebaseDatabase.getInstance();

        list = new ArrayList<UserLink>();

        Intent intent = getIntent();

        database.getReference().child("UserLinks").child(intent.getExtras().getString("projectName")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot linkSnapshot: dataSnapshot.getChildren()) {

                    String user = null;
                    String link = null;

                    for (DataSnapshot snapshot: linkSnapshot.getChildren()) {
                        if (link == null) link = snapshot.getValue().toString();
                        else  user = snapshot.getValue().toString();
                    }

                    list.add(new UserLink(user, link));
                }
                addRecycler();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void addRecycler() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new UserLinkAdapter(this, list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
