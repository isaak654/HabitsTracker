package com.example.android.habitstracker.data;

import android.provider.BaseColumns;

public final class HabitContract {

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private HabitContract() {}

    /* Inner class that defines the table contents */
    public static class HabitEntry implements BaseColumns {

        /** Name of database table for habits */
        public static final String TABLE_NAME = "habits";

        /* Unique ID - Type: INTEGER */
        public static final String _ID = BaseColumns._ID;
        /* Habit name - Type: TEXT */
        public static final String COLUMN_HABIT_NAME = "name";
        /* Habit description - Type: TEXT */
        public static final String COLUMN_HABIT_DESC = "description";
        /* Habit duration - Type: INTEGER */
        public static final String COLUMN_HABIT_DURATION = "duration";
        /* Habit duration unit - Type: TEXT */
        public static final String COLUMN_HABIT_DURATION_UNIT = "duration_unit";

        /* Possible values for the unit of duration. */
        public static final String DURATION_SEC = "sec";
        public static final String DURATION_MIN = "min";
        public static final String DURATION_HOUR = "hour";

        /* Returns whether or not the given duration is valid */
        public static boolean isValidDuration(String duration) {
            return duration.equalsIgnoreCase(DURATION_SEC) || duration.equalsIgnoreCase(DURATION_MIN)
                    || duration.equalsIgnoreCase(DURATION_HOUR);
        }

    }

}
