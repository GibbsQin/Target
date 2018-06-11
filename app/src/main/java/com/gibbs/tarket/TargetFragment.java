package com.gibbs.tarket;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class TargetFragment extends Fragment {

    private static final String LOG_TAG = "TargetFragment";
    private LinearLayout mTargetList;

    public TargetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_target, container, false);

        mTargetList = view.findViewById(R.id.target_list);
        setCustomActionBar(view);
        initView();
        return view;
    }

    private void setCustomActionBar(View view) {
        TextView leftImg = view.findViewById(R.id.left_view);
        TextView middleTextView = view.findViewById(R.id.middle_view);
        TextView rightImg = view.findViewById(R.id.right_view);
        rightImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSelectTargetActivity();
            }
        });

        middleTextView.setText("进行中");
        //rightImg.setBackgroundResource(R.mipmap.add);
        rightImg.setText("添加");
    }

    private void initView() {
        ArrayList<TargetInfo> list = TargetDAO.getInstance(getActivity()).selectAll();
        for (TargetInfo targetInfo : list) {
            addTargetView(targetInfo);
        }
    }

    private void addTargetView(TargetInfo targetInfo) {
        final TargetEditView targetEditView = new TargetEditView(getActivity());
        targetEditView.setTargetInfo(targetInfo);
        TextView editBtn = targetEditView.findViewById(R.id.edit_target);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TargetInfo ti = targetEditView.getTargetInfo();
                startSelectTargetActivity(ti);
            }
        });
        TextView deleteBtn = targetEditView.findViewById(R.id.delete_target);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TargetInfo ti = targetEditView.getTargetInfo();
                TargetDAO.getInstance(getActivity()).delete(ti);
                mTargetList.removeView(targetEditView);
            }
        });
        mTargetList.addView(targetEditView);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) targetEditView.getLayoutParams();
        lp.topMargin = 25;
        lp.bottomMargin = 25;
        lp.leftMargin = 50;
        lp.rightMargin = 50;
    }

    private void startSelectTargetActivity() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), SelectedTargetActivity.class);
        startActivityForResult(intent, 1);
    }

    private void startSelectTargetActivity(TargetInfo targetInfo) {
        Intent intent = TargetUtils.getIntent(targetInfo);
        intent.setClass(getActivity(), SelectedTargetActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(LOG_TAG, "onActivityResult");
        if (resultCode != 1) {
            return;
        }

        TargetInfo targetInfo = TargetUtils.getTargetInfo(data);
        long rowId = TargetDAO.getInstance(getActivity()).insert(targetInfo);
        targetInfo.setRowId(rowId);
        addTargetView(targetInfo);
    }
}
