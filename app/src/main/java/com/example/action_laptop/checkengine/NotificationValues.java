package com.example.action_laptop.checkengine;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Action-Laptop on 10/15/2017.
 */

public class NotificationValues {

    //region Variables
    public Map<Enum, Object> notificationItemsHasMap = new LinkedHashMap<>();

    public enum NotificationFrequency {
        DAILY(0),
        WEEKLY(1);

        private final int value;

        //one arg constructor
        //initializes each enum with the int hardcoded to it
        NotificationFrequency(int value) {
            this.value = value;
        }

        //returns int associated with enum
        private int getValue() {
            return value;
        }

        public NotificationFrequency GetEnumFromInt(int value){
            switch (value){
                case 1:
                    return NotificationFrequency.WEEKLY;
                case 0:
                default:
                    return NotificationFrequency.DAILY;
            }
        }
    }

    public enum NotificationItems {
        MILEAGE_THRESHOLD_COLUMN("MileageThreshold"),
        FREQUENCY_COLUMN("Frequency"),
        ANDROID_BAR_COLUMN("AndroidBar"),
        LOCK_SCREEN_COLUMN("LockScreen");

        private final String value;

        //one arg constructor
        //initializes each enum with the int hardcoded to it
        NotificationItems(String value) {
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

    //endregion

    //region Constructors

    //No Arg Constructor
    public NotificationValues(){
        setMileageThreshold(500);
        setFrequency(NotificationFrequency.DAILY);
        setAndroidBar(true);
        setLockScreen(true);
    }

    //endregion

    //region Methods
    public static List<String> GetNotificationFrequencyList(){
        List<String> list = new ArrayList<>();
        for(Enum column : NotificationFrequency.values()) {
            list.add(column.toString());
        }
        return list;
    }

    //endregion

    //region Getters and Setters
    public int getMileageThreshold() {
        return (int) notificationItemsHasMap.get(NotificationItems.MILEAGE_THRESHOLD_COLUMN);
    }

    public void setMileageThreshold(int mileage) {
        notificationItemsHasMap.put(NotificationItems.MILEAGE_THRESHOLD_COLUMN, mileage);
    }

    public NotificationFrequency getFrequency() {
        return NotificationFrequency.values()[(int) notificationItemsHasMap.get(NotificationItems.FREQUENCY_COLUMN)];
    }

    public void setFrequency(NotificationFrequency notificationFrequency) {
        notificationItemsHasMap.put(NotificationItems.FREQUENCY_COLUMN, notificationFrequency.value);
    }

    public boolean getAndroidBar() {
        return (int) notificationItemsHasMap.get(NotificationItems.ANDROID_BAR_COLUMN) == 1;
    }

    public void setAndroidBar(boolean value) {
        notificationItemsHasMap.put(NotificationItems.ANDROID_BAR_COLUMN, value ? 1 : 0);
    }

    public boolean getLockScreen() {
        return (int) notificationItemsHasMap.get(NotificationItems.LOCK_SCREEN_COLUMN) == 1;
    }

    public void setLockScreen(boolean value) {
        notificationItemsHasMap.put(NotificationItems.LOCK_SCREEN_COLUMN, value ? 1 : 0);
    }
    //endregion
}
