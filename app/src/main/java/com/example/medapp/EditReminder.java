package com.example.medapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class EditReminder extends AppCompatActivity {
    Button afterDinnerButton, afterBreakfastButton, afterLunchButton, beforeDinnerButton, beforeBreakfastButton, beforeLunchButton;
    boolean afterDinnerSelected = false;
    boolean afterLunchSelected = false;
    boolean afterBreakfastSelected = false;
    boolean beforeDinnerSelected = false;
    boolean beforeLunchSelected = false;
    boolean beforeBreakfastSelected = false;
    RadioButton genderradioButton, lastButtonReference;
    RadioGroup radioGroup;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reminder);

        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        android.graphics.drawable.Drawable background = EditReminder.this.getResources().getDrawable(R.drawable.color);
        getWindow().setBackgroundDrawable(background);

        afterDinnerButton = findViewById(R.id.afterDinnerButton);
        afterBreakfastButton = findViewById(R.id.afterBreakfastButton);
        afterLunchButton = findViewById(R.id.afterLunchButton);
        beforeDinnerButton = findViewById(R.id.beforeDinnerButton);
        beforeBreakfastButton = findViewById(R.id.beforeBreakfastButton);
        beforeLunchButton = findViewById(R.id.beforeLunchButton);
        radioGroup = findViewById(R.id.rg);

        afterDinnerButton.setOnClickListener(v -> afterDinnerSelected = afterClickChange(afterDinnerButton, afterDinnerSelected));
        afterBreakfastButton.setOnClickListener(v -> afterBreakfastSelected = afterClickChange(afterBreakfastButton, afterBreakfastSelected));
        afterLunchButton.setOnClickListener(v -> afterLunchSelected = afterClickChange(afterLunchButton, afterLunchSelected));
        beforeLunchButton.setOnClickListener(v -> beforeLunchSelected = afterClickChange(beforeLunchButton, beforeLunchSelected));
        beforeBreakfastButton.setOnClickListener(v -> beforeBreakfastSelected = afterClickChange(beforeBreakfastButton, beforeBreakfastSelected));
        beforeDinnerButton.setOnClickListener(v -> beforeDinnerSelected = afterClickChange(beforeDinnerButton, beforeDinnerSelected));

        Bundle extras = getIntent().getExtras();
        if(extras.getString("page").equals("Medicine")){
            Toast.makeText(this, "From Medicine Page", Toast.LENGTH_SHORT).show();
        } else if (extras.getString("page").equals("Dashboard")){
            Toast.makeText(this, "From Dashboard Page", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("ResourceAsColor")
    public boolean afterClickChange(Button button, boolean afterClick) {
        afterClick = !afterClick;
        if (afterClick == true) {
            button.getBackground().setAlpha(100);
        } else {
            button.getBackground().setAlpha(255);
        }
        return afterClick;
    }

    public void onClickMethod(View v) {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        genderradioButton = (RadioButton) findViewById(selectedId);
        if (lastButtonReference != null) {
            lastButtonReference.getBackground().setAlpha(255);
        }
        genderradioButton.getBackground().setAlpha(150);
        lastButtonReference = genderradioButton;
    }
}