package com.example.action_laptop.checkengine;

import android.content.Context;

import java.util.Map;

/**
 * Created by Action-Laptop on 10/16/2017.
 */

public class NotificationsDBHandler extends DBHandler {

    Map<String, Object> columnNameValues;
    Context context;

    //region Constructor
    public NotificationsDBHandler(Context context) {
        super(context, null);
        this.context = context;
    }
    //endregion

    //Pulls the values from the notification table based on the name of the column and returns a notificationValues object
    public NotificationValues GetAllNotificationValues(){
        NotificationValues notificationValues = new NotificationValues();

        //retrieve row from database
        columnNameValues = SelectAllFromTable(NotificationsTable.TABLE_NAME);

        //populate notifications values object
        notificationValues.setMileageThreshold(GetValueFromColumnName(NotificationsTable.TableColumns.MILEAGE_THRESHOLD_COLUMN.toString()));
        notificationValues.setFrequency(NotificationValues.NotificationFrequency.values()[GetValueFromColumnName(NotificationsTable.TableColumns.FREQUENCY_COLUMN.toString())]);
        notificationValues.setAndroidBar(GetValueFromColumnName(NotificationsTable.TableColumns.ANDROID_BAR_COLUMN.toString()) == 1);
        notificationValues.setLockScreen(GetValueFromColumnName(NotificationsTable.TableColumns.LOCK_SCREEN_COLUMN.toString()) == 1);

        return notificationValues;
    }

    private int GetValueFromColumnName(String columnName){
        //try to parse the string as an int. return the parsed int if true, else return 0
        return Validator.TryParseToInt((String) columnNameValues.get(columnName)) ? Integer.valueOf((String) columnNameValues.get(columnName)) : 0;
    }

    public void UpdateNotificationSetting(String notificationSetting, boolean isSet){
        //TODO Validate settingName
        StringBuilder columnValuePairs = new StringBuilder(notificationSetting + "=" + (isSet ? 1 : 0));
        UpdateAllValuesInTable(NotificationsTable.TABLE_NAME, columnValuePairs);
    }

    public void UpdateNotificationSetting(String notificationSetting, String stringValue){
        //TODO Validate settingName
        StringBuilder columnValuePairs = new StringBuilder(notificationSetting + "='" + stringValue + "'");
        UpdateAllValuesInTable(NotificationsTable.TABLE_NAME, columnValuePairs);
    }

    public void UpdateNotificationSetting(String notificationSetting, int intValue){
        //TODO Validate settingName
        StringBuilder columnValuePairs = new StringBuilder(notificationSetting + "=" + intValue);
        UpdateAllValuesInTable(NotificationsTable.TABLE_NAME, columnValuePairs);
    }
}
