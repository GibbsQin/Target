package com.gibbs.target.ui;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gibbs.target.TargetUtils;
import com.gibbs.target.view.SelectedTargetView;
import com.gibbs.target.R;
import com.gibbs.target.TargetApplication;
import com.gibbs.target.TargetInfo;

import java.util.List;

public class SelectedTargetActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout mTargetList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_target);
        setCustomActionBar();

        mTargetList = findViewById(R.id.target_list);
        LinearLayout linearLayout = (LinearLayout) mTargetList.getChildAt(0);
        linearLayout.setOnClickListener(this);
        initSelectedTargetList();
    }

    private void setCustomActionBar() {
        TextView leftImg = findViewById(R.id.left_view);
        TextView middleTextView = findViewById(R.id.middle_view);
        TextView rightImg = findViewById(R.id.right_view);
        leftImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        leftImg.setText("返回");
        middleTextView.setText("新建目标");
    }

    private void initSelectedTargetList() {
        List<TargetInfo> list = ((TargetApplication)getApplication()).getInitTargetInfoList();
        for (TargetInfo info : list) {
            SelectedTargetView selectedTargetView = new SelectedTargetView(this);
            mTargetList.addView(selectedTargetView);

            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) selectedTargetView.getLayoutParams();
            lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
            lp.leftMargin = TargetUtils.dip2px(this,10);
            lp.rightMargin = TargetUtils.dip2px(this,10);
            lp.bottomMargin = TargetUtils.dip2px(this,5);
            lp.topMargin = TargetUtils.dip2px(this,5);
            selectedTargetView.setTargetInfo(info);
            selectedTargetView.setOnClickListener(this);
        }
    }

    private void startNewTargetActivity() {
        Intent intent = new Intent();
        intent.setClass(this, NewTargetActivity.class);
        startActivityForResult(intent, 1);
    }

    private void startNewTargetActivity(TargetInfo targetInfo) {
        Intent intent = TargetUtils.getIntent(targetInfo);
        intent.setClass(this, NewTargetActivity.class);
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
            TargetInfo targetInfo = ((SelectedTargetView)v).getTargetInfo();
            startNewTargetActivity(targetInfo);
        }
    }
}
