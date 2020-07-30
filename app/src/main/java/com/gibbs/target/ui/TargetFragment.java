package com.gibbs.target.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gibbs.target.R;
import com.gibbs.target.TargetInfo;
import com.gibbs.target.TargetUtils;
import com.gibbs.target.dao.TargetDAO;
import com.gibbs.target.view.TargetEditView;

import java.util.ArrayList;


public class TargetFragment extends Fragment {

    private static final String LOG_TAG = "TargetFragment";

    private ArrayList<TargetInfo> mTargetInfoList = new ArrayList<>();

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
        mTargetInfoList = TargetDAO.getInstance(getActivity()).selectAll();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clock_in, container, false);
        RecyclerView recyclerViewClock = view.findViewById(R.id.rv_clock);
        recyclerViewClock.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerViewClock.setAdapter(new TargetAdapter());

        return view;
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
//        addTargetView(targetInfo);
    }

    private static final class TargetViewHolder extends RecyclerView.ViewHolder {
        TargetEditView targetView;

        public TargetViewHolder(@NonNull View itemView) {
            super(itemView);
            targetView = itemView.findViewById(R.id.target_view);
        }
    }

    private final class TargetAdapter extends RecyclerView.Adapter<TargetViewHolder> {

        @NonNull
        @Override
        public TargetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_target_view, parent, false);
            return new TargetViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TargetViewHolder holder, int position) {
            TargetInfo targetInfo = mTargetInfoList.get(position);
            holder.targetView.setTargetInfo(targetInfo);
        }

        @Override
        public int getItemCount() {
            return mTargetInfoList.size();
        }
    }
}
