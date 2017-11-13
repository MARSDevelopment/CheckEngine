package com.example.action_laptop.checkengine;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

/**
 * Created by Action-Laptop on 10/17/2017.
 */

public class CarInfoDBHandler extends DBHandler {

    Map<String, Object> columnNameValues;

    //region Constructor
    public CarInfoDBHandler(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, factory);
    }
    //endregion

    //Pulls the values from the notification table based on the name of the column and returns a carInfo object
    public CarInfo GetCarInfoFromCarName(String carName){
        CarInfo carInfo = new CarInfo();

        //retrieve row from database
        columnNameValues = SelectAllFromTableRow(GarageTable.TABLE_NAME, GarageTable.TableColumns.NAME_COLUMN.toString(), carName);

        //populate notifications values object
        carInfo.setID(GetStringValueFromColumnName(GarageTable.TableColumns.ID_COLUMN.toString()));
        carInfo.setName(GetStringValueFromColumnName(GarageTable.TableColumns.NAME_COLUMN.toString()));
        carInfo.setMake(GetStringValueFromColumnName(GarageTable.TableColumns.MAKE_COLUMN.toString()));
        carInfo.setModel(GetStringValueFromColumnName(GarageTable.TableColumns.MODEL_COLUMN.toString()));
        carInfo.setYear(GetIntValueFromColumnName(GarageTable.TableColumns.YEAR_COLUMN.toString()));
        carInfo.setCurrentMileage(GetIntValueFromColumnName(GarageTable.TableColumns.CURRENT_MILEAGE_COLUMN.toString()));
        carInfo.setInUse(GetBooleanValueFromColumnName(GarageTable.TableColumns.IN_USE_COLUMN.toString()));

        return carInfo;
    }

    private String GetStringValueFromColumnName(String columnName){
        return String.valueOf(columnNameValues.get(columnName));
    }

    private int GetIntValueFromColumnName(String columnName){
        //try to parse the string as an int. return the parsed int if true, else return 0
        return Validator.TryParseToInt((String) columnNameValues.get(columnName)) ? Integer.valueOf((String) columnNameValues.get(columnName)) : 0;
    }

    private boolean GetBooleanValueFromColumnName(String columnName){
        //returns true if string can be parsed to int and equals 1
        return Validator.TryParseToInt((String) columnNameValues.get(columnName)) && Integer.valueOf((String) columnNameValues.get(columnName)) == 1;
    }

    public void UpdateCarInUse(String carName, boolean isSet) {
        //TODO Validate Car Name
        StringBuilder columnValuePairs = new StringBuilder(GarageTable.TableColumns.IN_USE_COLUMN.toString() + "=" + (isSet ? 1 : 0));
        StringBuilder whereCondition = new StringBuilder(GarageTable.TableColumns.NAME_COLUMN.toString() + "='" + carName + "'");
        UpdateValuesInTable(GarageTable.TABLE_NAME, columnValuePairs, whereCondition);
    }

    public void UpdateCarCurrentMileage(String carName, int currentMileage){
        //TODO Validate Car Name
        StringBuilder columnValuePairs = new StringBuilder(GarageTable.TableColumns.CURRENT_MILEAGE_COLUMN.toString() + "=" + currentMileage);
        StringBuilder whereCondition = new StringBuilder(GarageTable.TableColumns.NAME_COLUMN.toString() + "='" + carName + "'");
        UpdateValuesInTable(GarageTable.TABLE_NAME, columnValuePairs, whereCondition);
    }
}
