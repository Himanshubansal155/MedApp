package com.example.medapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MedicinePage extends AppCompatActivity {
    Button button;
    private final String TAG = "Firebase";
    Map<String, Object> dataMap;
    TextView name;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_page);
        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        android.graphics.drawable.Drawable background = MedicinePage.this.getResources().getDrawable(R.drawable.color);
        getWindow().setBackgroundDrawable(background);
        button = findViewById(R.id.button3);
        name = findViewById(R.id.Name);
        dataMap = new HashMap<>();

        button.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), EditReminder.class);
            startActivity(intent);
        });

        Intent intent = getIntent();
        if(intent != null) {
            HashMap<String, Object> hashMap = (HashMap<String, Object>) intent.getSerializableExtra("hashMap");
            Toast.makeText(this, hashMap.toString(), Toast.LENGTH_SHORT).show();
            name.setText(hashMap.get("Name").toString());
        }
    }
}