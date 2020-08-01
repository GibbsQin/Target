package com.gibbs.target.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.gibbs.target.R;

public class JumpSettingView extends LinearLayout {
    private int iconAttr;
    private String nameAttr;

    private ImageView mIvIcon;
    private TextView mTvName;

    public JumpSettingView(Context context) {
        this(context, null);
    }

    public JumpSettingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JumpSettingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.JumpSettingView);
        iconAttr = typedArray.getResourceId(R.styleable.JumpSettingView_icon, 0);
        nameAttr = typedArray.getString(R.styleable.JumpSettingView_name);
        typedArray.recycle();

        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.pref_target_setting, this, true);
        mIvIcon = findViewById(R.id.iv_setting_icon);
        mTvName = findViewById(R.id.tv_setting_name);

        if (iconAttr > 0) {
            setIcon(iconAttr);
        }
        setName(nameAttr);
    }

    public void setIcon(int iconAttr) {
        this.iconAttr = iconAttr;
        mIvIcon.setBackgroundResource(iconAttr);
    }

    public void setName(String nameAttr) {
        this.nameAttr = nameAttr;
        mTvName.setText(nameAttr);
    }
}
