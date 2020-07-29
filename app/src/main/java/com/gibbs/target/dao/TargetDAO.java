package com.gibbs.target.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gibbs.target.TargetUtils;
import com.gibbs.target.TargetInfo;

import java.util.ArrayList;

public class TargetDAO {
    private static final String LOG_TAG = "TargetDAO";
    private static TargetDAO sTargetDAO;
    private Context context;
    private MySQLiteOpenHelper mySQLiteOpenHelper;
    private static final String SELECT_SQL =
            "select * from target";
    private static final String SELECT_INIT_SQL =
            "select * from init_target";

    private TargetDAO(Context context) {
        this.context = context;
        mySQLiteOpenHelper = new MySQLiteOpenHelper(context);
    }

    public synchronized static TargetDAO getInstance(Context context) {
        if (sTargetDAO == null) {
            sTargetDAO = new TargetDAO(context);
        }

        return sTargetDAO;
    }

    public long insert(TargetInfo targetInfo) {
        SQLiteDatabase db = null;
        Log.d(LOG_TAG, targetInfo.toString());
        long rowId = -1;

        try {
            ContentValues contentValues = TargetUtils.getContentValues(targetInfo);

            db = mySQLiteOpenHelper.getWritableDatabase();
            db.beginTransaction();
            rowId = db.insertOrThrow(MySQLiteOpenHelper.TABLE_MSG, null, contentValues);
            db.setTransactionSuccessful();
        } catch (SQLiteConstraintException e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return rowId;
    }

    public void delete(TargetInfo targetInfo) {
        SQLiteDatabase db = null;
        Log.d(LOG_TAG, targetInfo.toString());

        try {
            db = mySQLiteOpenHelper.getWritableDatabase();
            db.beginTransaction();
            String rowId = String.valueOf(targetInfo.getRowId());
            db.delete(MySQLiteOpenHelper.TABLE_MSG, "_id=?", new String[]{rowId});
            db.setTransactionSuccessful();
        } catch (SQLiteConstraintException e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
    }

    public void update(TargetInfo targetInfo) {
        SQLiteDatabase db = null;

        try {
            db = mySQLiteOpenHelper.getWritableDatabase();
            db.beginTransaction();
            ContentValues contentValues = TargetUtils.getContentValues(targetInfo);
            db.update(MySQLiteOpenHelper.TABLE_MSG, contentValues, "_id=?",
                    new String[]{String.valueOf(targetInfo.getRowId())});
            db.setTransactionSuccessful();
        } catch (SQLiteConstraintException e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
    }

    public ArrayList<TargetInfo> selectAll() {
        SQLiteDatabase db = mySQLiteOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_SQL, null);

        return TargetUtils.getTargetInfoList(cursor);
    }

    public ArrayList<TargetInfo> selectInitAll() {
        SQLiteDatabase db = mySQLiteOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_INIT_SQL, null);

        return TargetUtils.getTargetInfoList(cursor);
    }
}
