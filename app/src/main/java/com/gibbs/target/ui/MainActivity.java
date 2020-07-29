package com.gibbs.target.ui;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.gibbs.target.R;
import com.gibbs.target.base.BaseActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends BaseActivity {

    private ClockInFragment mClockInFragment;
    private TargetFragment mTargetFragment;
    private UserFragment mUserFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_clock_in:
                    addFragment(0);
                    return true;
                case R.id.navigation_target:
                    addFragment(1);
                    return true;
                case R.id.navigation_user:
                    addFragment(2);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mClockInFragment = new ClockInFragment();
        mTargetFragment = new TargetFragment();
        mUserFragment = new UserFragment();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        addFragment(0);
    }

    private void addFragment(int position) {
        FragmentTransaction fragmentFragmentTransaction = getFragmentManager().beginTransaction();
        switch (position) {
            case 0:
                fragmentFragmentTransaction.replace(R.id.fun_fragment, mClockInFragment);
                break;
            case 1:
                fragmentFragmentTransaction.replace(R.id.fun_fragment, mTargetFragment);
                break;
            case 2:
                fragmentFragmentTransaction.replace(R.id.fun_fragment, mUserFragment);
                break;
        }
        fragmentFragmentTransaction.commit();
    }
}
