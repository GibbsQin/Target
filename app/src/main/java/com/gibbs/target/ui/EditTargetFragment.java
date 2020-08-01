package com.gibbs.target.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gibbs.target.R;
import com.gibbs.target.TargetInfo;
import com.gibbs.target.TargetUtils;

public class EditTargetFragment extends Fragment {
    private TargetInfo mTargetInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTargetInfo = new TargetInfo();
        mTargetInfo = TargetUtils.getTargetInfo(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_target, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

//    @Override
//    public boolean onPreferenceClick(Preference preference) {
//        String key = preference.getKey();
//        switch (key) {
//            case "date":
//                DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
//
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        datePref.setContent(year + ":" + month + ":" + dayOfMonth);
//                    }
//                }, 2013, 7, 20);
//                datePicker.show();
//                return true;
//            case "clock_in":
//                TimePickerDialog clockInPicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                        clockInPref.setContent(hourOfDay + ":" + minute);
//                    }
//                }, 0, 0, true);
//                clockInPicker.show();
//                return true;
//            case "time":
//                TimePickerDialog timePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                        timePref.setContent(hourOfDay + ":" + minute);
//                    }
//                }, 0, 0, true);
//                timePicker.show();
//                return true;
//        }
//        return false;
//    }
}
