
package com.example.medapp.ui.home;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medapp.MainActivity;
import com.example.medapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {

    ListView listView;
    DatePickerDialog picker;
    int day, month, year, dayWeek;
    Calendar cldr;
    Button todayButton, monthButton, weekButton;
    ImageButton imageButton;
    String[] days = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ((MainActivity) requireActivity()).getSupportActionBar().hide();
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        TextView calendarDateDisplay = root.findViewById(R.id.calendarDateDisplay);
        TextView dayDisplay = root.findViewById(R.id.dayDisplay);
        imageButton = root.findViewById(R.id.imageButton);
        todayButton = root.findViewById(R.id.today);
        weekButton = root.findViewById(R.id.week);
        monthButton = root.findViewById(R.id.month);
        listView = root.findViewById(R.id.listview);

        calendarDisplay(days, calendarDateDisplay, dayDisplay);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Image Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        String[] mainTitle = {
                "Title 1", "Title 2"
        };

        String[] button1Title = {
                "After Lunch", "After Lunch"
        };
        String[] button2Title = {
                "After Dinner", "After Dinner"
        };
        Integer[] imgId = {
                R.drawable.pill1, R.drawable.pill2
        };
        listDisplay(mainTitle, button1Title, button2Title, imgId, listView);

        todayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lineDisplay(todayButton, weekButton, monthButton);
                String[] mainTitle = {
                        "Title 1", "Title 2"
                };

                String[] button1Title = {
                        "After Lunch", "After Lunch"
                };
                String[] button2Title = {
                        "After Dinner", "After Dinner"
                };
                Integer[] imgId = {
                        R.drawable.pill1, R.drawable.pill2
                };
                listDisplay(mainTitle, button1Title, button2Title, imgId, listView);
            }
        });
        weekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] mainTitle = {
                        "Title 1", "Title 2",
                        "Title 3"
                };

                String[] button1Title = {
                        "After Lunch", "After Lunch", "After Lunch"
                };
                String[] button2Title = {
                        "After Dinner", "After Dinner", "After Dinner"
                };
                Integer[] imgId = {
                        R.drawable.pill1, R.drawable.pill2, R.drawable.pill3
                };
                lineDisplay(weekButton, todayButton, monthButton);
                listDisplay(mainTitle, button1Title, button2Title, imgId, listView);
            }
        });
        monthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] mainTitle = {
                        "Title 1", "Title 2",
                        "Title 3", "Title 4",
                        "Title 5",
                };

                String[] button1Title = {
                        "After Lunch", "After Lunch", "After Lunch", "After Lunch", "After Lunch"
                };
                String[] button2Title = {
                        "After Dinner", "After Dinner", "After Dinner", "After Dinner", "After Dinner"
                };
                Integer[] imgId = {
                        R.drawable.pill1, R.drawable.pill2,
                        R.drawable.pill3, R.drawable.pill4,
                        R.drawable.pill5,
                };
                lineDisplay(monthButton, weekButton, todayButton);
                listDisplay(mainTitle, button1Title, button2Title, imgId, listView);
            }
        });
        return root;
    }

    private void listDisplay(String[] mainTitle, String[] button1Title, String[] button2Title, Integer[] imgId, ListView listView) {
        MyListAdapter adapter = new MyListAdapter(getActivity(), mainTitle, button1Title, button2Title, imgId);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "item clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void lineDisplay(Button todayButton, Button weekButton, Button monthButton) {
        todayButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, android.R.drawable.button_onoff_indicator_on);
        weekButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        monthButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    }

    public void calendarDisplay(String[] days, TextView calendarDateDisplay, TextView dayDisplay){
        cldr = Calendar.getInstance();
        day = cldr.get(Calendar.DAY_OF_MONTH);
        month = cldr.get(Calendar.MONTH);
        year = cldr.get(Calendar.YEAR);
        dayWeek = cldr.get(Calendar.DAY_OF_WEEK);
        Log.d("HomeFragment", String.valueOf(dayWeek));
        calendarDateDisplay.setText(day + "/" + (month + 1) + "/" + year);
        dayDisplay.setText(days[dayWeek]);

        calendarDateDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cldr = Calendar.getInstance();
                day = cldr.get(Calendar.DAY_OF_MONTH);
                month = cldr.get(Calendar.MONTH);
                year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                calendarDateDisplay.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                Date d = new Date(year, monthOfYear, dayOfMonth);
                                Log.d("HomeFragment", String.valueOf(d.getDay()));
                                dayDisplay.setText(days[d.getDay()]);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
    }
}