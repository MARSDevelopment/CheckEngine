package com.example.action_laptop.checkengine;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Action-Laptop on 10/15/2017.
 */

public class NotificationsTable{

    public static final String TABLE_NAME = "Notifications";

    //region String to be Used Only to Create the Default Repair Schedule Table Structure
    //TODO update with default values
    public static final String createColumnsString =
            //in SQLite Primary Key automatically auto increments
            "MileageThreshold INTEGER DEFAULT 500, " +
                    "LockScreen INTEGER DEFAULT 1, " +
                    "AndroidBar INTEGER DEFAULT 1, " +
                    "Frequency INTEGER DEFAULT 0";
    //endregion

    public enum TableColumns {
        MILEAGE_THRESHOLD_COLUMN("MileageThreshold"),
        FREQUENCY_COLUMN("Frequency"),
        ANDROID_BAR_COLUMN("AndroidBar"),
        LOCK_SCREEN_COLUMN("LockScreen");

        private String value;

        //one arg constructor
        //initializes each enum with the string hardcoded to it
        TableColumns(String value) {
            this.value = value;
        }

        //returns string associated with enum
        private String getValue() {
            return value;
        }

        @Override
        //override toString method to return enum string
        public String toString() {
            return this.getValue();
        }
    }

}
