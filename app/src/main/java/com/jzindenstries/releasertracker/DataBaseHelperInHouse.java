package com.jzindenstries.releasertracker;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelperInHouse extends SQLiteOpenHelper{

    public static final String RELEASER_TABLE = "RELEASER_TABLE";
    public static final String COLUMN_RELEASER_TYPE = "RELEASER_TYPE";
    public static final String COLUMN_RELEASER_ID = "RELEASER_ID";
    public static final String COLUMN_RELEASER_CHECKED = "RELEASER_CHECKED";
    public static final String COLUMN_RELEASER_NAME = "RELEASER_NAME";
    public static final String COLUMN_RELEASER_DATE = "RELEASER_DATE";

    public DataBaseHelperInHouse(@Nullable Context context) {
        super(context, "releaserData.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + RELEASER_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_RELEASER_TYPE + " TEXT,  " + COLUMN_RELEASER_ID + " INT," + COLUMN_RELEASER_CHECKED + " BOOL, " + COLUMN_RELEASER_NAME + " TEXT, " + COLUMN_RELEASER_DATE + " TEXT)";
        db.execSQL(createTableStatement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(GoToHouse releaserClass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_RELEASER_TYPE, releaserClass.getType());
        cv.put(COLUMN_RELEASER_ID, releaserClass.getId());
        cv.put(COLUMN_RELEASER_CHECKED, releaserClass.isInspected());
        cv.put(COLUMN_RELEASER_NAME, releaserClass.getName());
        cv.put(COLUMN_RELEASER_DATE, releaserClass.getDate());

        long insert = db.insert(RELEASER_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteTitle(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(RELEASER_TABLE, COLUMN_RELEASER_ID + "=?", new String[]{id}) > 0;
    }

    public List<GoToHouse> getEveryone(){
        List<GoToHouse> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + RELEASER_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                String type = cursor.getString(1);
                int id = cursor.getInt(2);
                boolean checked = cursor.getInt(3) == 1 ? true:false;
                String name = cursor.getString(4);
                String date = cursor.getString(5);

                GoToHouse newReleaser = new GoToHouse(type,id, date, name, checked);
                returnList.add(newReleaser);


            }while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return returnList;
    }
}