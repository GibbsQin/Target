package com.gibbs.target.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gibbs.target.R;
import com.gibbs.target.TargetInfo;
import com.gibbs.target.TargetUtils;
import com.gibbs.target.dao.TargetDAO;

public class TargetSquareView extends TargetView implements View.OnTouchListener {
    private static final float SCALE_XY = 1.05f;

    private OnClickListener mOnClickListener;
    private TextView mProgressText;
    private ProgressBar mProgressBar;

    public TargetSquareView(Context context) {
        super(context);
    }

    public TargetSquareView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TargetSquareView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    int getLayoutRes() {
        return R.layout.view_target_square;
    }

    @Override
    void initView(Context context) {
        super.initView(context);
        mProgressText = findViewById(R.id.progress_text);
        mProgressBar = findViewById(R.id.progress_img);
        setOnTouchListener(this);
        mOnClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                performClickEvent();
            }
        };
    }

    private void performClickEvent() {
        if (mTargetInfo.getCompleted() == TargetUtils.COMPLETED) {
            showCancelAlertDialog(getContext());
        } else if (mTargetInfo.getCompleted() == TargetUtils.UNCOMPLETED) {
            showOkAlertDialog(getContext());
        }
    }

    @Override
    public void setTargetInfo(TargetInfo targetInfo) {
        super.setTargetInfo(targetInfo);
        mTargetInfo = targetInfo;
        setName(targetInfo.getName());
        setContent(targetInfo.getContent());
        setProgressMax(targetInfo.getMax());
        setProgress(targetInfo.getProgress());

        if (targetInfo.getCompleted() == TargetUtils.COMPLETED) {
            mTvContent.setVisibility(GONE);
        } else {
            mTvContent.setVisibility(VISIBLE);
        }
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
        new AlertDialog.Builder(context)
                .setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTargetInfo.setCompleted(TargetUtils.UNCOMPLETED);
                        mTargetInfo.setProgress(mTargetInfo.getProgress() - 1);
                        TargetDAO.getInstance(context).update(mTargetInfo);

                        mTvContent.setVisibility(VISIBLE);
                        setProgress(mProgressBar.getProgress() - 1);
                        setBackgroundResource(R.drawable.corner_shape_white);
                    }
                })
                .setNegativeButton("不再显示", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    private void showOkAlertDialog(final Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_ok, null);
        new AlertDialog.Builder(context)
                .setView(view)
                .setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTargetInfo.setCompleted(TargetUtils.COMPLETED);
                        mTargetInfo.setProgress(mTargetInfo.getProgress() + 1);
                        TargetDAO.getInstance(context).update(mTargetInfo);

                        mTvContent.setVisibility(GONE);
                        setProgress(mProgressBar.getProgress() + 1);
                        setBackground(TargetUtils.createDrawable(getContext()));
                    }
                })
                .setNegativeButton("不再显示", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }
}
