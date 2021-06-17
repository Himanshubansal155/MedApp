package com.example.medapp.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.medapp.MedicinePage;
import com.example.medapp.R;
import com.example.medapp.ui.home.MyListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    ListView listView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        FloatingActionButton fab = root.findViewById(R.id.floatingActionButton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MedicinePage.class);
                startActivity(intent);
            }
        });

        listView = root.findViewById(R.id.medicineList);

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

        MyListAdapter adapter = new MyListAdapter(getActivity(), mainTitle, button1Title, button2Title, imgId);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "item clicked", Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }
}