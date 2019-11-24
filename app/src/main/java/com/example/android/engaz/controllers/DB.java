package com.example.android.engaz.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.android.engaz.activities.tracks;
import com.example.android.engaz.models.task;
import com.example.android.engaz.models.track;

import java.util.ArrayList;

public class DB extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "engaz";

    public static final String TABLE1_NAME = "track";
    public static final String TABLE1_COLUMN_ID = "id";
    public static final String TABLE1_COLUMN_CONTENT = "content";

    public static final String TABLE2_NAME = "task";
    public static final String TABLE2_COLUMN_ID = "id";
    public static final String TABLE2_COLUMN_TID = "tid";
    public static final String TABLE2_COLUMN_CONTENT = "content";
    public static final String TABLE2_COLUMN_STATUE = "statue";

    public static final String CREATE_TABLE1 = "create table " + TABLE1_NAME +
            " (" + TABLE1_COLUMN_ID + " integer primary key," +
            TABLE1_COLUMN_CONTENT + " text" + ")";

    public static final String CREATE_TABLE2 = "create table " + TABLE2_NAME +
            " (" + TABLE2_COLUMN_ID + " integer primary key," +
            TABLE2_COLUMN_CONTENT + " text," +
            TABLE2_COLUMN_STATUE + " integer," +
            TABLE2_COLUMN_TID + " integer" + ")";
    public Context context;

    public DB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE1);
        sqLiteDatabase.execSQL(CREATE_TABLE2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public int insertTrack(track t) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE1_COLUMN_CONTENT, t.getContent());
        int id = (int) db.insert(TABLE1_NAME, null, contentValues);
        return id;
    }

    public int insertTask(task t) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE2_COLUMN_TID, t.getIdTrack());
        contentValues.put(TABLE2_COLUMN_CONTENT, t.getContent());
        contentValues.put(TABLE2_COLUMN_STATUE, t.isStatue());
        int id = (int) db.insert(TABLE2_NAME, null, contentValues);
        db.close();
        return id;
    }

    public ArrayList<track> selectTracks() {
        ArrayList<track> tracks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE1_NAME, null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            tracks.add(new track(res.getInt(res.getColumnIndex(TABLE1_COLUMN_ID)),
                    res.getString(res.getColumnIndex(TABLE1_COLUMN_CONTENT))));
            res.moveToNext();
        }
        db.close();
        return tracks;
    }

    public ArrayList<task> selectTasks(int id) {
        ArrayList<task> tasks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE2_NAME +
                " where " + TABLE2_COLUMN_TID + "=" + id, null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            tasks.add(new task(res.getInt(res.getColumnIndex(TABLE2_COLUMN_ID)),
                    res.getInt(res.getColumnIndex(TABLE2_COLUMN_TID)),
                    res.getString(res.getColumnIndex(TABLE2_COLUMN_CONTENT)),
                    res.getInt(res.getColumnIndex(TABLE2_COLUMN_STATUE)))
            );
            res.moveToNext();
        }
        db.close();
        return tasks;
    }

    public void updateTask(task t) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE2_COLUMN_STATUE, t.isStatue());
        db.update(TABLE2_NAME, contentValues, TABLE2_COLUMN_ID + "=" + t.getId(), null);
        db.close();
    }
    public void updateTaskTitle(task t) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE2_COLUMN_CONTENT, t.getContent());
        db.update(TABLE2_NAME, contentValues, TABLE2_COLUMN_ID + "=" + t.getId(), null);
        db.close();
    }
    public void deleteTask(task t) {
        SQLiteDatabase db = this.getWritableDatabase();
        String s = "delete from " + TABLE2_NAME + " where " + TABLE2_COLUMN_ID + "= " + t.getId();
        db.execSQL(s);
        db.close();
    }

    public void deleteTrack(track t) {
        SQLiteDatabase db = this.getWritableDatabase();
        String s = "delete from " + TABLE1_NAME + " where " + TABLE1_COLUMN_ID + "= " + t.getId();
        db.execSQL(s);
        db.close();
        deleteTask(t.getId());
    }

    private void deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String s = "delete from " + TABLE2_NAME + " where " + TABLE2_COLUMN_TID + "= " + id;
        db.execSQL(s);
        db.close();
    }
}
