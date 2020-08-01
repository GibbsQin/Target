package com.gibbs.target;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.gibbs.target.dao.MySQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TargetUtils {
    private static final String LOG_TAG = "TargetUtils";

    public static final int UNCOMPLETED = 0;
    public static final int COMPLETED = 1;
    private static int[] COLORS = {
            R.color.cornerColor1, R.color.cornerColor2, R.color.cornerColor3,
            R.color.cornerColor4, R.color.cornerColor5, R.color.cornerColor6,
            R.color.cornerColor7};

    public static String[] getNowDateAndTime() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        String dateString = formatter.format(currentTime);

        Log.d(LOG_TAG, "current time " + dateString);
        return dateString.split(" ");
    }

    public static @NonNull
    Intent getIntent(TargetInfo targetInfo) {
        Intent intent = new Intent();
        if (targetInfo == null) {
            return intent;
        }

        intent.putExtra(MySQLiteOpenHelper.ROW_NAME, targetInfo.getName());
        intent.putExtra(MySQLiteOpenHelper.ROW_CONTENT, targetInfo.getContent());
        intent.putExtra(MySQLiteOpenHelper.ROW_COMPLETE, targetInfo.getCompleted());
        intent.putExtra(MySQLiteOpenHelper.ROW_PROGRESS, targetInfo.getProgress());
        intent.putExtra(MySQLiteOpenHelper.ROW_MAX, targetInfo.getMax());

        return intent;
    }

    public static TargetInfo getTargetInfo(Intent data) {
        if (data == null) {
            return null;
        }
        String name = data.getStringExtra(MySQLiteOpenHelper.ROW_NAME);
        String content = data.getStringExtra(MySQLiteOpenHelper.ROW_CONTENT);
        int complete = data.getIntExtra(MySQLiteOpenHelper.ROW_COMPLETE, 0);
        int progress = data.getIntExtra(MySQLiteOpenHelper.ROW_PROGRESS, 0);
        int max = data.getIntExtra(MySQLiteOpenHelper.ROW_MAX, 30);

        TargetInfo targetInfo;
        targetInfo = new TargetInfo(name, content, complete, progress, max);

        return targetInfo;
    }

    public static ArrayList<TargetInfo> getTargetInfoList(Cursor cursor) {
        ArrayList<TargetInfo> targetInfoList = new ArrayList<>();
        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndex(MySQLiteOpenHelper.ROW_ID));
            String content = cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.ROW_CONTENT));
            int completed = cursor.getInt(cursor.getColumnIndex(MySQLiteOpenHelper.ROW_COMPLETE));
            String name = cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.ROW_NAME));
            int progress = cursor.getInt(cursor.getColumnIndex(MySQLiteOpenHelper.ROW_PROGRESS));
            int max = cursor.getInt(cursor.getColumnIndex(MySQLiteOpenHelper.ROW_MAX));
            TargetInfo targetInfo = new TargetInfo(id, name, content, completed, progress, max);

            targetInfoList.add(targetInfo);
        }

        return targetInfoList;
    }

    public static ContentValues getContentValues(TargetInfo targetInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MySQLiteOpenHelper.ROW_PROGRESS, targetInfo.getProgress());
        contentValues.put(MySQLiteOpenHelper.ROW_CONTENT, targetInfo.getContent());
        contentValues.put(MySQLiteOpenHelper.ROW_MAX, targetInfo.getMax());
        contentValues.put(MySQLiteOpenHelper.ROW_COMPLETE, targetInfo.getCompleted());
        contentValues.put(MySQLiteOpenHelper.ROW_NAME, targetInfo.getName());

        return contentValues;
    }

    public static Bundle getBundle(TargetInfo targetInfo) {
        Bundle bundle = new Bundle();
        if (targetInfo == null) {
            return null;
        }

        bundle.putString(MySQLiteOpenHelper.ROW_NAME, targetInfo.getName());
        bundle.putString(MySQLiteOpenHelper.ROW_CONTENT, targetInfo.getContent());
        bundle.putInt(MySQLiteOpenHelper.ROW_COMPLETE, targetInfo.getCompleted());
        bundle.putInt(MySQLiteOpenHelper.ROW_PROGRESS, targetInfo.getProgress());
        bundle.putInt(MySQLiteOpenHelper.ROW_MAX, targetInfo.getMax());

        return bundle;
    }

    public static TargetInfo getTargetInfo(Bundle data) {
        if (data == null) {
            return null;
        }
        String name = data.getString(MySQLiteOpenHelper.ROW_NAME);
        String content = data.getString(MySQLiteOpenHelper.ROW_CONTENT);
        int complete = data.getInt(MySQLiteOpenHelper.ROW_COMPLETE, 0);
        int progress = data.getInt(MySQLiteOpenHelper.ROW_PROGRESS, 0);
        int max = data.getInt(MySQLiteOpenHelper.ROW_MAX, 30);

        TargetInfo targetInfo;
        targetInfo = new TargetInfo(name, content, complete, progress, max);

        return targetInfo;
    }

    public static int getCurrentMonthDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        return calendar.get(Calendar.DATE);
    }

    public static int getRandomColor() {
        return (int) (System.currentTimeMillis() % 7);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static GradientDrawable createDrawable(Context context, int roundRadiusDip, int fillColor) {
        int roundRadius = dip2px(context, roundRadiusDip);
        int[] colors = {0xFFFFFF, 0xFFFFFF, 0xFFFFFF};//分别为开始颜色，中间夜色，结束颜色
        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);//创建drawable
        gd.setColor(fillColor);
        gd.setCornerRadius(roundRadius);

        return gd;
    }

    public static GradientDrawable createDrawable(Context context, int roundRadiusDip) {
        long timeStamp = System.nanoTime();
        int colorRes = context.getResources().getColor(COLORS[(int) (timeStamp % COLORS.length)]);
        return createDrawable(context, roundRadiusDip, colorRes);
    }

    public static GradientDrawable createDrawable(Context context) {
        return createDrawable(context, 10);
    }
}
