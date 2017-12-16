package com.example.action_laptop.checkengine;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Action-Laptop on 10/17/2017.
 */

public class CarInfoDBHandler extends DBHandler {

    //region Variables
    Map<String, Object> columnNameValues;
    Context context;
    //endregion

    //region Constructor
    public CarInfoDBHandler(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, factory);
        this.context = context;
    }
    //endregion

    //region Select Methods
    //Pulls the values from the notification table based on the name of the column and returns a carInfo object
    public CarInfo GetCarInfoFromCarName(@NonNull String carName){
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

    public List<String> GetAllCarNamesInDatabase(){
        return SelectAllFromColumnNameInTable(GarageTable.TABLE_NAME, GarageTable.TableColumns.NAME_COLUMN.toString());
    }
    //endregion

    //region Create Methods
    public void CreateNewCarWithDefaultRepairs(@NonNull final CarInfo carInfo){
        CarValues carValues = new CarValues();
        Map<String, String> garageTableMap = new HashMap<String, String>(){{
            put(GarageTable.TableColumns.NAME_COLUMN.toString(), "'"+ carInfo.getName() +"'");
        }};
        Map<String, String> lastRepairedTableMap = new HashMap<String, String>(){{
            put(LastRepairedTable.TableColumns.RELATED_REPAIR_SCHEDULE_NAME_COLUMN.toString(), "'"+ carInfo.getName() +"'");
        }};
        Map<String, String> repairScheduleTableMap = new HashMap<String, String>(){{
            put(RepairScheduleTable.TableColumns.NAME_COLUMN.toString(), "'"+ carInfo.getName() +"'");
        }};

        InsertRowIntoTable(GarageTable.TABLE_NAME, DetermineValuesAddedToTable(GarageTable.TableColumns.class, garageTableMap, carInfo.detailsHasMap));
        InsertRowIntoTable(LastRepairedTable.TABLE_NAME, DetermineValuesAddedToTable(LastRepairedTable.TableColumns.class, lastRepairedTableMap, carValues.carItemsHashMap));
        InsertRowIntoTable(RepairScheduleTable.TABLE_NAME, DetermineValuesAddedToTable(RepairScheduleTable.TableColumns.class, repairScheduleTableMap, carValues.carItemsHashMap));
    }
    //endregion

    //region Update Methods
    public void UpdateCarInUse(@NonNull String carName, boolean isSet) {
        //TODO parameterize
        StringBuilder columnValuePairs = new StringBuilder(GarageTable.TableColumns.IN_USE_COLUMN.toString()).append("=").append((isSet ? 1 : 0));
        StringBuilder whereCondition = new StringBuilder(GarageTable.TableColumns.NAME_COLUMN.toString()).append("='").append(carName).append("'");
        UpdateValuesInTable(GarageTable.TABLE_NAME, columnValuePairs, whereCondition);
    }

    public void UpdateCarCurrentMileage(@NonNull String carName, int currentMileage){
        //TODO parameterize
        StringBuilder columnValuePairs = new StringBuilder(GarageTable.TableColumns.CURRENT_MILEAGE_COLUMN.toString()).append("=").append(currentMileage);
        StringBuilder whereCondition = new StringBuilder(GarageTable.TableColumns.NAME_COLUMN.toString()).append("='").append(carName).append("'");
        UpdateValuesInTable(GarageTable.TABLE_NAME, columnValuePairs, whereCondition);
    }

    public void UpdateAllCarInfo(@NonNull CarInfo carInfo){
        StringBuilder columnValuePairs = new StringBuilder();
        columnValuePairs.append(GarageTable.TableColumns.CURRENT_MILEAGE_COLUMN.toString()).append("=").append(carInfo.getCurrentMileage()).append(", ");
        columnValuePairs.append(GarageTable.TableColumns.MAKE_COLUMN.toString()).append("='").append(carInfo.getMake()).append("', ");
        columnValuePairs.append(GarageTable.TableColumns.MODEL_COLUMN.toString()).append("='").append(carInfo.getModel()).append("', ");
        columnValuePairs.append(GarageTable.TableColumns.YEAR_COLUMN.toString()).append("=").append(carInfo.getYear());

        StringBuilder whereCondition = new StringBuilder(GarageTable.TableColumns.NAME_COLUMN.toString()).append("='").append(carInfo.getName()).append("'");

        UpdateValuesInTable(GarageTable.TABLE_NAME, columnValuePairs, whereCondition);
    }
    //endregion

    //region Delete Methods
    public void DeleteAllAssociatedWithCarName(@NonNull String carName){
        DeleteRowFromTable(GarageTable.TABLE_NAME, GarageTable.TableColumns.NAME_COLUMN.toString(), carName);
        DeleteRowFromTable(RepairScheduleTable.TABLE_NAME, RepairScheduleTable.TableColumns.NAME_COLUMN.toString(), carName);
        DeleteRowFromTable(LastRepairedTable.TABLE_NAME, LastRepairedTable.TableColumns.RELATED_REPAIR_SCHEDULE_NAME_COLUMN.toString(), carName);
    }
    //endregion

    //region Helper Methods
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
    //endregion

}
