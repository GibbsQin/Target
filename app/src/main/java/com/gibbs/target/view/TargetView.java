package com.gibbs.target.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gibbs.target.TargetInfo;
import com.gibbs.target.TargetUtils;
import com.gibbs.target.dao.TargetDAO;
import com.gibbs.target.R;

public class TargetView extends FrameLayout implements View.OnTouchListener {
    private static final String LOG_TAG = "TargetView";
    private Animation animation;
    private OnClickListener mOnClickListener;
    private Context context;
    private TextView mTargetName;
    private ImageView mTargetIcon;
    private TextView mContentTextView;
    private TextView mProgressText;
    private ProgressBar mProgressBar;
    private LinearLayout mContentCompleteLayout;
    private TargetInfo mTargetInfo;

    private int targetIconAttr;
    private String targetNameAttr;
    private String targetContent1;
    private String[] targetContent2;

    public TargetView(Context context) {
        this(context, null);
    }

    public TargetView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TargetView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        animation = AnimationUtils.loadAnimation(context, R.anim.zoom_out);
        setOnTouchListener(this);
        mOnClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                performClickEvent();
            }
        };

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TargetView);
        targetIconAttr = typedArray.getResourceId(R.styleable.TargetView_target_icon, R.mipmap.ic_launcher_round);
        targetNameAttr = typedArray.getString(R.styleable.TargetView_target_name);
        initChildView(context);
    }

    private void initChildView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_target, this, true);
        mTargetName = findViewById(R.id.target_name);
        mTargetIcon = findViewById(R.id.clock_in_icon);
        mProgressText = findViewById(R.id.progress_text);
        mProgressBar = findViewById(R.id.progress_img);
        mContentTextView = findViewById(R.id.target_content);
        mContentCompleteLayout = findViewById(R.id.target_complete);
        setBgColor(R.drawable.corner_shape);
    }

    public void setBgColor(int res) {
        setBackgroundResource(res);
    }

    private void performClickEvent() {
        if (mTargetInfo.completed == TargetUtils.COMPLETED) {
            showCancelAlertDialog(context);
        } else if (mTargetInfo.completed == TargetUtils.UNCOMPLETED) {
            showOkAlertDialog(context);
        }
    }

    public void setTargetInfo(TargetInfo targetInfo) {
        mTargetInfo = targetInfo;
        setName(targetInfo.name);
        setIcon(targetInfo.icon);
        setContent(targetInfo.content);
        setProgressMax(targetInfo.max);
        setProgress(targetInfo.progress);

        if (targetInfo.completed == TargetUtils.COMPLETED) {
            setBgColor(TargetUtils.getRandomColorRes(targetInfo.bgColor));
            mContentTextView.setVisibility(GONE);
            mContentCompleteLayout.setVisibility(VISIBLE);
        } else {
            setBgColor(R.drawable.corner_shape_white);
            mContentTextView.setVisibility(VISIBLE);
            mContentCompleteLayout.setVisibility(GONE);
        }
    }

    public void setIcon(int res) {
        mTargetIcon.setBackgroundResource(res);
    }

    public void setName(String name) {
        mTargetName.setText(name);
    }

    public void setContent(String content) {
        mContentTextView.setText(content);
    }

    public void setProgressMax(int max) {
        mProgressBar.setMax(max);
    }

    public void setProgress(int progress) {
        mProgressBar.setProgress(progress);
        int max = mProgressBar.getMax();
        mProgressText.setText(Integer.toString(progress) + "/" + Integer.toString(max));

        setCompleteText(new String[]{"打卡的第" + Integer.toString(progress) + "天"});
    }

    public void setCompleteText(String[] content) {
        mContentCompleteLayout.removeAllViews();
        for (int i = 0; i < content.length; i++) {
            View view = LayoutInflater.from(context).inflate(
                    R.layout.item_target_complete, null, false);
            mContentCompleteLayout.addView(view);
            TextView textView = view.findViewById(R.id.target_content2_content);
            textView.setText(content[i]);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                v.startAnimation(animation);
                return true;
            case MotionEvent.ACTION_UP:
                v.clearAnimation();
                if (mOnClickListener != null) {
                    mOnClickListener.onClick(v);
                }
                return true;
        }
        return false;
    }

    private void showCancelAlertDialog(final Context context) {
        final AlertDialog cancelDialog = new AlertDialog.Builder(context).create();

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_cancel,null);
        ImageView closeBtn = view.findViewById(R.id.close);
        Button okBtn = view.findViewById(R.id.ok);
        Button cancelBtn = view.findViewById(R.id.cancel);

        closeBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelDialog.dismiss();
            }
        });
        cancelBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelDialog.dismiss();
            }
        });
        okBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mTargetInfo.completed = TargetUtils.UNCOMPLETED;
                mTargetInfo.progress = mTargetInfo.progress - 1;
                TargetDAO.getInstance(context).update(mTargetInfo);

                mContentTextView.setVisibility(VISIBLE);
                mContentCompleteLayout.setVisibility(GONE);
                setProgress(mProgressBar.getProgress() - 1);
                setBgColor(R.drawable.corner_shape_white);
                cancelDialog.dismiss();
            }
        });

        cancelDialog.setView(view);
        cancelDialog.show();
    }

    private void showOkAlertDialog(final Context context) {
        final AlertDialog okDialog = new AlertDialog.Builder(context).create();

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_ok,null);
        ImageView closeBtn = view.findViewById(R.id.close);
        Button okBtn = view.findViewById(R.id.ok);
        Button cancelBtn = view.findViewById(R.id.cancel);
        closeBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                okDialog.dismiss();
            }
        });
        cancelBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                okDialog.dismiss();
            }
        });
        okBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mTargetInfo.completed = TargetUtils.COMPLETED;
                mTargetInfo.progress = mTargetInfo.progress + 1;
                TargetDAO.getInstance(context).update(mTargetInfo);

                mContentTextView.setVisibility(GONE);
                mContentCompleteLayout.setVisibility(VISIBLE);
                setProgress(mProgressBar.getProgress() + 1);
                setBgColor(TargetUtils.getRandomColorRes(mTargetInfo.bgColor));
                okDialog.dismiss();
            }
        });

        okDialog.setView(view);
        okDialog.show();
    }
}
