package com.example.action_laptop.checkengine;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.Map;

/**
 * Created by Action-Laptop on 10/7/2017.
 */

public class RepairScheduleTable extends CarValuesDBHandler{
    public static final String TABLE_NAME = "RepairSchedule";

    //region String to be Used Only to Create the Default Repair Schedule Table Structure
    //TODO update with default repair values
    public static final String createColumnsString =
            //in SQLite Primary Key automatically auto increments
            "ID INTEGER PRIMARY KEY, " +
                    "Name TEXT, " +
                    "AirFilter INTEGER DEFAULT 25000, " +
                    "Alignment INTEGER DEFAULT 25000, " +
                    "Alternator INTEGER DEFAULT 25000, " +
                    "AutomaticTransmissionFluid INTEGER DEFAULT 25000, " +
                    "BrakeFluid INTEGER DEFAULT 25000, " +
                    "Coolant INTEGER DEFAULT 25000, " +
                    "DiscBrakes INTEGER DEFAULT 25000, " +
                    "DrumBrakes INTEGER DEFAULT 25000, " +
                    "ManualTransmissionFluid INTEGER DEFAULT 25000, " +
                    "Oil INTEGER DEFAULT 25000, " +
                    "PowerSteeringFluid INTEGER DEFAULT 25000, " +
                    "RadiatorFluid INTEGER DEFAULT 25000, " +
                    "Rotation INTEGER DEFAULT 25000, " +
                    "Rotors INTEGER DEFAULT 25000, " +
                    "Solenoid INTEGER DEFAULT 25000, " +
                    "Starter INTEGER DEFAULT 25000, " +
                    "TimingBelts INTEGER DEFAULT 25000, " +
                    "Tires INTEGER DEFAULT 25000";
    //endregion

    public enum TableColumns {
        ID_COLUMN("ID"),
        NAME_COLUMN("Name"),
        AIR_FILTER_COLUMN("AirFilter"),
        ALIGNMENT_COLUMN("Alignment"),
        ALTERNATOR_COLUMN("Alternator"),
        AUTOMATIC_TRANSMISSION_FLUID_COLUMN("AutomaticTransmissionFluid"),
        BRAKE_FLUID_COLUMN("BrakeFluid"),
        COOLANT_COLUMN("Coolant"),
        DISC_BRAKES_COLUMN("DiscBrakes"),
        DRUM_BRAKES_COLUMN("DrumBrakes"),
        MANUAL_TRANSMISSION_FLUID_COLUMN("ManualTransmissionFluid"),
        OIL_COLUMN("Oil"),
        POWER_STEERING_FLUID_COLUMN("PowerSteeringFluid"),
        RADIATOR_FLUID_COLUMN("RadiatorFluid"),
        ROTATION_COLUMN("Rotation"),
        ROTORS_COLUMN("Rotors"),
        SOLENOID_COLUMN("Solenoid"),
        STARTER_COLUMN("Starter"),
        TIMING_BELTS_COLUMN("TimingBelts"),
        TIRES_COLUMN("Tires");

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

    //region Constructor for super
    public RepairScheduleTable(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, factory);
    }
    //endregion

    public <T extends Enum<T>> void AddTableRow(@NonNull String tableName, @NonNull Class<T> tableColumnsEnum, @NonNull Map<Enum, Integer> valuesToAdd) {
        super.AddTableRow(tableName, tableColumnsEnum, valuesToAdd);
    }
}
