package com.example.makechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
   FirebaseAuth firebaseAuth;
   RecyclerView recyclerView;
   UserAdapter adapter;
   FirebaseDatabase database;
   ArrayList<Users> usersArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        database=FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();

        DatabaseReference reference=database.getReference().child("user");
        usersArrayList=new ArrayList<>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Users users=dataSnapshot.getValue(Users.class);
                    usersArrayList.add(users);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        recyclerView=findViewById(R.id.recyclermain);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new UserAdapter(MainActivity.this,usersArrayList);
        recyclerView.setAdapter(adapter);




        if(firebaseAuth==null){
            startActivity(new Intent(MainActivity.this,login.class));
        }




    }
}