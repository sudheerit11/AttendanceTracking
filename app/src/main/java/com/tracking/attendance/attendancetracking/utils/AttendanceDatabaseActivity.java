package com.tracking.attendance.attendancetracking.utils;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.tracking.attendance.attendancetracking.R;
import com.tracking.attendance.attendancetracking.dataBaseHelper.Schedule;
import com.tracking.attendance.attendancetracking.dataBaseHelper.SchedulesDataSource;

import org.w3c.dom.Comment;

import java.util.List;
import java.util.Random;

/**
 * Created by sud on 6/12/15.
 */
public class AttendanceDatabaseActivity extends ListActivity {
    private SchedulesDataSource datasource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tracking);

        datasource = new SchedulesDataSource(this);
        datasource.open();

        List<Schedule> values = datasource.getAllSchedules();
        Log.i("Got data so far", values.toString());
        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Schedule> adapter = new ArrayAdapter<Schedule>(this,android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    // Will be called via the onClick attribute
    // of the buttons in main.xml
    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Schedule> adapter = (ArrayAdapter<Schedule>) getListAdapter();
        Schedule schedule = null;
        switch (view.getId()) {
            case R.id.add:
                String[] comments = new String[] { "Cool", "Very", "Hate" };
                int nextInt = new Random().nextInt(3);
                // save the new comment to the database
                schedule = datasource.createSchedule(comments[nextInt]);
                adapter.add(schedule);
                break;
            case R.id.delete:
                if (getListAdapter().getCount() > 0) {
                    schedule = (Schedule) getListAdapter().getItem(0);
                    datasource.deleteSchedule(schedule);
                    adapter.remove(schedule);
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

}

