package com.gibbs.target.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.gibbs.target.R;
import com.gibbs.target.TargetInfo;
import com.gibbs.target.TargetUtils;
import com.gibbs.target.dao.TargetDAO;

public class TargetView extends ConstraintLayout implements View.OnTouchListener {
    private static final float SCALE_XY = 1.05f;

    private OnClickListener mOnClickListener;
    private Context context;
    private TextView mTargetName;
    private ImageView mTargetIcon;
    private TextView mContentTextView;
    private TextView mProgressText;
    private ProgressBar mProgressBar;
    private TargetInfo mTargetInfo;

    private int targetIconAttr;
    private String targetNameAttr;

    public TargetView(Context context) {
        this(context, null);
    }

    public TargetView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TargetView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
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
        typedArray.recycle();
        initChildView(context);
    }

    private void initChildView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_target, this, true);
        mTargetName = findViewById(R.id.target_name);
        mTargetIcon = findViewById(R.id.clock_in_icon);
        mProgressText = findViewById(R.id.progress_text);
        mProgressBar = findViewById(R.id.progress_img);
        mContentTextView = findViewById(R.id.target_content);
    }

    private void performClickEvent() {
        if (mTargetInfo.getCompleted() == TargetUtils.COMPLETED) {
            showCancelAlertDialog(context);
        } else if (mTargetInfo.getCompleted() == TargetUtils.UNCOMPLETED) {
            showOkAlertDialog(context);
        }
    }

    public void setTargetInfo(TargetInfo targetInfo) {
        mTargetInfo = targetInfo;
        setName(targetInfo.getName());
        setIcon(targetInfo.getIcon());
        setContent(targetInfo.getContent());
        setProgressMax(targetInfo.getMax());
        setProgress(targetInfo.getProgress());

        setBackground(TargetUtils.createDrawable(getContext(), 10, targetInfo));
        if (targetInfo.getCompleted() == TargetUtils.COMPLETED) {
            mContentTextView.setVisibility(GONE);
        } else {
            mContentTextView.setVisibility(VISIBLE);
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
        mProgressText.setText(String.format("%s/%s", progress, max));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                v.setScaleX(SCALE_XY);
                v.setScaleY(SCALE_XY);
                return true;
            case MotionEvent.ACTION_UP:
                v.setScaleX(1.0f);
                v.setScaleY(1.0f);
                if (mOnClickListener != null) {
                    mOnClickListener.onClick(v);
                }
                return true;
            case MotionEvent.ACTION_CANCEL:
                v.setScaleX(1.0f);
                v.setScaleY(1.0f);
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
                mTargetInfo.setCompleted(TargetUtils.UNCOMPLETED);
                mTargetInfo.setProgress(mTargetInfo.getProgress() - 1);
                TargetDAO.getInstance(context).update(mTargetInfo);

                mContentTextView.setVisibility(VISIBLE);
                setProgress(mProgressBar.getProgress() - 1);
                setBackgroundResource(R.drawable.corner_shape_white);
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
                mTargetInfo.setCompleted(TargetUtils.COMPLETED);
                mTargetInfo.setProgress(mTargetInfo.getProgress() + 1);
                TargetDAO.getInstance(context).update(mTargetInfo);

                mContentTextView.setVisibility(GONE);
                setProgress(mProgressBar.getProgress() + 1);
                setBackground(TargetUtils.createDrawable(getContext(), 10, mTargetInfo));
                okDialog.dismiss();
            }
        });

        okDialog.setView(view);
        okDialog.show();
    }
}
