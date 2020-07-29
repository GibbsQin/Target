package com.gibbs.tarket.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.gibbs.tarket.R;
import com.gibbs.tarket.TargetInfo;
import com.gibbs.tarket.TargetUtils;
import com.gibbs.tarket.preference.SaySelfPreference;
import com.gibbs.tarket.preference.SelectPreference;
import com.gibbs.tarket.preference.TargetInfoPreference;


public class NewTargetActivity extends Activity {
    private static final String LOG_TAG = "NewTargetActivity";
    private TargetInfo mTargetInfo;
    private NewTargetFragment newTargetFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_target);
        setCustomActionBar();
        mTargetInfo = TargetUtils.getTargetInfo(getIntent());
        Bundle bundle = TargetUtils.getBundle(mTargetInfo);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        newTargetFragment = new NewTargetFragment();
        newTargetFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.new_target_framelayout, newTargetFragment);
        fragmentTransaction.commit();
    }

    private void setCustomActionBar() {
        TextView leftImg = findViewById(R.id.left_view);
        TextView middleTextView = findViewById(R.id.middle_view);
        TextView rightImg = findViewById(R.id.right_view);

        rightImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.sendEmptyMessage(0);
            }
        });
        leftImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        leftImg.setText("返回");
        middleTextView.setText("新建目标");
        rightImg.setText("完成");
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            TargetInfo targetInfo = newTargetFragment.getTargetInfo();
            if (targetInfo.name == null || targetInfo.name.length() == 0) {
                Toast.makeText(NewTargetActivity.this, "请输入目标名称", Toast.LENGTH_LONG).show();
                return;
            }
            setResult(1, TargetUtils.getIntent(targetInfo));
            finish();
        }
    };

    public static class NewTargetFragment extends PreferenceFragment implements OnPreferenceClickListener {
        SelectPreference datePref;
        SelectPreference clockInPref;
        SelectPreference timePref;
        TargetInfoPreference targetInfoPref;
        SaySelfPreference saySelfPref;

        private TargetInfo mTargetInfo;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_new_target);
            setHasOptionsMenu(true);

            datePref = (SelectPreference) findPreference("date");
            datePref.setOnPreferenceClickListener(this);

            clockInPref = (SelectPreference) findPreference("clock_in");
            clockInPref.setOnPreferenceClickListener(this);

            timePref = (SelectPreference) findPreference("time");
            timePref.setOnPreferenceClickListener(this);

            String[] time = TargetUtils.getNowDateAndTime();
            datePref.setContent(time[0]);
            clockInPref.setContent(time[1]);
            timePref.setContent(time[1]);

            targetInfoPref = (TargetInfoPreference) findPreference("target_icon");
            saySelfPref = (SaySelfPreference) findPreference("say_self");

            mTargetInfo = new TargetInfo();
            mTargetInfo = TargetUtils.getTargetInfo(getArguments());
            targetInfoPref.setEditText(mTargetInfo.name);
            saySelfPref.setContent(mTargetInfo.content);
            Log.d(LOG_TAG, "Fragment is created!");
        }

        @Override
        public boolean onPreferenceClick(Preference preference) {
            String key = preference.getKey();
            if (key.equals("date")) {
                DatePickerDialog datePicker=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Log.d(LOG_TAG, "date : " + year+":"+month+":"+dayOfMonth);
                        datePref.setContent(year+":"+month+":"+dayOfMonth);
                    }
                }, 2013, 7, 20);
                datePicker.show();
                return true;
            } else if (key.equals("clock_in")) {
                TimePickerDialog clockInPicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Log.d(LOG_TAG, "time : " + hourOfDay + ":" + minute);
                        clockInPref.setContent(hourOfDay + ":" + minute);
                    }
                },0,0,true);
                clockInPicker.show();
                return true;
            } else if (key.equals("time")) {
                TimePickerDialog timePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Log.d(LOG_TAG, "time : " + hourOfDay + ":" + minute);
                        timePref.setContent(hourOfDay + ":" + minute);
                    }
                },0,0,true);
                timePicker.show();
                return true;
            }
            return false;
        }

        public TargetInfo getTargetInfo() {
            mTargetInfo.setName(targetInfoPref.getEditText());
            mTargetInfo.setContent(saySelfPref.getSaySelf());
            mTargetInfo.setCompleted(TargetUtils.UNCOMPLETED);
            mTargetInfo.setProgress(0);
            mTargetInfo.setMax(TargetUtils.getCurrentMonthDay());
            mTargetInfo.setIcon(R.mipmap.default_target_icon);
            mTargetInfo.setBgColor(TargetUtils.getRandomColor());

            TargetUtils.saveLastColor(getActivity());
            return mTargetInfo;
        }
    }
}
