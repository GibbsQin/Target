package com.gibbs.target.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.gibbs.target.R;
import com.gibbs.target.TargetInfo;
import com.gibbs.target.TargetUtils;

abstract class TargetView extends ConstraintLayout {
    int targetIconAttr;
    String targetNameAttr;
    String targetContentAttr;

    ImageView mIvIcon;
    TextView mTvName;
    TextView mTvContent;

    TargetInfo mTargetInfo;

    public TargetView(Context context) {
        this(context, null);
    }

    public TargetView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TargetView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TargetView);
        targetIconAttr = typedArray.getResourceId(R.styleable.TargetView_target_icon, R.mipmap.default_target_icon);
        targetNameAttr = typedArray.getString(R.styleable.TargetView_target_name);
        targetContentAttr = typedArray.getString(R.styleable.TargetView_target_content);
        typedArray.recycle();

        initView(context);
    }

    abstract int getLayoutRes();

    void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(getLayoutRes(), this, true);
        mIvIcon = view.findViewById(R.id.target_icon);
        mTvName = view.findViewById(R.id.target_name);
        mTvContent = view.findViewById(R.id.target_content);
        displayInfo();
    }

    void displayInfo() {
        setIcon(targetIconAttr);
        setName(targetNameAttr);
        setContent(targetContentAttr);
    }

    public void setTargetInfo(TargetInfo targetInfo) {
        mTargetInfo = targetInfo;
        targetNameAttr = targetInfo.getName();
        targetContentAttr = targetInfo.getContent();
        displayInfo();
        setBackground(TargetUtils.createDrawable(getContext()));
    }

    public TargetInfo getTargetInfo() {
        return mTargetInfo;
    }

    public void setIcon(int res) {
        mIvIcon.setBackgroundResource(res);
    }

    public void setName(String name) {
        mTvName.setText(name);
    }

    public void setContent(String content) {
        mTvContent.setText(content);
    }
}
