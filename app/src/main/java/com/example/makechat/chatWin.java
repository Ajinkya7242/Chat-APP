package com.example.makechat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

public class chatWin extends AppCompatActivity {
    String receiverImg,RecUid,RecName,SenderUid;
    ShapeableImageView profile;
    TextView txtrecName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_win);
        getSupportActionBar().hide();
        receiverImg=getIntent().getStringExtra("reciverImg");
        RecUid=getIntent().getStringExtra("uid");
        RecName=getIntent().getStringExtra("name");

        profile=findViewById(R.id.imgUserChat);
        txtrecName=findViewById(R.id.receiverName);

        Picasso.get().load(receiverImg).into(profile);
        txtrecName.setText(""+RecName);


    }
}