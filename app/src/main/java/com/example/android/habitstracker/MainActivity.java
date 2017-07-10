package com.example.android.habitstracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.android.habitstracker.data.HabitContract.HabitEntry;
import com.example.android.habitstracker.data.HabitDbHelper;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create HabitDbHelper instance
        HabitDbHelper mDbHelper = new HabitDbHelper(this);
        // Insert habits records
        insertHabit(mDbHelper);
        // Read and display habits records
        displayHabit(mDbHelper);
    }

    /* Helper method to display habits data from the database.*/
    private void displayHabit(HabitDbHelper mDbHelper) {
        // Find the TextView which will be populated
        TextView displayView = (TextView) findViewById(R.id.text_view);
        // Call helper method to read habits data
        Cursor cursor = readData(mDbHelper);
        try {
            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_NAME);
            int descColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_DESC);
            int durationColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_DURATION);
            int unitColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_DURATION_UNIT);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentDesc = cursor.getString(descColumnIndex);
                int currentDuration = cursor.getInt(durationColumnIndex);
                String currentUnit = cursor.getString(unitColumnIndex);

                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" +
                        currentID + " - " +
                        currentName + " - " +
                        currentDesc + " - " +
                        currentDuration + " - " +
                        currentUnit));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    /* Helper method to read habits data into the database.*/
    private Cursor readData(HabitDbHelper mDbHelper) {
        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_HABIT_NAME,
                HabitEntry.COLUMN_HABIT_DESC,
                HabitEntry.COLUMN_HABIT_DURATION,
                HabitEntry.COLUMN_HABIT_DURATION_UNIT};

        // Perform a query on the habits table
        return db.query(
                HabitEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);
    }

    /* Helper method to insert hardcoded habits data into the database.*/
    private void insertHabit(HabitDbHelper mDbHelper) {
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_HABIT_NAME, "Gym Workout");
        values.put(HabitEntry.COLUMN_HABIT_DESC, "Work-out session");
        values.put(HabitEntry.COLUMN_HABIT_DURATION, "30");
        values.put(HabitEntry.COLUMN_HABIT_DURATION_UNIT, HabitEntry.DURATION_MIN);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);

        Log.v("CatalogActivity", "New row ID " + newRowId);
    }

}
