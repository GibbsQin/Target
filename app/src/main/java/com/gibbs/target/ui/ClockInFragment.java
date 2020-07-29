package com.gibbs.target.ui;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gibbs.target.TargetUtils;
import com.gibbs.target.R;
import com.gibbs.target.TargetInfo;
import com.gibbs.target.dao.TargetDAO;
import com.gibbs.target.view.TargetView;

import java.util.ArrayList;


public class ClockInFragment extends Fragment {

    private View mView;

    public ClockInFragment() {
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
        View view = inflater.inflate(R.layout.fragment_clock_in, container, false);
        mView = view;
        setCustomActionBar(view);
        initView();

        return view;
    }

    private void setCustomActionBar(View view) {
        TextView leftImg = view.findViewById(R.id.left_view);
        TextView middleTextView = view.findViewById(R.id.middle_view);
        TextView rightImg = view.findViewById(R.id.right_view);

        middleTextView.setText("打卡");
        //rightImg.setBackgroundResource(R.mipmap.ic_launcher_round);
    }

    private void initView() {
        ArrayList<TargetInfo> list = TargetDAO.getInstance(getActivity()).selectAll();
        for (TargetInfo targetInfo : list) {
            addTargetView(targetInfo);
        }
    }

    private void addTargetView(TargetInfo targetInfo) {
        LinearLayout leftView = mView.findViewById(R.id.left_tool_kit);
        LinearLayout rightView = mView.findViewById(R.id.right_tool_kit);
        TargetView targetView = new TargetView(getActivity());
        targetView.setTargetInfo(targetInfo);

        if (leftView.getChildCount() > rightView.getChildCount()) {
            rightView.addView(targetView);

            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) targetView.getLayoutParams();
            lp.setMargins(30,30,60,30);
            lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
            lp.height = TargetUtils.getScreenWidth(getActivity()) * 3/8;
        } else {
            leftView.addView(targetView);

            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) targetView.getLayoutParams();
            lp.setMargins(60,30,30,30);
            lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
            lp.height = TargetUtils.getScreenWidth(getActivity()) * 3/8;
        }
    }
}
