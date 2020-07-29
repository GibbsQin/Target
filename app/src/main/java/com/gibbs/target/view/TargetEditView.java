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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gibbs.target.TargetInfo;
import com.gibbs.target.TargetUtils;
import com.gibbs.target.R;

public class TargetEditView extends FrameLayout {
    private static final String LOG_TAG = "TargetEditView";
    private Context context;
    private TargetInfo mTargetInfo;

    private int targetIconAttr;
    private String targetNameAttr;
    private String targetContentAttr;

    public TargetEditView(Context context) {
        this(context, null);
    }

    public TargetEditView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TargetEditView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TargetView);
        targetIconAttr = typedArray.getResourceId(R.styleable.TargetView_target_icon, R.mipmap.ic_launcher_round);
        targetNameAttr = typedArray.getString(R.styleable.TargetView_target_name);
        targetContentAttr = typedArray.getString(R.styleable.TargetView_target_content);
        initChildView(context);
    }

    private void initChildView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_edit_target, this, true);
        ImageView iconImg = view.findViewById(R.id.target_icon);
        TextView nameTV = view.findViewById(R.id.name);
        TextView dayTV = view.findViewById(R.id.day);

        iconImg.setBackgroundResource(targetIconAttr);
        nameTV.setText(targetNameAttr);
        dayTV.setText(targetContentAttr);
    }

    public void setTargetInfo(TargetInfo targetInfo) {
        mTargetInfo = targetInfo;
        ImageView icon = findViewById(R.id.target_icon);
        TextView name = findViewById(R.id.name);
        TextView content = findViewById(R.id.content);
        TextView day = findViewById(R.id.day);

        icon.setBackgroundResource(targetInfo.getIcon());
        name.setText(targetInfo.getName());
        content.setText(String.format("目标打卡%s天，已坚持%s天", targetInfo.getMax(), targetInfo.getProgress()));
        day.setText(String.format("%s天", (targetInfo.getMax() - targetInfo.getProgress())));

        LinearLayout linearLayout = findViewById(R.id.edit_layout);
        linearLayout.setBackgroundResource(TargetUtils.getRandomColorRes(targetInfo.getBgColor()));
    }

    public TargetInfo getTargetInfo() {
        return mTargetInfo;
    }
}
