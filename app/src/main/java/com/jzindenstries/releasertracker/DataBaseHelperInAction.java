package com.jzindenstries.releasertracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelperInAction extends SQLiteOpenHelper {
    public static final String ACTION_TABLE = "ACTION_TABLE";
    public static final String COLUMN_RELEASER_TYPE = "RELEASER_TYPE";
    public static final String COLUMN_RELEASER_ID = "RELEASER_ID";
    public static final String COLUMN_ACTION_NAME = "ACTION_NAME";
    public static final String COLUMN_TAKER_NAME = "TAKER_NAME";
    public static final String COLUMN_TAKER_DATE = "TAKER_DATE";
    public static final String COLUMN_INSPECTOR_NAME = "INSPECTOR_NAME";
    public static final String COLUMN_INSPECTOR_DATE = "INSPECTOR_DATE";

    public DataBaseHelperInAction(@Nullable Context context) {
        super(context, "inActionData.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + ACTION_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_RELEASER_TYPE + " TEXT,  " + COLUMN_RELEASER_ID + " INT," + COLUMN_ACTION_NAME + " TEXT, " + COLUMN_TAKER_NAME + " TEXT, " + COLUMN_TAKER_DATE + " TEXT, " + COLUMN_INSPECTOR_NAME + " TEXT, " + COLUMN_INSPECTOR_DATE + " TEXT)";
        db.execSQL(createTableStatement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(GoToAction releaserClass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_RELEASER_TYPE, releaserClass.getType());
        cv.put(COLUMN_RELEASER_ID, releaserClass.getId());
        cv.put(COLUMN_ACTION_NAME, releaserClass.getActionName());
        cv.put(COLUMN_TAKER_NAME, releaserClass.getTakerName());
        cv.put(COLUMN_TAKER_DATE, releaserClass.getTakerDate());
        cv.put(COLUMN_INSPECTOR_NAME, releaserClass.getInspectorName());
        cv.put(COLUMN_INSPECTOR_DATE, releaserClass.getInspectorDate());

        long insert = db.insert(ACTION_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteTitle(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(ACTION_TABLE, COLUMN_RELEASER_ID + "=?", new String[]{id}) > 0;
    }

    public List<GoToAction> getEveryone(){
        List<GoToAction> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + ACTION_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                String type = cursor.getString(1);
                int id = cursor.getInt(2);
                String actionName = cursor.getString(3);
                String takerName = cursor.getString(4);
                String takerDate = cursor.getString(5);
                String inspectorName = cursor.getString(6);
                String inspectorDate = cursor.getString(7);

                GoToAction newReleaser = new GoToAction(type,id, actionName, takerName, takerDate, inspectorName, inspectorDate);
                returnList.add(newReleaser);


            }while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return returnList;
    }

}