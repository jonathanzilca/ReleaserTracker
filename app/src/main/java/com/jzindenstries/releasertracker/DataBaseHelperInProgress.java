package com.jzindenstries.releasertracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelperInProgress extends SQLiteOpenHelper {
    public static final String PROGRESS_TABLE = "PROGESS_TABLE";
    public static final String COLUMN_RELEASER_TYPE = "RELEASER_TYPE";
    public static final String COLUMN_RELEASER_ID = "RELEASER_ID";
    public static final String COLUMN_ACTION_NAME = "ACTION_NAME";
    public static final String COLUMN_RETURN_DATE = "RETURN_DATE";

    public DataBaseHelperInProgress(@Nullable Context context) {
        super(context, "inProgressData.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + PROGRESS_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_RELEASER_TYPE + " TEXT,  " + COLUMN_RELEASER_ID + " INT," + COLUMN_ACTION_NAME + " TEXT, " + " TEXT, " + COLUMN_RETURN_DATE + " TEXT)";
        db.execSQL(createTableStatement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(GoToProgress releaserClass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_RELEASER_TYPE, releaserClass.getType());
        cv.put(COLUMN_RELEASER_ID, releaserClass.getId());
        cv.put(COLUMN_ACTION_NAME, releaserClass.getActionName());
        cv.put(COLUMN_RETURN_DATE, releaserClass.getReturnDate());

        long insert = db.insert(PROGRESS_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteTitle(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(PROGRESS_TABLE, COLUMN_RELEASER_ID + "=?", new String[]{id}) > 0;
    }

    public List<GoToProgress> getEveryone(){
        List<GoToProgress> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + PROGRESS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                String type = cursor.getString(1);
                int id = cursor.getInt(2);
                String actionName = cursor.getString(3);
                String returnDate = cursor.getString(4);

                GoToProgress newReleaser = new GoToProgress(type,id, actionName, returnDate);
                returnList.add(newReleaser);


            }while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return returnList;
    }

}