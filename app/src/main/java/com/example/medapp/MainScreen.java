package com.example.medapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainScreen extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        android.graphics.drawable.Drawable background = MainScreen.this.getResources().getDrawable(R.drawable.color);
        getWindow().setBackgroundDrawable(background);
        setContentView(R.layout.activity_main_screen);

        Button skip = findViewById(R.id.skip);
        ImageButton next = findViewById(R.id.next);
        ImageButton next1 = findViewById(R.id.imageButton3);
        ImageButton next2 = findViewById(R.id.imageButton5);

        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2000);
                    Intent intent = new Intent(getApplicationContext(),MainScreen2.class);
                    overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_left);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this, MainScreen2.class);
                overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_left);
                startActivity(intent);
                finish();
                thread.stop();
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this, LoginActivity.class);
                overridePendingTransition(R.anim.slide_out_right, R.anim.slide_out_left);
                startActivity(intent);
                finish();
                thread.stop();
            }
        });

        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this, MainScreen2.class);
                overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_left);
                startActivity(intent);
                finish();
                thread.stop();
            }
        });

        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this, MainScreen3.class);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                startActivity(intent);
                finish();
                thread.stop();
            }
        });
    }
}