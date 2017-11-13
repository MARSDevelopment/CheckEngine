package com.example.action_laptop.checkengine;

/**
 * Created by Action-Laptop on 10/17/2017.
 */

public class GarageTable {

    public static final String TABLE_NAME = "Garage";

    //region String to be Used Only to Create the Default Repair Schedule Table Structure
    //TODO update with default values
    public static final String createColumnsString =
            //in SQLite Primary Key automatically auto increments
            "ID INTEGER PRIMARY KEY, " +
                    "Name TEXT, " +
                    "Make TEXT, " +
                    "Model TEXT, " +
                    "Year INTEGER, " +
                    "CurrentMileage INTEGER DEFAULT 0, " +
                    "InUse INTEGER DEFAULT 0";
    //endregion

    public enum TableColumns {
        ID_COLUMN("ID"),
        NAME_COLUMN("Name"),
        MAKE_COLUMN("Make"),
        MODEL_COLUMN("Model"),
        YEAR_COLUMN("Year"),
        CURRENT_MILEAGE_COLUMN("CurrentMileage"),
        IN_USE_COLUMN("InUse");

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
