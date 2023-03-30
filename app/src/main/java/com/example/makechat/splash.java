package com.example.makechat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class splash extends AppCompatActivity {
    ImageView logo;
    TextView name,own1,own2;
    Animation topAnim,botAnim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
       getSupportActionBar().hide();
       logo=findViewById(R.id.imgLogo);
       name=findViewById(R.id.txt1);
       own1=findViewById(R.id.textView2);
       own2=findViewById(R.id.textView3);

       topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
       botAnim=AnimationUtils.loadAnimation(this,R.anim.bott_animation);
       logo.setAnimation(topAnim);
       name.setAnimation(botAnim);
       own1.setAnimation(botAnim);
       own2.setAnimation(botAnim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(splash.this,MainActivity.class);
                 startActivity(intent);
                 finish();
            }
        },4000);

    }
}