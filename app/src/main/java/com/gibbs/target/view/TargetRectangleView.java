package com.gibbs.target.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.gibbs.target.R;
import com.gibbs.target.TargetInfo;

public class TargetRectangleView extends TargetView {
    public TargetRectangleView(Context context) {
        super(context);
    }

    public TargetRectangleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TargetRectangleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    int getLayoutRes() {
        return R.layout.view_target_rectangle;
    }

    @Override
    public void setTargetInfo(TargetInfo targetInfo) {
        super.setTargetInfo(targetInfo);
        TextView day = findViewById(R.id.day);

        mTvContent.setText(String.format("目标打卡%s天，已坚持%s天", targetInfo.getMax(), targetInfo.getProgress()));
        day.setText(String.format("%s天", (targetInfo.getMax() - targetInfo.getProgress())));
    }
}
