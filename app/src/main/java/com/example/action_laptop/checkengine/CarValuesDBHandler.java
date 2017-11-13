package com.example.action_laptop.checkengine;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.Map;

/**
 * Created by Action-Laptop on 10/8/2017.
 */

public class CarValuesDBHandler extends DBHandler {

    Map<String, Object> columnNameValues;


    public CarValuesDBHandler(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, factory);
    }

    //Pulls the values from the repair schedule table based on the name of the column and returns a carValues object
    public CarValues GetRowFrom(String tableName, @NonNull String tableRowName, String tableRowNameValue){
        CarValues carValues = new CarValues();

        //retrieve row from database
        columnNameValues = SelectAllFromTableRow(tableName, tableRowName, tableRowNameValue);

        //populate car values object
        carValues.setAirFilter(GetValueFromColumnName(RepairScheduleTable.TableColumns.AIR_FILTER_COLUMN.toString()));
        carValues.setAlignment(GetValueFromColumnName(RepairScheduleTable.TableColumns.ALIGNMENT_COLUMN.toString()));
        carValues.setAlternator(GetValueFromColumnName(RepairScheduleTable.TableColumns.ALTERNATOR_COLUMN.toString()));
        carValues.setAutoTransmission(GetValueFromColumnName(RepairScheduleTable.TableColumns.AUTOMATIC_TRANSMISSION_FLUID_COLUMN.toString()));
        carValues.setBrakeFluid(GetValueFromColumnName(RepairScheduleTable.TableColumns.BRAKE_FLUID_COLUMN.toString()));
        carValues.setCoolant(GetValueFromColumnName(RepairScheduleTable.TableColumns.COOLANT_COLUMN.toString()));
        carValues.setDiscBrakes(GetValueFromColumnName(RepairScheduleTable.TableColumns.DISC_BRAKES_COLUMN.toString()));
        carValues.setDrumBrakes(GetValueFromColumnName(RepairScheduleTable.TableColumns.DRUM_BRAKES_COLUMN.toString()));
        carValues.setManualTransmission(GetValueFromColumnName(RepairScheduleTable.TableColumns.MANUAL_TRANSMISSION_FLUID_COLUMN.toString()));
        carValues.setOil(GetValueFromColumnName(RepairScheduleTable.TableColumns.OIL_COLUMN.toString()));
        carValues.setPowerSteering(GetValueFromColumnName(RepairScheduleTable.TableColumns.POWER_STEERING_FLUID_COLUMN.toString()));
        carValues.setRadiator(GetValueFromColumnName(RepairScheduleTable.TableColumns.RADIATOR_FLUID_COLUMN.toString()));
        carValues.setRotation(GetValueFromColumnName(RepairScheduleTable.TableColumns.ROTATION_COLUMN.toString()));
        carValues.setRotors(GetValueFromColumnName(RepairScheduleTable.TableColumns.ROTORS_COLUMN.toString()));
        carValues.setSolenoid(GetValueFromColumnName(RepairScheduleTable.TableColumns.SOLENOID_COLUMN.toString()));
        carValues.setStarter(GetValueFromColumnName(RepairScheduleTable.TableColumns.STARTER_COLUMN.toString()));
        carValues.setTimingBelts(GetValueFromColumnName(RepairScheduleTable.TableColumns.TIMING_BELTS_COLUMN.toString()));
        carValues.setTires(GetValueFromColumnName(RepairScheduleTable.TableColumns.TIRES_COLUMN.toString()));

        return carValues;
    }

    public <T extends Enum<T>> void AddTableRow(@NonNull String tableName, @NonNull Class<T> tableColumnsEnum, @NonNull Map<Enum, Integer> valuesToAdd){
        if(valuesToAdd != null && !valuesToAdd.isEmpty() && tableName != null) {
            ContentValues values = new ContentValues();
            Validator validator = new Validator();

            //validate the value to be inserted into the table
            for (Map.Entry<Enum, Integer> entry : valuesToAdd.entrySet()) {
                //checks if the string value of each entry in the map is a string that exists in the tableColumnsEnum
                if(validator.IsStringAnEnum(entry.getKey().toString(), tableColumnsEnum))
                    //TODO Check if entry int value is formatted as expected by the database
                    values.put(entry.getKey().toString(), entry.getValue());
            }

            //call the inherited DBHandler method to insert the row
            super.InsertRowIntoTable(tableName, values);
        }
    }

    public void UpdateTableColumn(@NonNull String tableName, @NonNull String carName, @NonNull String tableRowWithCarName, @NonNull String tableColumn, @NonNull int value){
        StringBuilder columnValuePairs = new StringBuilder(tableColumn + "=" + value);
        StringBuilder whereCondition = new StringBuilder(tableRowWithCarName + "='" + carName + "'");
        UpdateValuesInTable(tableName, columnValuePairs, whereCondition);
    }

    private int GetValueFromColumnName(String columnName){
        //try to parse the string as an int. return the parsed int if true, else return 0
        return Validator.TryParseToInt((String) columnNameValues.get(columnName)) ? Integer.valueOf((String) columnNameValues.get(columnName)) : 0;
    }
}
