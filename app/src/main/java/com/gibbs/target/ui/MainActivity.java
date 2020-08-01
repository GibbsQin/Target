package com.gibbs.target.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.FragmentTransaction;

import com.gibbs.target.R;
import com.gibbs.target.base.BaseActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends BaseActivity {
    private static final int SHOW_CLOCK_IN_FRAGMENT = 0;
    private static final int SHOW_TARGET_FRAGMENT = 1;
    private static final int SHOW_USER_FRAGMENT = 2;

    private ClockInFragment mClockInFragment;
    private TargetFragment mTargetFragment;
    private UserFragment mUserFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_clock_in:
                    addFragment(SHOW_CLOCK_IN_FRAGMENT);
                    return true;
                case R.id.navigation_target:
                    addFragment(SHOW_TARGET_FRAGMENT);
                    return true;
                case R.id.navigation_user:
                    addFragment(SHOW_USER_FRAGMENT);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
        }

        mClockInFragment = new ClockInFragment();
        mTargetFragment = new TargetFragment();
        mUserFragment = new UserFragment();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        addFragment(SHOW_CLOCK_IN_FRAGMENT);
    }

    private void addFragment(int position) {
        FragmentTransaction fragmentFragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            case SHOW_CLOCK_IN_FRAGMENT:
                fragmentFragmentTransaction.replace(R.id.fun_fragment, mClockInFragment);
                setTitle("打卡");
                break;
            case SHOW_TARGET_FRAGMENT:
                fragmentFragmentTransaction.replace(R.id.fun_fragment, mTargetFragment);
                setTitle("目标");
                break;
            case SHOW_USER_FRAGMENT:
                fragmentFragmentTransaction.replace(R.id.fun_fragment, mUserFragment);
                setTitle("我的");
                break;
        }
        fragmentFragmentTransaction.commit();
    }
}
