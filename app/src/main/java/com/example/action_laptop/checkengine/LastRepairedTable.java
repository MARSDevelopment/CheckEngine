package com.example.action_laptop.checkengine;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.Map;

/**
 * Created by Action-Laptop on 10/7/2017.
 */

public class LastRepairedTable extends CarValuesDBHandler implements ICarValuesTable {
    public static final String TABLE_NAME = "LastRepaired";

    //region String to be Used Only to Create the Default Repair Schedule Table Structure
    //TODO update with default repair values
    public static final String createColumnsString =
            //in SQLite Primary Key automatically auto increments
            "ID INTEGER PRIMARY KEY, " +
                    "RelatedRepairScheduleName TEXT, " +
                    "AirFilter INTEGER DEFAULT 0, " +
                    "Alignment INTEGER DEFAULT 0, " +
                    "Alternator INTEGER DEFAULT 0, " +
                    "AutomaticTransmissionFluid INTEGER DEFAULT 0, " +
                    "BrakeFluid INTEGER DEFAULT 0, " +
                    "Coolant INTEGER DEFAULT 0, " +
                    "DiscBrakes INTEGER DEFAULT 0, " +
                    "DrumBrakes INTEGER DEFAULT 0, " +
                    "ManualTransmissionFluid INTEGER DEFAULT 0, " +
                    "Oil INTEGER DEFAULT 0, " +
                    "PowerSteeringFluid INTEGER DEFAULT 0, " +
                    "RadiatorFluid INTEGER DEFAULT 0, " +
                    "Rotation INTEGER DEFAULT 0, " +
                    "Rotors INTEGER DEFAULT 0, " +
                    "Solenoid INTEGER DEFAULT 0, " +
                    "Starter INTEGER DEFAULT 0, " +
                    "TimingBelts INTEGER DEFAULT 0, " +
                    "Tires INTEGER DEFAULT 0";
    //endregion

    public enum TableColumns {
        ID_COLUMN("ID"),
        RELATED_REPAIR_SCHEDULE_NAME_COLUMN("RelatedRepairScheduleName"),
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
    public LastRepairedTable(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, factory);
    }
    //endregion

    public <T extends Enum<T>> void AddTableRow(@NonNull String tableName, @NonNull Class<T> tableColumnsEnum, @NonNull Map<Enum, Integer> valuesToAdd) {
        super.AddTableRow(tableName, tableColumnsEnum, valuesToAdd);
    }
}
