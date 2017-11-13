package com.example.action_laptop.checkengine;

import java.util.*;

/**
 * Created by Action-Laptop on 6/29/2017.
 */

public class CarValues{

    //region Variables
    public Map<Enum, Object> carItemsHashMap = new LinkedHashMap<>();

    public enum CarItems {
        AIR_FILTER("AirFilter"),
        ALIGNMENT("Alignment"),
        ALTERNATOR("Alternator"),
        AUTOMATIC_TRANSMISSION_FLUID("AutomaticTransmissionFluid"),
        BRAKE_FLUID("BrakeFluid"),
        COOLANT("Coolant"),
        DISC_BRAKES("DiscBrakes"),
        DRUM_BRAKES("DrumBrakes"),
        MANUAL_TRANSMISSION_FLUID("ManualTransmissionFluid"),
        OIL("Oil"),
        POWER_STEERING_FLUID("PowerSteeringFluid"),
        RADIATOR_FLUID("RadiatorFluid"),
        ROTATION("Rotation"),
        ROTORS("Rotors"),
        SOLENOID("Solenoid"),
        STARTER("Starter"),
        TIMING_BELTS("TimingBelts"),
        TIRES("Tires");

        private final String value;

        //returns string associated with enum
        private String getValue() {
            return value;
        }

        CarItems(String value){
            this.value = value;
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
    public CarValues(){
        for (CarItems item : CarItems.values()){
            carItemsHashMap.put(item, 0);
        }
    }

    public CarValues(String str){
        setAirFilter(25000);
        setAlignment(25000);
        setAlternator(25000);
        setAutoTransmission(25000);
        setBrakeFluid(25000);
        setCoolant(25000);
        setDiscBrakes(25000);
        setDrumBrakes(25000);
        setManualTransmission(25000);
        setOil(25000);
        setPowerSteering(25000);
        setRadiator(25000);
        setRotation(25000);
        setRotors(25000);
        setSolenoid(25000);
        setStarter(25000);
        setTimingBelts(25000);
        setTires(25000);
    }

    //Full Arg Contructor
    public CarValues(int airFilter, int alignment, int alternator, int automaticTransFluid, int brakeFluid, int coolant, int discBrakes, int drumBrakes, int manualTransFluid,
                     int oil, int powerSteering, int radiator, int rotation, int rotors, int solenoid, int starter, int timingBelts, int tires) {
        setAirFilter(airFilter);
        setAlignment(alignment);
        setAlternator(alternator);
        setAutoTransmission(automaticTransFluid);
        setBrakeFluid(brakeFluid);
        setCoolant(coolant);
        setDiscBrakes(discBrakes);
        setDrumBrakes(drumBrakes);
        setManualTransmission(manualTransFluid);
        setOil(oil);
        setPowerSteering(powerSteering);
        setRadiator(radiator);
        setRotation(rotation);
        setRotors(rotors);
        setSolenoid(solenoid);
        setStarter(starter);
        setTimingBelts(timingBelts);
        setTires(tires);
    }
    //endregion

    //region Methods

    //creates and populates a string list from the car item enums
    public static List<String> GetCarItemList(){
        ArrayList<String> carItemList = new ArrayList<>();

        for(Enum item : CarValues.CarItems.values()){
            carItemList.add(item.toString());
        }

        return carItemList;
    }
    //endregion

    //region Getters and Setters
    public int getAirFilter() {
        return (int) carItemsHashMap.get(CarItems.AIR_FILTER);
    }

    public void setAirFilter(int airFilter) {
        carItemsHashMap.put(CarItems.AIR_FILTER, airFilter);
    }

    public int getAlignment() {
        return (int) carItemsHashMap.get(CarItems.ALIGNMENT);
    }

    public void setAlignment(int alignment) {
        carItemsHashMap.put(CarItems.ALIGNMENT, alignment);
    }

    public int getAlternator() {
        return (int) carItemsHashMap.get(CarItems.ALTERNATOR);
    }

    public void setAlternator(int alternator) {
        carItemsHashMap.put(CarItems.ALTERNATOR, alternator);
    }

    public int getAutoTransmission() {
        return (int) carItemsHashMap.get(CarItems.AUTOMATIC_TRANSMISSION_FLUID);
    }

    public void setAutoTransmission(int autoTransmission) {
        carItemsHashMap.put(CarItems.AUTOMATIC_TRANSMISSION_FLUID, autoTransmission);
    }

    public int getBrakeFluid() {
        return (int) carItemsHashMap.get(CarItems.BRAKE_FLUID);
    }

    public void setBrakeFluid(int brakeFluid) {
        carItemsHashMap.put(CarItems.BRAKE_FLUID, brakeFluid);
    }

    public int getCoolant() {
        return (int) carItemsHashMap.get(CarItems.COOLANT);
    }

    public void setCoolant(int coolant) {
        carItemsHashMap.put(CarItems.COOLANT, coolant);
    }

    public int getDiscBrakes() {
        return (int) carItemsHashMap.get(CarItems.DISC_BRAKES);
    }

    public void setDiscBrakes(int discBrakes) {
        carItemsHashMap.put(CarItems.DISC_BRAKES, discBrakes);
    }

    public int getDrumBrakes() {
        return (int) carItemsHashMap.get(CarItems.DRUM_BRAKES);
    }

    public void setDrumBrakes(int drumBrakes) {
        carItemsHashMap.put(CarItems.DRUM_BRAKES, drumBrakes);
    }

    public int getManualTransmission() {
        return (int) carItemsHashMap.get(CarItems.MANUAL_TRANSMISSION_FLUID);
    }

    public void setManualTransmission(int manualTransmission) {
        carItemsHashMap.put(CarItems.MANUAL_TRANSMISSION_FLUID, manualTransmission);
    }

    public int getOil() {
        return (int) carItemsHashMap.get(CarItems.OIL);
    }

    public void setOil(int oil) {
        carItemsHashMap.put(CarItems.OIL, oil);
    }

    public int getPowerSteering() {
        return (int) carItemsHashMap.get(CarItems.POWER_STEERING_FLUID);
    }

    public void setPowerSteering(int powerSteering) {
        carItemsHashMap.put(CarItems.POWER_STEERING_FLUID, powerSteering);
    }

    public int getRadiator() {
        return (int) carItemsHashMap.get(CarItems.RADIATOR_FLUID);
    }

    public void setRadiator(int radiator) {
        carItemsHashMap.put(CarItems.RADIATOR_FLUID, radiator);
    }

    public int getRotation() {
        return (int) carItemsHashMap.get(CarItems.ROTATION);
    }

    public void setRotation(int rotation) {
        carItemsHashMap.put(CarItems.ROTATION, rotation);
    }

    public int getRotors() {
        return (int) carItemsHashMap.get(CarItems.ROTORS);
    }

    public void setRotors(int rotors) {
        carItemsHashMap.put(CarItems.ROTORS, rotors);
    }

    public int getSolenoid() {
        return (int) carItemsHashMap.get(CarItems.SOLENOID);
    }

    public void setSolenoid(int solenoid) {
        carItemsHashMap.put(CarItems.SOLENOID, solenoid);
    }

    public int getStarter() {
        return (int) carItemsHashMap.get(CarItems.STARTER);
    }

    public void setStarter(int starter) {
        carItemsHashMap.put(CarItems.STARTER, starter);
    }

    public int getTimingBelts() {
        return (int) carItemsHashMap.get(CarItems.TIMING_BELTS);
    }

    public void setTimingBelts(int timingBelts) {
        carItemsHashMap.put(CarItems.TIMING_BELTS, timingBelts);
    }

    public int getTires() {
        return (int) carItemsHashMap.get(CarItems.TIRES);
    }

    public void setTires(int tires) {
        carItemsHashMap.put(CarItems.TIRES, tires);
    }
    //endregion

}