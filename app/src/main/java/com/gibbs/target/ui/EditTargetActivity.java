package com.gibbs.target.ui;

import android.os.Bundle;

import androidx.fragment.app.FragmentTransaction;

import com.gibbs.target.R;
import com.gibbs.target.TargetInfo;
import com.gibbs.target.TargetUtils;
import com.gibbs.target.base.BaseActivity;


public class EditTargetActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_target);
        TargetInfo targetInfo = TargetUtils.getTargetInfo(getIntent());
        Bundle bundle = TargetUtils.getBundle(targetInfo);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        EditTargetFragment editTargetFragment = new EditTargetFragment();
        editTargetFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.edit_target_frame_layout, editTargetFragment);
        fragmentTransaction.commit();
    }
}
