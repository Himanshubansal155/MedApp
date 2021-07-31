package com.example.medapp.ui.settings;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.medapp.MedicinePage;
import com.example.medapp.R;
import com.example.medapp.ui.home.MyListAdapter;
import com.example.medapp.ui.notifications.NotificationsViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingsFragment extends Fragment {

    Map<String, Object> dataMap;

    String[] weekDays = {"", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    Map<String, List<Integer>> times;
    private SettingsViewModel settingsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        Switch alarmState = root.findViewById(R.id.alarmState);
        dataMap = new HashMap<>();

        alarmState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(alarmState.isChecked()) {
                    String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                    DocumentReference docRef = FirebaseFirestore.getInstance().collection("Users").document(email);
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot snap = task.getResult();
                                assert snap != null;
                                if (snap.exists()) {
                                    dataMap = snap.getData();
                                    assert dataMap != null;
                                    Map<String, HashMap<String, Object>> medicineStore;
                                    medicineStore = (Map<String, HashMap<String, Object>>) dataMap.get("MedicineNames");
                                    Map<String, List<Integer>> times = (Map<String, List<Integer>>) dataMap.get("Times");
                                    List<String> group = (List<String>) medicineStore.get("Medicines");
                                    assert group != null;
                                    for (String MedicineName : group) {
                                        Map<String, Object> medicineName;
                                        medicineName = medicineStore.get(MedicineName);
                                        assert medicineName != null;
                                        List<String> days = (List<String>) medicineName.get("Time");
                                        Date endDate = ((Timestamp) medicineName.get("EndDate")).toDate();
                                        Date startDate = ((Timestamp) medicineName.get("StartDate")).toDate();
                                        List<String> timeStamp;
                                        timeStamp = (List<String>) medicineName.get("TimingSchedule");
                                        Calendar today = Calendar.getInstance();
                                        today.set(Calendar.HOUR_OF_DAY, 0);
                                        today.set(Calendar.MINUTE, 0);
                                        today.set(Calendar.SECOND, 0);
                                        today.set(Calendar.MILLISECOND, 0);
                                        assert timeStamp != null;
                                        if (getDateBetweenDates(startDate, endDate, today.getTime())) {
                                            assert days != null;
                                            for (String day : days) {
                                                if (weekDays[today.get(Calendar.DAY_OF_WEEK)].equals(day)) {
                                                    for (String s : timeStamp) {
                                                        assert times != null;
                                                        List<Integer> time = times.get(s);
                                                        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
                                                        assert time != null;
                                                        intent.putExtra(AlarmClock.EXTRA_HOUR, time.get(0));
                                                        intent.putExtra(AlarmClock.EXTRA_MINUTES, time.get(1));
                                                        intent.putExtra(AlarmClock.EXTRA_MESSAGE, "Take your medicine " + MedicineName);
                                                        startActivity(intent);

                                                    }
                                                }
                                            }

                                        }
                                    }
                                }
                            }
                        }
                    });
                }
            }
        });
        return root;
    }

    public boolean getDateBetweenDates(Date startDate, Date endDate, Date givenDate) {
        List<Date> dates = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);

        while (calendar.getTime().before(endDate)) {
            Date result = calendar.getTime();
            dates.add(result);
            calendar.add(Calendar.DATE, 1);
        }

        for (Date date : dates) {
            if (date.equals(givenDate)) {
                return true;
            }
        }
        return false;
    }
}
