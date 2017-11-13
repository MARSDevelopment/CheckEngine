package com.example.action_laptop.checkengine;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Action-Laptop on 10/17/2017.
 */

public class GlobalValues {

    private SharedPreferences sharedPreferences;
    public static final String CHECK_ENGINE = "CheckEngine";

    //region Constructors
    public GlobalValues(Context context){
        sharedPreferences = context.getSharedPreferences(GlobalValues.CHECK_ENGINE, Context.MODE_PRIVATE);
    }
    //endregion

    //set global app variable
    public void Set(String key, String value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    //gets global app variable. returns blank string if it doesn't exist
    public String Get(String key){
        return sharedPreferences.getString(key, "");
    }

    public enum CarInfo {
        CAR_NAME("CarName"),
        CURRENT_MILEAGE("CurrentMileage");

        private String value;

        //one arg constructor
        //initializes each enum with the string hardcoded to it
        CarInfo(String value) {
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
