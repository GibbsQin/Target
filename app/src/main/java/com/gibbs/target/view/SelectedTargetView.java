package com.gibbs.target.view;

import android.content.Context;
import android.content.res.TypedArray;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.gibbs.target.R;
import com.gibbs.target.TargetInfo;
import com.gibbs.target.TargetUtils;

public class SelectedTargetView extends FrameLayout {
    private static final String LOG_TAG = "TargetEditView";

    private Context context;
    private TargetInfo mTargetInfo;

    private int targetIconAttr;
    private String targetNameAttr;
    private String targetContentAttr;

    public SelectedTargetView(Context context) {
        this(context, null);
    }

    public SelectedTargetView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectedTargetView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TargetView);
        targetIconAttr = typedArray.getResourceId(R.styleable.TargetView_target_icon, R.mipmap.ic_launcher_round);
        targetNameAttr = typedArray.getString(R.styleable.TargetView_target_name);
        targetContentAttr = typedArray.getString(R.styleable.TargetView_target_content);
        mTargetInfo = new TargetInfo(-1,targetIconAttr,targetNameAttr,targetContentAttr,0,0,30,0);
        initChildView(context);
    }

    private void initChildView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_selected_target, this, true);
        ImageView iconImg = view.findViewById(R.id.target_icon);
        TextView nameTV = view.findViewById(R.id.target_name);
        TextView contentTV = view.findViewById(R.id.target_content);

        iconImg.setBackgroundResource(targetIconAttr);
        nameTV.setText(targetNameAttr);
        contentTV.setText(targetContentAttr);
    }

    public void setTargetInfo(TargetInfo targetInfo) {
        mTargetInfo = targetInfo;

        targetIconAttr = targetInfo.getIcon();
        targetNameAttr = targetInfo.getName();
        targetContentAttr = targetInfo.getContent();

        setBackground(TargetUtils.createDrawable(getContext(), 10, targetInfo));
        initChildView(context);
    }

    public TargetInfo getTargetInfo() {
        return mTargetInfo;
    }
}
