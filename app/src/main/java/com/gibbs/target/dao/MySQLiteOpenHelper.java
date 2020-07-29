package com.gibbs.target.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.alibaba.fastjson.JSON;
import com.gibbs.target.TargetUtils;
import com.gibbs.target.R;
import com.gibbs.target.TargetInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String JSON_FILE_NAME = "init_target_info.json";
    private static final int INIT_TARGET_SUM = 5;

    public static final String DB_MESSAGE = "target.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_MSG = "target";
    public static final String TABLE_INIT = "init_target";
    public static final String ROW_ID = "_id";
    public static final String ROW_ICON = "icon";
    public static final String ROW_NAME = "name";
    public static final String ROW_CONTENT = "content";
    public static final String ROW_COMPLETE = "completed";
    public static final String ROW_PROGRESS = "progress";
    public static final String ROW_BGCOLOR = "bgcolor";
    public static final String ROW_MAX = "max";

    private static final String CREATE_TARGET_TABLE =
            "create table target (_id integer PRIMARY KEY autoincrement," +
                    "icon integer," +
                    "name text," +
                    "content text," +
                    "completed boolean," +
                    "progress integer," +
                    "max integer," +
                    "bgcolor int);";

    private static final String CREATE_INIT_TABLE =
            "create table init_target (_id integer PRIMARY KEY autoincrement," +
                    "icon integer," +
                    "name text," +
                    "content text," +
                    "completed boolean," +
                    "progress integer," +
                    "max integer," +
                    "bgcolor int);";

    public MySQLiteOpenHelper(Context context) {
        this(context, DB_MESSAGE, null, DB_VERSION);
    }

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        this(context, name, factory, version, null);
    }

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TARGET_TABLE);
        db.execSQL(CREATE_INIT_TABLE);

        List<TargetInfo> list = getInitTargetList();
        for (TargetInfo info : list) {
            info.setIcon(R.mipmap.default_target_icon);
            info.setMax(TargetUtils.getCurrentMonthDay());
            insert(db,info,TABLE_INIT);
        }

        for (int i=0; i<list.size() && i<INIT_TARGET_SUM; i++) {
            TargetInfo info = list.get(i);
            insert(db,info,TABLE_MSG);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void insert(SQLiteDatabase db, TargetInfo targetInfo, String table) {
        try {
            ContentValues contentValues = TargetUtils.getContentValues(targetInfo);

            db.beginTransaction();
            db.insertOrThrow(table, null, contentValues);
            db.setTransactionSuccessful();
        } catch (SQLiteConstraintException e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.endTransaction();
            }
        }
    }

    private List<TargetInfo> getInitTargetList() {
        String jsonString = readStringFromFile(JSON_FILE_NAME);

        List<TargetInfo> targetInfoList;
        targetInfoList = (List<TargetInfo>) JSON.parseArray(jsonString, TargetInfo.class);

        return targetInfoList;
    }

    private String readStringFromFile(String filePath) {
        String res = "";
        try {
            InputStream inputStream = context.getAssets().open(filePath);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                res += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }
}
