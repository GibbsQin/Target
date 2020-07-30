package com.gibbs.target;

import android.app.Application;
import android.util.Log;

import com.gibbs.target.dao.TargetDAO;

import java.util.ArrayList;

public class TargetApplication extends Application {
    private static final String LOG_TAG = "TargetApplication";
    private ArrayList<TargetInfo> mInitTargetInfoList;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "onCreate");
        mInitTargetInfoList = TargetDAO.getInstance(this).selectInitAll();
    }

    public ArrayList<TargetInfo> getInitTargetInfoList() {
        return mInitTargetInfoList;
    }
}
