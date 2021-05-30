package com.example.medapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.medapp.R;

public class medicine_Page_fk extends AppCompatActivity {
    private Button setrem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine__page_fk);
        setrem =findViewById(R.id.Editschedule);

        setrem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(medicine_Page_fk.this,Edit_Reminder.class);
                startActivity(intent);
            }
        });

    }

}