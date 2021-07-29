package com.example.medapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MedicinePage extends AppCompatActivity {
    Button ereminder,siout;
    EditText e1,e2;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;


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
        ereminder = findViewById(R.id.Editreminder);
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        siout=findViewById(R.id.sout);
        ereminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
////                String s=firebaseAuth.getCurrentUser().getEmail();
//                String s1=e1.getText().toString();
//
//                String fulname=e2.getText().toString();
//                if(s1.isEmpty()){
//                    Toast.makeText(MedicinePage.this, "Plz fill all feilds", Toast.LENGTH_SHORT).show();
//                }
//                else {
//
//                    Map<String,Object> map=new HashMap<>();
//                    map.put("Medicine name",s1);
//                    firebaseFirestore.collection("Users").document(fulname).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if(task.isSuccessful()){
//                                Toast.makeText(MedicinePage.this, "DAta base added", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//                    Toast.makeText(MedicinePage.this, "Medicine mane is :-"+s1+" Email ", Toast.LENGTH_SHORT).show();
//                }
                Intent intent = new Intent(getApplicationContext(), EditReminder.class);
                startActivity(intent);
            }

        });
        siout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth.signOut();
                startActivity(new Intent(MedicinePage.this,LoginActivity.class));
                finish();
                Toast.makeText(MedicinePage.this, "Sign out", Toast.LENGTH_SHORT).show();
            }
        });
    }
}