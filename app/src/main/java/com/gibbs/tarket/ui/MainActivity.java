package com.gibbs.tarket.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;

import com.gibbs.tarket.R;

public class MainActivity extends AppCompatActivity  {

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
