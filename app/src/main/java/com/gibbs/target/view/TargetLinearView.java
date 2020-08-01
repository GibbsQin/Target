package com.gibbs.target.view;

import android.content.Context;
import android.util.AttributeSet;

import com.gibbs.target.R;

public class TargetLinearView extends TargetView {
    public TargetLinearView(Context context) {
        super(context);
    }

    public TargetLinearView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TargetLinearView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    int getLayoutRes() {
        return R.layout.view_target_linear;
    }
}
