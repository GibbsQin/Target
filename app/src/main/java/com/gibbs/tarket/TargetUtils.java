package com.gibbs.tarket;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.gibbs.tarket.dao.MySQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TargetUtils {
    private static final String LOG_TAG = "TargetUtils";

    public static final int UNCOMPLETED = 0;
    public static final int COMPLETED = 1;
    private static int lastColor = 0;
    private static int[] COLORS = {
            R.drawable.corner_shape1,R.drawable.corner_shape2,R.drawable.corner_shape3,
            R.drawable.corner_shape4,R.drawable.corner_shape5,R.drawable.corner_shape6,
            R.drawable.corner_shape7};

    public static String[] getNowDateAndTime() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);

        Log.d(LOG_TAG, "current time " + dateString);
        return dateString.split(" ");
    }

    public static Intent getIntent(TargetInfo targetInfo) {
        Intent intent = new Intent();
        if (targetInfo == null) {
            return null;
        }

        intent.putExtra(MySQLiteOpenHelper.ROW_ICON, targetInfo.icon);
        intent.putExtra(MySQLiteOpenHelper.ROW_NAME, targetInfo.name);
        intent.putExtra(MySQLiteOpenHelper.ROW_CONTENT, targetInfo.content);
        intent.putExtra(MySQLiteOpenHelper.ROW_COMPLETE,targetInfo.completed);
        intent.putExtra(MySQLiteOpenHelper.ROW_PROGRESS,targetInfo.progress);
        intent.putExtra(MySQLiteOpenHelper.ROW_MAX,targetInfo.max);
        intent.putExtra(MySQLiteOpenHelper.ROW_BGCOLOR,targetInfo.bgColor);

        return intent;
    }

    public static TargetInfo getTargetInfo(Intent data) {
        if (data == null) {
            return null;
        }
        int icon = data.getIntExtra(MySQLiteOpenHelper.ROW_ICON, R.mipmap.default_target_icon);
        String name = data.getStringExtra(MySQLiteOpenHelper.ROW_NAME);
        String content = data.getStringExtra(MySQLiteOpenHelper.ROW_CONTENT);
        int complete = data.getIntExtra(MySQLiteOpenHelper.ROW_COMPLETE,0);
        int progress = data.getIntExtra(MySQLiteOpenHelper.ROW_PROGRESS, 0);
        int max = data.getIntExtra(MySQLiteOpenHelper.ROW_MAX,30);
        int bgColor = data.getIntExtra(MySQLiteOpenHelper.ROW_BGCOLOR,0);

        TargetInfo targetInfo = new TargetInfo(icon,name,content,complete,progress,max,bgColor);

        return targetInfo;
    }

    public static ArrayList<TargetInfo> getTargetInfoList(Cursor cursor) {
        ArrayList<TargetInfo> targetInfoList = new ArrayList<>();
        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndex(MySQLiteOpenHelper.ROW_ID));
            String content = cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.ROW_CONTENT));
            int completed = cursor.getInt(cursor.getColumnIndex(MySQLiteOpenHelper.ROW_COMPLETE));
            String name = cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.ROW_NAME));
            int icon = cursor.getInt(cursor.getColumnIndex(MySQLiteOpenHelper.ROW_ICON));
            int progress = cursor.getInt(cursor.getColumnIndex(MySQLiteOpenHelper.ROW_PROGRESS));
            int max = cursor.getInt(cursor.getColumnIndex(MySQLiteOpenHelper.ROW_MAX));
            int bgColor = cursor.getInt(cursor.getColumnIndex(MySQLiteOpenHelper.ROW_BGCOLOR));
            TargetInfo targetInfo = new TargetInfo(id,icon,name,content,completed,progress,max,bgColor);

            targetInfoList.add(targetInfo);
        }

        return targetInfoList;
    }

    public static ContentValues getContentValues(TargetInfo targetInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MySQLiteOpenHelper.ROW_PROGRESS, targetInfo.progress);
        contentValues.put(MySQLiteOpenHelper.ROW_CONTENT, targetInfo.content);
        contentValues.put(MySQLiteOpenHelper.ROW_ICON, targetInfo.icon);
        contentValues.put(MySQLiteOpenHelper.ROW_MAX, targetInfo.max);
        contentValues.put(MySQLiteOpenHelper.ROW_COMPLETE, targetInfo.completed);
        contentValues.put(MySQLiteOpenHelper.ROW_BGCOLOR, targetInfo.bgColor);
        contentValues.put(MySQLiteOpenHelper.ROW_NAME, targetInfo.name);

        return contentValues;
    }

    public static Bundle getBundle(TargetInfo targetInfo) {
        Bundle bundle = new Bundle();
        if (targetInfo == null) {
            return null;
        }

        bundle.putInt(MySQLiteOpenHelper.ROW_ICON, targetInfo.icon);
        bundle.putString(MySQLiteOpenHelper.ROW_NAME, targetInfo.name);
        bundle.putString(MySQLiteOpenHelper.ROW_CONTENT, targetInfo.content);
        bundle.putInt(MySQLiteOpenHelper.ROW_COMPLETE,targetInfo.completed);
        bundle.putInt(MySQLiteOpenHelper.ROW_PROGRESS,targetInfo.progress);
        bundle.putInt(MySQLiteOpenHelper.ROW_MAX,targetInfo.max);
        bundle.putInt(MySQLiteOpenHelper.ROW_BGCOLOR,targetInfo.bgColor);

        return bundle;
    }

    public static TargetInfo getTargetInfo(Bundle data) {
        if (data == null) {
            return null;
        }
        int icon = data.getInt(MySQLiteOpenHelper.ROW_ICON, R.mipmap.default_target_icon);
        String name = data.getString(MySQLiteOpenHelper.ROW_NAME);
        String content = data.getString(MySQLiteOpenHelper.ROW_CONTENT);
        int complete = data.getInt(MySQLiteOpenHelper.ROW_COMPLETE,0);
        int progress = data.getInt(MySQLiteOpenHelper.ROW_PROGRESS, 0);
        int max = data.getInt(MySQLiteOpenHelper.ROW_MAX,30);
        int bgColor = data.getInt(MySQLiteOpenHelper.ROW_BGCOLOR,0);

        TargetInfo targetInfo = new TargetInfo(icon,name,content,complete,progress,max,bgColor);

        return targetInfo;
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();

        return width;
    }

    public static int getCurrentMonthDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        int maxDate = calendar.get(Calendar.DATE);
        return maxDate;
    }

    public static int getRandomColor() {
        if (lastColor >= COLORS.length) {
            lastColor = 0;
        }
        lastColor++;
        Log.d(LOG_TAG, "current color is " + lastColor%7);
        return lastColor%7;
    }

    public static int getRandomColorRes(int pos) {
        if (pos < COLORS.length && pos >= 0) {
            return COLORS[pos];
        }

        return R.drawable.corner_shape;
    }

    public static void saveLastColor(Context context) {
        SharedPreferences sh = context.getSharedPreferences("bgcolor", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sh.edit();
        editor.putInt("color", lastColor);
        editor.commit();
    }

    public static void setLastColor(int color) {
        lastColor = color;
    }

    public static int getLastColor(Context context) {
        SharedPreferences sh = context.getSharedPreferences("bgcolor", Context.MODE_PRIVATE);

        return sh.getInt("color",0);
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
}
