package com.example.medapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
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

import com.example.medapp.ui.dashboard.DashboardFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class EditReminder extends AppCompatActivity {
    Button afterDinnerButton, afterBreakfastButton, afterLunchButton, beforeDinnerButton, beforeBreakfastButton, beforeLunchButton;
    Button mondayButton, tuesdayButton, wednesdayButton, thursdayButton, fridayButton, saturdayButton, sundayButton;
    boolean afterDinnerSelected = false;
    boolean afterLunchSelected = false;
    boolean afterBreakfastSelected = false;
    boolean beforeDinnerSelected = false;
    boolean beforeLunchSelected = false;
    boolean beforeBreakfastSelected = false;
    boolean[] daySelection = {false, false, false, false, false, false, false};
    RadioButton genderradioButton, lastButtonReference;
    RadioGroup radioGroup;
    private Stack<String> ll;
    private Stack<String> daysSelect;
    private boolean check = false;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    Button donereminder;
    EditText mname;
    TextView t1, mo;
    DatePickerDialog.OnDateSetListener setListener1, setListener2;
    public int selectedId;
    Date date1, date2;

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
        mondayButton = findViewById(R.id.mondayButton);
        tuesdayButton = findViewById(R.id.tuesdayButton);
        wednesdayButton = findViewById(R.id.wednesdayButton);
        thursdayButton = findViewById(R.id.thursdayButton);
        fridayButton = findViewById(R.id.fridayButton);
        saturdayButton = findViewById(R.id.saturdayButton);
        sundayButton = findViewById(R.id.sundayButton);
        radioGroup = findViewById(R.id.rg);
        donereminder = findViewById(R.id.done);
        mname = findViewById(R.id.medname);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        mo = findViewById(R.id.monthedit);
        t1 = findViewById(R.id.daily);
        ll = new Stack<>();
        daysSelect = new Stack<>();


        afterDinnerButton.setOnClickListener(v -> afterDinnerSelected = afterClickChange(afterDinnerButton, afterDinnerSelected, ll));
        afterBreakfastButton.setOnClickListener(v -> afterBreakfastSelected = afterClickChange(afterBreakfastButton, afterBreakfastSelected, ll));
        afterLunchButton.setOnClickListener(v -> afterLunchSelected = afterClickChange(afterLunchButton, afterLunchSelected, ll));
        beforeLunchButton.setOnClickListener(v -> beforeLunchSelected = afterClickChange(beforeLunchButton, beforeLunchSelected, ll));
        beforeBreakfastButton.setOnClickListener(v -> beforeBreakfastSelected = afterClickChange(beforeBreakfastButton, beforeBreakfastSelected, ll));
        beforeDinnerButton.setOnClickListener(v -> beforeDinnerSelected = afterClickChange(beforeDinnerButton, beforeDinnerSelected, ll));
        mondayButton.setOnClickListener(v -> daySelection[0] = afterClickChange(mondayButton, daySelection[0], daysSelect));
        tuesdayButton.setOnClickListener(v -> daySelection[1] = afterClickChange(tuesdayButton, daySelection[1], daysSelect));
        wednesdayButton.setOnClickListener(v -> daySelection[2] = afterClickChange(wednesdayButton, daySelection[2], daysSelect));
        thursdayButton.setOnClickListener(v -> daySelection[3] = afterClickChange(thursdayButton, daySelection[3], daysSelect));
        fridayButton.setOnClickListener(v -> daySelection[4] = afterClickChange(fridayButton, daySelection[4], daysSelect));
        saturdayButton.setOnClickListener(v -> daySelection[5] = afterClickChange(saturdayButton, daySelection[5], daysSelect));
        sundayButton.setOnClickListener(v -> daySelection[6] = afterClickChange(sundayButton, daySelection[6], daysSelect));

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        mo.setOnClickListener(v -> {
            check = true;
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    EditReminder.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener1, year, month, day);
            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            datePickerDialog.show();
        });
        setListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                date1 = new Date(year - 1900, month, dayOfMonth);
                String date = dayOfMonth + "/" + month + "/" + year;
                mo.setText(date);
            }
        };
        t1.setOnClickListener(v -> {
            check = false;
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    EditReminder.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener2, year, month, day);
            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            datePickerDialog.show();
        });
        setListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                date2 = new Date(year - 1900, month, dayOfMonth);
                String date1 = dayOfMonth + "/" + month + "/" + year;
                t1.setText(date1);
            }
        };

        Bundle extras = getIntent().getExtras();
        if (extras.getString("page").equals("Medicine")) {
            Toast.makeText(this, "From Medicine Page", Toast.LENGTH_SHORT).show();
        } else if (extras.getString("page").equals("Dashboard")) {
            Toast.makeText(this, "From Dashboard Page", Toast.LENGTH_SHORT).show();
        }

        donereminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (extras.getString("page").equals("Dashboard")) {
                    String email = firebaseAuth.getCurrentUser().getEmail();
                    String s = mname.getText().toString();
                    Integer s1 = 0;
                    switch (genderradioButton.getId()) {
                        case R.id.pill1:
                            s1 = R.drawable.pill1;
                            break;
                        case R.id.pill2:
                            s1 = R.drawable.pill2;
                            break;
                        case R.id.pill3:
                            s1 = R.drawable.pill3;
                            break;
                        case R.id.pill4:
                            s1 = R.drawable.pill4;
                            break;
                        case R.id.pill5:
                            s1 = R.drawable.pill5;
                            break;
                    }
                    if (s.isEmpty() || s1 == 0 || ll.isEmpty() || date1.equals("") || date2.equals("")) {
                        Toast.makeText(EditReminder.this, s + s1 + ll + date1 + date2, Toast.LENGTH_SHORT).show();
                        Toast.makeText(EditReminder.this, "Plz Fill all fields ", Toast.LENGTH_SHORT).show();
                    } else {
                        List<String> medicines = new ArrayList<>();
                        Map<String, Object> mp = new HashMap<>();
                        Map<String, Object> mp1 = new HashMap<>();
                        Map<String, Object> map = new HashMap<>();
                        map.put("Icon", s1);
                        map.put("Completed", false);
                        map.put("Time", daysSelect);
                        map.put("Name", s);
                        map.put("TimingSchedule", ll);
                        map.put("StartDate", date1);
                        map.put("EndDate", date2);
                        mp1.put(s, map);
                        mp.put("MedicineNames", mp1);

                        DocumentReference docRef = FirebaseFirestore.getInstance().collection("Users").document(email);
                        docRef.update("MedicineNames.Medicines", FieldValue.arrayUnion(s)).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                docRef.set(mp, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                                        Toast.makeText(EditReminder.this, "Updated", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(EditReminder.this, DashboardFragment.class);
                                        startActivity(intent);
                                    }
                                });
                            }
                        });
                    }
                }

            }

        });
    }

    @SuppressLint("ResourceAsColor")
    public boolean afterClickChange(Button button, boolean afterClick, Stack<String> ll) {
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
        int dr = R.drawable.pill1;
    }

//    public void numberOFWeeks(){
//        Calendar a = new GregorianCalendar(2002,1,22);
//        Calendar b = new GregorianCalendar(2002,1,28);
//        System.out.println(a.get(Calendar.WEEK_OF_YEAR));
//        System.out.println(b.get(Calendar.WEEK_OF_YEAR));
//        int weeks = b.get(Calendar.WEEK_OF_YEAR)-a.get(Calendar.WEEK_OF_YEAR);
//        System.out.println(weeks);
//    }
}