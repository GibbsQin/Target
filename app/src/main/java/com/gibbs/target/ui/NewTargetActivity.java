package com.gibbs.target.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.gibbs.target.R;
import com.gibbs.target.TargetApplication;
import com.gibbs.target.TargetInfo;
import com.gibbs.target.TargetUtils;
import com.gibbs.target.base.BaseActivity;
import com.gibbs.target.view.TargetLinearView;

import java.util.List;

public class NewTargetActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout mTargetList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_target);

        mTargetList = findViewById(R.id.target_list);
        LinearLayout linearLayout = (LinearLayout) mTargetList.getChildAt(0);
        linearLayout.setOnClickListener(this);
        initSelectedTargetList();
    }

    private void initSelectedTargetList() {
        List<TargetInfo> list = ((TargetApplication)getApplication()).getInitTargetInfoList();
        for (TargetInfo info : list) {
            TargetLinearView targetLinearView = new TargetLinearView(this);
            mTargetList.addView(targetLinearView);

            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) targetLinearView.getLayoutParams();
            lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
            lp.leftMargin = TargetUtils.dip2px(this,10);
            lp.rightMargin = TargetUtils.dip2px(this,10);
            lp.bottomMargin = TargetUtils.dip2px(this,5);
            lp.topMargin = TargetUtils.dip2px(this,5);
            targetLinearView.setTargetInfo(info);
            targetLinearView.setOnClickListener(this);
        }
    }

    private void startNewTargetActivity() {
        Intent intent = new Intent();
        intent.setClass(this, EditTargetActivity.class);
        startActivityForResult(intent, 1);
    }

    private void startNewTargetActivity(TargetInfo targetInfo) {
        Intent intent = TargetUtils.getIntent(targetInfo);
        intent.setClass(this, EditTargetActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != 1) {
            return;
        }

        setResult(1, data);
        finish();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.custom_target) {
            startNewTargetActivity();
        } else {
            TargetInfo targetInfo = ((TargetLinearView)v).getTargetInfo();
            startNewTargetActivity(targetInfo);
        }
    }
}
