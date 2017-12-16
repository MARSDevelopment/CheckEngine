package com.example.action_laptop.checkengine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Action-Laptop on 9/15/2017.
 */


//Reference Material: https://www.youtube.com/watch?v=cp2rL3sAFmI

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "CheckEngine.db";
    private SQLiteDatabase db;
    private Context context;

    //region Failed Instance/Singleton Database Structure Attempt
//    public static synchronized DBHandler getInstance(Context context) {
//
//        // Use the application context, which will ensure that you
//        // don't accidentally leak an Activity's context.
//        // See this article for more information: http://bit.ly/6LRzfx
//        if (dbInstance == null) {
//            dbInstance = new DBHandler(context.getApplicationContext(), null);
//        }
//        return dbInstance;
//    }
    //endregion

    //region Constructors
     public DBHandler(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
         this.context = context;
    }
    //endregion

    //region OnCreate Override Method - Creates App's Default SQLite Database
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String defaultRowName = "Default";
        String sql;
        this.db = db;
        Map<String, String> contentValues = new HashMap<>();
        GlobalValues globalValues = new GlobalValues(context);

        try {
            //TODO deal with constructors that have string parameters
            //create repair schedule table and default row
            sql = "CREATE TABLE " + RepairScheduleTable.TABLE_NAME + " (" + RepairScheduleTable.createColumnsString + ")";
            db.execSQL(sql);
            CarValues repairScheduleDefaultCarValues = new CarValues(defaultRowName);
            contentValues.put(RepairScheduleTable.TableColumns.NAME_COLUMN.toString(), "'"+ defaultRowName +"'");
            contentValues = DetermineValuesAddedToTable(RepairScheduleTable.TableColumns.class, contentValues, repairScheduleDefaultCarValues.carItemsHashMap);
            InsertDefaultRowIntoTable(RepairScheduleTable.TABLE_NAME, contentValues);

            //clear old data
            contentValues = new HashMap<>();

            //create last repaired table and default row
            sql = "CREATE TABLE " + LastRepairedTable.TABLE_NAME + " (" + LastRepairedTable.createColumnsString + ")";
            db.execSQL(sql);
            CarValues lastRepairedDefaultCarValues = new CarValues();
            contentValues.put(LastRepairedTable.TableColumns.RELATED_REPAIR_SCHEDULE_NAME_COLUMN.toString(), "'"+ defaultRowName +"'");
            contentValues = DetermineValuesAddedToTable(LastRepairedTable.TableColumns.class, contentValues, lastRepairedDefaultCarValues.carItemsHashMap);
            InsertDefaultRowIntoTable(LastRepairedTable.TABLE_NAME, contentValues);

            contentValues = new HashMap<>();

            //create notifications table and default row
            sql = "CREATE TABLE " + NotificationsTable.TABLE_NAME + " (" + NotificationsTable.createColumnsString + ")";
            db.execSQL(sql);
            NotificationValues notificationDefaultValues = new NotificationValues();
            contentValues = DetermineValuesAddedToTable(NotificationsTable.TableColumns.class, contentValues, notificationDefaultValues.notificationItemsHasMap);
            InsertDefaultRowIntoTable(NotificationsTable.TABLE_NAME, contentValues);

            contentValues = new HashMap<>();

            //create garage table and default row
            sql = "CREATE TABLE " + GarageTable.TABLE_NAME + " (" + GarageTable.createColumnsString + ")";
            db.execSQL(sql);
            CarInfo defaultCarInfoValues = new CarInfo("Default", true);
            contentValues = DetermineValuesAddedToTable(GarageTable.TableColumns.class, contentValues, defaultCarInfoValues.detailsHasMap);
            InsertDefaultRowIntoTable(GarageTable.TABLE_NAME, contentValues);

            //TODO initialize all global values: all notification settings
            //set global app parameters
            globalValues.Set(GlobalValues.CarInfo.CAR_NAME.toString(), defaultRowName);
            globalValues.Set(GlobalValues.CarInfo.CURRENT_MILEAGE.toString(), String.valueOf(defaultCarInfoValues.getCurrentMileage()));
            globalValues.Set(NotificationsTable.TableColumns.MILEAGE_THRESHOLD_COLUMN.toString(), String.valueOf(notificationDefaultValues.getMileageThreshold()));
            globalValues.Set(NotificationsTable.TableColumns.FREQUENCY_COLUMN.toString(), String.valueOf(NotificationValues.NotificationFrequency.DAILY.getValue()));
            globalValues.Set(NotificationsTable.TableColumns.ANDROID_BAR_COLUMN.toString(), String.valueOf(notificationDefaultValues.getAndroidBar()));
            globalValues.Set(NotificationsTable.TableColumns.LOCK_SCREEN_COLUMN.toString(), String.valueOf(notificationDefaultValues.getLockScreen()));


            //not working, I have no fucking idea why. it should
            //ContentValues contentValues = new ContentValues();   <------ would be used instead of Map
            //long insert = db.insert(RepairScheduleTable.TABLE_NAME, "ID", contentValues);
            //insert = insert ++;
            //InsertRowIntoTable(RepairScheduleTable.TABLE_NAME, contentValues);

        /*
         *
         * IMPORTANT - Do not close the database. The database close is handled by the inherited class (SQLiteOpenHelper) after onCreate has executed
         *             Closing the database will cause an exception. That was a hard learned lesson...
         */
        } catch (SQLiteException sqlEx) {
            //TODO Handle Exceptions
            sqlEx = sqlEx;
        } catch (Exception ex) {
            Exception ex1 = ex;
        }
    }
    //endregion

    //region Override Method for Upgrading the Database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //endregion

    //region DBConnectors
    private void OpenDBConnection(){
        if(db == null || !db.isOpen())
            db = getWritableDatabase();
    }

    private void CloseDBConnection(){
        if (db != null && db.isOpen())
            db.close();
        db = null;
    }
    //endregion

    //region CRUD Methods

    //region Read/Select Methods
    //Retrieve All Values From Table
    public Map<String, Object> SelectAllFromTable(@NonNull String tableName) {
        Cursor cursor = null;
        Map<String, Object> columnNameValueMap = new HashMap<>();
        String sql = "SELECT * FROM " + tableName;

        try {
            OpenDBConnection();
            cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            do{
                for(int i = 0; i < cursor.getColumnCount(); i++){
                    columnNameValueMap.put(cursor.getColumnName(i), cursor.getString(i));
                }
            }
            while(cursor.moveToNext());

        } catch (SQLiteException sqlEx) {
            //TODO Handle Exceptions
            SQLiteException sqlEx2 = sqlEx;
        } catch (Exception ex){
            Exception ex2 = ex;
        } finally {
            CloseDBConnection();
            cursor.close();
        }

        //moveToFirst is less expensive than getCount()
        return columnNameValueMap.isEmpty() && columnNameValueMap.size() > 0 ? null : columnNameValueMap;
    }

    //Retrieve All Values From Table Row
    public Map<String, Object> SelectAllFromTableRow(@NonNull String tableName, @NonNull String tableRow, @NonNull String tableRowValue){
        Cursor cursor = null;
        Map<String, Object> columnNameValueMap = new HashMap<>();
        String sql = "SELECT * FROM " + tableName + " WHERE " + tableRow + " = '" + tableRowValue + "'";

        try {
            OpenDBConnection();
            cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            do{
                for(int i = 0; i < cursor.getColumnCount(); i++){
                    columnNameValueMap.put(cursor.getColumnName(i), cursor.getString(i));
                }
            }
            while(cursor.moveToNext());

        } catch (SQLiteException sqlEx) {
            //TODO Handle Exceptions
            SQLiteException sqlEx2 = sqlEx;
        } catch (Exception ex){
            Exception ex2 = ex;
        } finally {
            CloseDBConnection();
            cursor.close();
        }

        //moveToFirst is less expensive than getCount()
        return columnNameValueMap.isEmpty() && columnNameValueMap.size() > 0 ? null : columnNameValueMap;
    }

    //Retrieve All Row Values From Column Name
    public List<String> SelectAllFromColumnNameInTable(@NonNull String tableName, @NonNull String columnName){
        Cursor cursor = null;
        List<String> columnNameList = new ArrayList<>();
        String sql = "SELECT "+ columnName +" FROM "+ tableName;

        try {
            OpenDBConnection();
            cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            do{
                columnNameList.add(cursor.getString(0));
            }
            while(cursor.moveToNext());

        } catch (SQLiteException sqlEx) {
            //TODO Handle Exceptions
            SQLiteException sqlEx2 = sqlEx;
        } catch (Exception ex){
            Exception ex2 = ex;
        } finally {
            CloseDBConnection();
            assert cursor != null;
            cursor.close();
        }

        return columnNameList;
    }
    //endregion

    //region Create/Insert Methods
    //inserts values into a table with values received from the parameters
    public void InsertRowIntoTable(@NonNull String tableName, @NonNull ContentValues contentValues){
        if(contentValues != null && contentValues.size() > 0 && tableName != null) {
            Validator validator = new Validator();
            StringBuilder values = new StringBuilder();
            StringBuilder columns = new StringBuilder();
            String sql;

            //validate the value to be inserted into the table
            for (Map.Entry<String, Object> entry : contentValues.valueSet()) {
                //checks if the string value of each entry in the map is a string that exists in the tableColumnsEnum
                if(validator.IsStringAnEnum(entry.getKey(), RepairScheduleTable.TableColumns.class))
                    //TODO Check if entry int value is formatted as expected by the database
                    contentValues.put(entry.getKey(), Integer.parseInt(entry.getValue().toString()));
            }

            for(Map.Entry<String, Object> entry : contentValues.valueSet()){
                columns.append(entry.getKey() + ", ");
                values.append(entry.getValue() + ", ");
            }

            values.setLength(values.length() - 2);
            columns.setLength(columns.length() - 2);

            sql = "INSERT INTO " + RepairScheduleTable.TABLE_NAME +
                " (" + columns + ")" +
                " VALUES (" + values + ")";

            ExecuteSqlAndHandleDB(sql);
        }
    }

    public void InsertRowIntoTable(@NonNull String tableName, @NonNull Map<String, String> contentValues){
        if(contentValues != null && contentValues.size() > 0 && tableName != null) {
            StringBuilder values = new StringBuilder();
            StringBuilder columns = new StringBuilder();
            String sql;

            for(Map.Entry<String, String> entry : contentValues.entrySet()){
                columns.append(entry.getKey()).append(", ");
                values.append(entry.getValue()).append(", ");
            }

            values.setLength(values.length() - 2);
            columns.setLength(columns.length() - 2);

            sql = "INSERT INTO " + tableName +
                    " (" + columns + ")" +
                    " VALUES (" + values + ")";

            ExecuteSqlAndHandleDB(sql);
        }
    }

    //Don't add DB open or close methods. This method is used only during database creation
    private void InsertDefaultRowIntoTable(@NonNull String tableName, @NonNull Map<String, String> contentValues){
        if(contentValues != null && contentValues.size() > 0 && tableName != null) {
            StringBuilder values = new StringBuilder();
            StringBuilder columns = new StringBuilder();
            String sql;

            //builds column and value portions of sql string
            for(Map.Entry<String, String> entry : contentValues.entrySet()){
                columns.append(entry.getKey() + ", ");
                values.append(entry.getValue() + ", ");
            }
            //trim the end comma
            values.setLength(values.length() - 2);
            columns.setLength(columns.length() - 2);

            sql = "INSERT INTO " + tableName +
                    " (" + columns + ")" +
                    " VALUES (" + values + ")";

            try{
                db.execSQL(sql);
                // not working either, again no idea
                //long insert = db.insert(tableName, null, values);
            } catch (Exception e){
                //TODO Handle Exceptions
                e = e;
            }
        }
    }

    //endregion

    //region Update Methods
    public void UpdateValuesInTable(@NonNull String tableName, @NonNull StringBuilder columnsValuePairs, StringBuilder condition){
        String sql;

        sql = "UPDATE " + tableName + " SET " + columnsValuePairs + " WHERE " + condition;

        ExecuteSqlAndHandleDB(sql);
    }

    public void UpdateAllValuesInTable(@NonNull String tableName, @NonNull StringBuilder columnsValuePairs){
        String sql;

        sql = "UPDATE " + tableName + " SET " + columnsValuePairs;

        ExecuteSqlAndHandleDB(sql);
    }

    //endregion Method

    //region Delete Methods
    public void DeleteRowFromTable(@NonNull String tableName, @NonNull String tableRowName, @NonNull String tableRowNameValue){
        if(tableRowName != null && tableName != null) {

            String sql = "DELETE FROM "+ tableName +" WHERE "+ tableRowName +" = '"+ tableRowNameValue +"'";
            ExecuteSqlAndHandleDB(sql);
        }
    }
    //endregion

    //endregion

    //region CRUD Helper Methods

    //validate the value to be inserted into the table
    public <T extends Enum<T>> Map<String, String> DetermineValuesAddedToTable(Class<T> tableColumnsEnum, Map<String, String> contentValues, Map<Enum, Object> valuesToCheck) {
        Validator validator = new Validator();
        String entryValue;

        for (Map.Entry<Enum, Object> entry : valuesToCheck.entrySet()) {
            //checks if the string value of each entry in the map is a string that exists in the tableColumnsEnum
            if(validator.IsStringAnEnum(entry.getKey().toString(), tableColumnsEnum)) {
                //put the key and value pair into the map that will be used for the SQL statement
                //string values require single quotes around them when entered into SQL DB
                entryValue = Validator.TryParseToInt(entry.getValue()) ? entry.getValue().toString() : "'" + entry.getValue().toString() + "'";
                contentValues.put(entry.getKey().toString(), entryValue);
            }
        }
        return contentValues;
    }

    private void ExecuteSqlAndHandleDB (String sql){
        try {
            OpenDBConnection();
            db.execSQL(sql);
        } catch (Exception e){
            //TODO Handle Exception
            Exception ex = e;
        } finally {
            CloseDBConnection();
        }
    }

    //endregion


}
