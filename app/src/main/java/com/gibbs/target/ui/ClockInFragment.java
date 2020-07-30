package com.gibbs.target.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gibbs.target.R;
import com.gibbs.target.TargetInfo;
import com.gibbs.target.dao.TargetDAO;
import com.gibbs.target.view.TargetView;

import java.util.ArrayList;
import java.util.Arrays;


public class ClockInFragment extends Fragment {
    private static final String TAG = "ClockInFragment";

    private ArrayList<TargetInfo> mTargetInfoList = new ArrayList<>();

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
        mTargetInfoList = TargetDAO.getInstance(getActivity()).selectAll();
        Log.i(TAG, "mTargetInfoList = " + Arrays.toString(mTargetInfoList.toArray()));
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clock_in, container, false);
        RecyclerView recyclerViewClock = view.findViewById(R.id.rv_clock);
        recyclerViewClock.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerViewClock.setAdapter(new TargetAdapter());

        return view;
    }

    private static final class TargetViewHolder extends RecyclerView.ViewHolder {
        TargetView targetView;

        public TargetViewHolder(@NonNull View itemView) {
            super(itemView);
            targetView = itemView.findViewById(R.id.target_view);
        }
    }

    private final class TargetAdapter extends RecyclerView.Adapter<TargetViewHolder> {

        @NonNull
        @Override
        public TargetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_clock_view, parent, false);
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
