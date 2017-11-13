package com.example.action_laptop.checkengine;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Action-Laptop on 10/17/2017.
 */

public class CarInfo {

    //region Variables
    public Map<Enum, Object> detailsHasMap = new LinkedHashMap<>();

    public enum Details {
        ID("ID"),
        NAME("Name"),
        MAKE("Make"),
        MODEL("Model"),
        YEAR("Year"),
        CURRENT_MILEAGE("CurrentMileage"),
        IN_USE("InUse");

        private String value;

        //one arg constructor
        //initializes each enum with the string hardcoded to it
        Details(String value) {
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

    //region Constructor
    public CarInfo(){
        setName("");
        setMake("");
        setModel("");
        setYear(0);
        setCurrentMileage(0);
        setInUse(false);
    }

    public CarInfo(String str){
        setName("Default");
        setMake("Generic");
        setModel("Generic");
        setYear(2006);
        setCurrentMileage(50000);
        setInUse(true);
    }
    //endregion

    //region Getters and Setters
    public String getID(){
        return (String) detailsHasMap.get(Details.ID);
    }

    public void setID(String value){
        detailsHasMap.put(Details.ID, value);
    }

    public String getName(){
        return (String) detailsHasMap.get(Details.NAME);
    }

    public void setName(String value){
        detailsHasMap.put(Details.NAME, value);
    }

    public String getMake(){
        return (String) detailsHasMap.get(Details.MAKE);
    }

    public void setMake(String value){
        detailsHasMap.put(Details.MAKE, value);
    }

    public String getModel(){
        return (String) detailsHasMap.get(Details.MODEL);
    }

    public void setModel(String value){
        detailsHasMap.put(Details.MODEL, value);
    }

    public int getYear(){
        return (int) detailsHasMap.get(Details.YEAR);
    }

    public void setYear(int value){
        detailsHasMap.put(Details.YEAR, value);
    }

    public int getCurrentMileage(){
        return (int) detailsHasMap.get(Details.CURRENT_MILEAGE);
    }

    public void setCurrentMileage(int value){
        detailsHasMap.put(Details.CURRENT_MILEAGE, value);
    }

    public void setInUse(boolean value) {
        detailsHasMap.put(Details.IN_USE, value ? 1 : 0);
    }

    public boolean getInUse(){
        return (int) detailsHasMap.get(Details.IN_USE) == 1;
    }
    //endregion
}
