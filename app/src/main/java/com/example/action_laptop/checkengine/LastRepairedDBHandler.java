package com.example.action_laptop.checkengine;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.Map;

/**
 * Created by Action-Laptop on 10/6/2017.
 */

public class LastRepairedDBHandler extends DBHandler {

    Map<String, Object> columnNameValues;

    //region Constructors
    public LastRepairedDBHandler(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, factory);
    }
    //endregion

    //Pulls the values from the repair schedule table based on the name of the column and returns a carValues object
    public CarValues GetLasrRepairedFromRowName(@NonNull String tableRowName){
        CarValues carValues = new CarValues();

        //retrieve row from database
        columnNameValues = SelectAllFromTableRow(LastRepairedTable.TABLE_NAME, LastRepairedTable.TableColumns.RELATED_REPAIR_SCHEDULE_NAME_COLUMN.toString(), tableRowName);

        //populate car values object
        carValues.setAirFilter(GetValueFromColumnName(LastRepairedTable.TableColumns.AIR_FILTER_COLUMN.toString()));
        carValues.setAlignment(GetValueFromColumnName(LastRepairedTable.TableColumns.ALIGNMENT_COLUMN.toString()));
        carValues.setAlternator(GetValueFromColumnName(LastRepairedTable.TableColumns.ALTERNATOR_COLUMN.toString()));
        carValues.setAutoTransmission(GetValueFromColumnName(LastRepairedTable.TableColumns.AUTOMATIC_TRANSMISSION_FLUID_COLUMN.toString()));
        carValues.setBrakeFluid(GetValueFromColumnName(LastRepairedTable.TableColumns.BRAKE_FLUID_COLUMN.toString()));
        carValues.setCoolant(GetValueFromColumnName(LastRepairedTable.TableColumns.COOLANT_COLUMN.toString()));
        carValues.setDiscBrakes(GetValueFromColumnName(LastRepairedTable.TableColumns.DISC_BRAKES_COLUMN.toString()));
        carValues.setDrumBrakes(GetValueFromColumnName(LastRepairedTable.TableColumns.DRUM_BRAKES_COLUMN.toString()));
        carValues.setManualTransmission(GetValueFromColumnName(LastRepairedTable.TableColumns.MANUAL_TRANSMISSION_FLUID_COLUMN.toString()));
        carValues.setOil(GetValueFromColumnName(LastRepairedTable.TableColumns.OIL_COLUMN.toString()));
        carValues.setPowerSteering(GetValueFromColumnName(LastRepairedTable.TableColumns.POWER_STEERING_FLUID_COLUMN.toString()));
        carValues.setRadiator(GetValueFromColumnName(LastRepairedTable.TableColumns.RADIATOR_FLUID_COLUMN.toString()));
        carValues.setRotation(GetValueFromColumnName(LastRepairedTable.TableColumns.ROTATION_COLUMN.toString()));
        carValues.setRotors(GetValueFromColumnName(LastRepairedTable.TableColumns.ROTORS_COLUMN.toString()));
        carValues.setSolenoid(GetValueFromColumnName(LastRepairedTable.TableColumns.SOLENOID_COLUMN.toString()));
        carValues.setStarter(GetValueFromColumnName(LastRepairedTable.TableColumns.STARTER_COLUMN.toString()));
        carValues.setTimingBelts(GetValueFromColumnName(LastRepairedTable.TableColumns.TIMING_BELTS_COLUMN.toString()));
        carValues.setTires(GetValueFromColumnName(LastRepairedTable.TableColumns.TIRES_COLUMN.toString()));

        return carValues;
    }

    private int GetValueFromColumnName(String columnName){
        //try to parse the string as an int. return the parsed int if true, else return 0
        return Validator.TryParseIntFromString((String) columnNameValues.get(columnName)) ? Integer.valueOf((String) columnNameValues.get(columnName)) : 0;
    }

}
