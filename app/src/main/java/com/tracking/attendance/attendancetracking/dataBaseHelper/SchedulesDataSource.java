package com.tracking.attendance.attendancetracking.dataBaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sud on 6/12/15.
 */
public class SchedulesDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_SUBJECT,
            MySQLiteHelper.COLUMN_DAY,
            MySQLiteHelper.COLUMN_START_TIME,
            MySQLiteHelper.COLUMN_END_TIME
    };

    public SchedulesDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Schedule createSchedule(String schedule) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_SUBJECT, schedule);
        values.put(MySQLiteHelper.COLUMN_DAY, schedule);
        values.put(MySQLiteHelper.COLUMN_START_TIME, schedule);
        values.put(MySQLiteHelper.COLUMN_END_TIME, schedule);
        Log.i("Testing",values.toString());
        long insertId = database.insert(MySQLiteHelper.TABLE_SCHEDULES, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_SCHEDULES,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        Log.i("PreCursor",cursor.toString());
        cursor.moveToFirst();
        Log.i("Cursor",cursor.toString());
        Schedule newSchedule = cursorToSchedule(cursor);
        cursor.close();
        return newSchedule;
    }

    public void deleteSchedule(Schedule schedule) {
        long id = schedule.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_SCHEDULES, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Schedule> getAllSchedules() {
        List<Schedule> schedules = new ArrayList<Schedule>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_SCHEDULES,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Schedule schedule = cursorToSchedule(cursor);
            schedules.add(schedule);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return schedules;
    }

    private Schedule cursorToSchedule(Cursor cursor) {
        Schedule schedule = new Schedule();
        schedule.setId(cursor.getLong(0));
        schedule.setSubject(cursor.getString(1));
        schedule.setDay(cursor.getString(2));
        schedule.setStart_time(cursor.getString(3));
        schedule.setEnd_time(cursor.getString(4));
        return schedule;
    }

}
