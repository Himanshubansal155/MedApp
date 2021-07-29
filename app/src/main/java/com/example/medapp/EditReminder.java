package com.example.medapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class EditReminder extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    Button donereminder;
    EditText mname;
    TextView t1,mo;
    DatePickerDialog.OnDateSetListener setListener1,setListener2;
    private int selectedId;
    private Stack<String> ll;
    private  boolean check=false;

    Button afterDinnerButton, afterBreakfastButton, afterLunchButton, beforeDinnerButton, beforeBreakfastButton, beforeLunchButton,confirm_butt;
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

        donereminder=findViewById(R.id.done);
        mname=findViewById(R.id.medname);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        mo=findViewById(R.id.monthedit);
        t1=findViewById(R.id.daily);

        afterDinnerButton = findViewById(R.id.afterDinnerButton);
        afterBreakfastButton = findViewById(R.id.afterBreakfastButton);
        afterLunchButton = findViewById(R.id.afterLunchButton);
        beforeDinnerButton = findViewById(R.id.beforeDinnerButton);
        beforeBreakfastButton = findViewById(R.id.beforeBreakfastButton);
        beforeLunchButton = findViewById(R.id.beforeLunchButton);
        radioGroup = findViewById(R.id.rg);
         ll=new Stack<>();

        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);
        mo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check=true;
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        EditReminder.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListener1,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();

            }
        });
        setListener1=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String date=day+"/"+month+"/"+year;
                mo.setText(date);
            }
        };
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check=false;
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        EditReminder.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListener2,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();


            }
        });
    setListener2=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            month=month+1;
            String date1=day+"/"+month+"/"+year;
            t1.setText(date1);
        }
    };
        afterDinnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                afterDinnerSelected = afterClickChange(afterDinnerButton, afterDinnerSelected);
//                ll.add(afterDinnerButton.getText().toString());
            }
        });
        afterBreakfastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                afterBreakfastSelected = afterClickChange(afterBreakfastButton, afterBreakfastSelected);
//                ll.add(afterBreakfastButton.getText().toString());
            }
        });
        afterLunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                afterLunchSelected = afterClickChange(afterLunchButton, afterLunchSelected);

            }
        });
        beforeLunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beforeLunchSelected = afterClickChange(beforeLunchButton, beforeLunchSelected);
            }
        });
        beforeBreakfastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beforeBreakfastSelected = afterClickChange(beforeBreakfastButton, beforeBreakfastSelected);
            }
        });
        beforeDinnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beforeDinnerSelected = afterClickChange(beforeDinnerButton, beforeDinnerSelected);

            }
        });

        donereminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=firebaseAuth.getCurrentUser().getEmail();
                String s=mname.getText().toString();
                String s1=Integer.toString(selectedId);
                String ms2=mo.getText().toString();
                String ds3=t1.getText().toString();
                if(s.isEmpty() || s1.equals("0") || ll.isEmpty() ||ms2.isEmpty() || ds3.isEmpty()){
                    Toast.makeText(EditReminder.this, "Plz Fill all fields ", Toast.LENGTH_SHORT).show();
                }
                else {
                        Map<String, Object> mp = new HashMap<>();
                        Map<String, Object> mp1 = new HashMap<>();
                        Map<String, Object> map = new HashMap<>();
                        map.put("Type", s1);
                        map.put("Time and Schedule", ll);
                        map.put("Start Date", ms2);
                        map.put("End Date", ds3);
                        mp1.put("Medicine", s);
                        mp1.put(s, map);
                        mp.put("Email", email);
                        mp.put("Medicine Names", mp1);
                        mp.put("User Name", firebaseAuth.getCurrentUser().getDisplayName());
                        mp.put("Phone", firebaseAuth.getCurrentUser().getPhoneNumber());

                        firebaseFirestore.collection("Users").document(email).set(mp).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(EditReminder.this, "Data base updated", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }

        });

    }
    @SuppressLint("ResourceAsColor")
    public boolean afterClickChange(Button button, boolean afterClick) {
        afterClick = !afterClick;
        if (afterClick == true) {
            button.getBackground().setAlpha(100);
            ll.add(button.getText().toString());

        } else {
            button.getBackground().setAlpha(255);
            ll.pop();

        }
        return afterClick;
    }

    public void onClickMethod(View v) {
        selectedId = radioGroup.getCheckedRadioButtonId();
        genderradioButton = (RadioButton) findViewById(selectedId);
        if (lastButtonReference != null) {
            lastButtonReference.getBackground().setAlpha(255);
        }
        genderradioButton.getBackground().setAlpha(150);
        lastButtonReference = genderradioButton;
    }
}