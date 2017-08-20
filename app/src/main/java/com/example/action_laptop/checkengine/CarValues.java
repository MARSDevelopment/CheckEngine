package com.example.action_laptop.checkengine;

import java.util.HashMap;
import java.util.*;

/**
 * Created by Action-Laptop on 6/29/2017.
 */

public class CarValues{

    //variables
    public LinkedHashMap<Enum, Integer> carItemsHasMap = new LinkedHashMap<Enum, Integer>();
    public enum CarItems {
        TIMING_BELTS("Timing Belts"),
        SOLENOID("Solenoid"),
        ALTERNATOR("Alternator"),
        STARTER("Starter"),
        AIR_FILTER("Air Filter"),
        DISC_BRAKES("Disc Brakes"),
        DRUM_BRAKES("Drum Brakes"),
        ROTORS("Rotors"),
        TIRES("Tires"),
        ROTATION("Rotation"),
        ALIGNMENT("Alignment"),
        OIL("Oil"),
        BRAKE_FLUID("Brake Fluid"),
        AUTOMATIC_TRANSMISSION_FLUID("Automatic Transmission Fluid"),
        MANUAL_TRANSMISSION_FLUID("Manual Transmission Fluid"),
        RADIATOR_FLUID("Radiator Fluid"),
        POWER_STEERING_FLUID("Power Steering Fluid"),
        COOLANT("Coolant");

        private String value;

        //returns string associated with enum
        private String getValue() {
            return value;
        }

        private CarItems(String value){
            this.value = value;
        }

        @Override
        //override toString method to return enum string
        public String toString() {
            return this.getValue();
        }

        //returns enum from string
        public static CarItems getEnum(String value) {
            for(CarItems item : values())
                if(item.getValue().equalsIgnoreCase(value)) return item;
            throw new IllegalArgumentException();
        }
    }

    //No Arg Constructor
    public CarValues(){
        for (CarItems item : CarItems.values()){
            carItemsHasMap.put(item, 0);
        }
    }

    //Full Arg Contructor
    public CarValues(int timingBelts, int solenoid, int alternator, int starter, int airFilter, int discBrakes, int drumBrakes, int rotors, int tires, int rotatation, int alignment, int oil, int brakeFluid, int autoTransmission, int manualTransmission, int radiator, int powerSteering, int coolant) {
        setAirFilter(airFilter);
        setAlignment(alignment);
        setAlternator(alternator);
        setAutoTransmission(autoTransmission);
        setBrakeFluid(brakeFluid);
        setCoolant(coolant);
        setDiscBrakes(discBrakes);
        setDrumBrakes(drumBrakes);
        setManualTransmission(manualTransmission);
        setOil(oil);
        setPowerSteering(powerSteering);
        setRadiator(radiator);
        setRotation(rotatation);
        setRotors(rotors);
        setSolenoid(solenoid);
        setStarter(starter);
        setTimingBelts(timingBelts);
        setTires(tires);
    }

    //Getters and Setters
    public int getAirFilter() {
        return carItemsHasMap.get(CarItems.AIR_FILTER);
    }

    public void setAirFilter(int airFilter) {
        carItemsHasMap.put(CarItems.AIR_FILTER, airFilter);
    }

    public int getAlignment() {
        return carItemsHasMap.get(CarItems.ALIGNMENT);
    }

    public void setAlignment(int alignment) {
        carItemsHasMap.put(CarItems.ALIGNMENT, alignment);
    }

    public int getAlternator() {
        return carItemsHasMap.get(CarItems.ALTERNATOR);
    }

    public void setAlternator(int alternator) {
        carItemsHasMap.put(CarItems.ALTERNATOR, alternator);
    }

    public int getAutoTransmission() {
        return carItemsHasMap.get(CarItems.AUTOMATIC_TRANSMISSION_FLUID);
    }

    public void setAutoTransmission(int autoTransmission) {
        carItemsHasMap.put(CarItems.AUTOMATIC_TRANSMISSION_FLUID, autoTransmission);
    }

    public int getBrakeFluid() {
        return carItemsHasMap.get(CarItems.BRAKE_FLUID);
    }

    public void setBrakeFluid(int brakeFluid) {
        carItemsHasMap.put(CarItems.BRAKE_FLUID, brakeFluid);
    }

    public int getCoolant() {
        return carItemsHasMap.get(CarItems.COOLANT);
    }

    public void setCoolant(int coolant) {
        carItemsHasMap.put(CarItems.COOLANT, coolant);
    }

    public int getDiscBrakes() {
        return carItemsHasMap.get(CarItems.DISC_BRAKES);
    }

    public void setDiscBrakes(int discBrakes) {
        carItemsHasMap.put(CarItems.DISC_BRAKES, discBrakes);
    }

    public int getDrumBrakes() {
        return carItemsHasMap.get(CarItems.DRUM_BRAKES);
    }

    public void setDrumBrakes(int drumBrakes) {
        carItemsHasMap.put(CarItems.DRUM_BRAKES, drumBrakes);
    }

    public int getManualTransmission() {
        return carItemsHasMap.get(CarItems.MANUAL_TRANSMISSION_FLUID);
    }

    public void setManualTransmission(int manualTransmission) {
        carItemsHasMap.put(CarItems.MANUAL_TRANSMISSION_FLUID, manualTransmission);
    }

    public int getOil() {
        return carItemsHasMap.get(CarItems.OIL);
    }

    public void setOil(int oil) {
        carItemsHasMap.put(CarItems.OIL, oil);
    }

    public int getPowerSteering() {
        return carItemsHasMap.get(CarItems.POWER_STEERING_FLUID);
    }

    public void setPowerSteering(int powerSteering) {
        carItemsHasMap.put(CarItems.POWER_STEERING_FLUID, powerSteering);
    }

    public int getRadiator() {
        return carItemsHasMap.get(CarItems.RADIATOR_FLUID);
    }

    public void setRadiator(int radiator) {
        carItemsHasMap.put(CarItems.RADIATOR_FLUID, radiator);
    }

    public int getRotation() {
        return carItemsHasMap.get(CarItems.ROTATION);
    }

    public void setRotation(int rotation) {
        carItemsHasMap.put(CarItems.ROTATION, rotation);
    }

    public int getRotors() {
        return carItemsHasMap.get(CarItems.ROTORS);
    }

    public void setRotors(int rotors) {
        carItemsHasMap.put(CarItems.ROTORS, rotors);
    }

    public int getSolenoid() {
        return carItemsHasMap.get(CarItems.SOLENOID);
    }

    public void setSolenoid(int solenoid) {
        carItemsHasMap.put(CarItems.SOLENOID, solenoid);
    }

    public int getStarter() {
        return carItemsHasMap.get(CarItems.STARTER);
    }

    public void setStarter(int starter) {
        carItemsHasMap.put(CarItems.STARTER, starter);
    }

    public int getTimingBelts() {
        return carItemsHasMap.get(CarItems.TIMING_BELTS);
    }

    public void setTimingBelts(int timingBelts) {
        carItemsHasMap.put(CarItems.TIMING_BELTS, timingBelts);
    }

    public int getTires() {
        return carItemsHasMap.get(CarItems.TIRES);
    }

    public void setTires(int tires) {
        carItemsHasMap.put(CarItems.TIRES, tires);
    }

//    public int getSolenoid() {
//        return solenoid;
//    }
//
//    public void setSolenoid(int solenoid) {
//        this.solenoid = solenoid;
//    }
//
//    public int getAlternator() {
//        return alternator;
//    }
//
//    public void setAlternator(int alternator) {
//        this.alternator = alternator;
//    }
//
//    public int getStarter() {
//        return starter;
//    }
//
//    public void setStarter(int starter) {
//        this.starter = starter;
//    }
//
//    public int getAirFilter() {
//        return airFilter;
//    }
//
//    public void setAirFilter(int airFilter) {
//        this.airFilter = airFilter;
//    }
//
//    public int getDiscBrakes() {
//        return  discBrakes;
//    }
//
//    public void setDiscBrakes(int dicsBrakes) {
//        this. discBrakes = dicsBrakes;
//    }
//
//    public int getDrumBrakes() {
//        return drumBrakes;
//    }
//
//    public void setDrumBrakes(int drumBrakes) {
//        this.drumBrakes = drumBrakes;
//    }
//
//    public int getRotors() {
//        return rotors;
//    }
//
//    public void setRotors(int rotors) {
//        this.rotors = rotors;
//    }
//
//    public int getTires() {
//        return tires;
//    }
//
//    public void setTires(int tires) {
//        this.tires = tires;
//    }
//
//    public int getRotation() {
//        return rotation;
//    }
//
//    public void setRotation(int rotate) {
//        this.rotation = rotate;
//    }
//
//    public int getAlignment() {
//        return alignment;
//    }
//
//    public void setAlignment(int alignment) {
//        this.alignment = alignment;
//    }
//
//    public int getOil() {
//        return oil;
//    }
//
//    public void setOil(int oil) {
//        this.oil = oil;
//    }
//
//    public int getBrake() {
//        return brakeFluid;
//    }
//
//    public void setBrake(int brakeFluid) {
//        this.brakeFluid = brakeFluid;
//    }
//
//    public int getAutoTransmission() {
//        return autoTransmission;
//    }
//
//    public void setAutoTransmission(int autoTransmission) {
//        this.autoTransmission = autoTransmission;
//    }
//
//    public int getManualTransmission() {
//        return manualTransmission;
//    }
//
//    public void setManualTransmission(int manualTransmission) {
//        this.manualTransmission = manualTransmission;
//    }
//
//    public int getRadiator() {
//        return radiator;
//    }
//
//    public void setRadiator(int radiator) {
//        this.radiator = radiator;
//    }
//
//    public int getPowerSteering() {
//        return powerSteering;
//    }
//
//    public void setPowerSteering(int powerSteering) {
//        this.powerSteering = powerSteering;
//    }
//
//    public int getCoolant() {
//        return coolant;
//    }
//
//    public void setCoolant(int coolant) {
//        this.coolant = coolant;
//    }
    //
//    for (CarItems item : CarItems.values()){
//        switch (item){
//            case AIR_FILTER:
//                carItemsHasMap.put(item, airFilter);
//                break;
//            case ALIGNMENT:
//                carItemsHasMap.put(item, alignment);
//                break;
//            case ALTERNATOR:
//                carItemsHasMap.put(item, alternator);
//                break;
//            case AUTOMATIC_TRANSMISSION_FLUID:
//                carItemsHasMap.put(item, autoTransmission);
//                break;
//            case BRAKE_FLUID:
//                carItemsHasMap.put(item, brakeFluid);
//                break;
//            case COOLANT:
//                carItemsHasMap.put(item, coolant);
//                break;
//            case DISC_BRAKES:
//                carItemsHasMap.put(item, discBrakes);
//                break;
//            case DRUM_BRAKES:
//                carItemsHasMap.put(item, drumBrakes);
//                break;
//            case MANUAL_TRANSMISSION_FLUID:
//                carItemsHasMap.put(item, manualTransmission);
//                break;
//            case OIL:
//                carItemsHasMap.put(item, oil);
//                break;
//            case POWER_STEERING_FLUID:
//                carItemsHasMap.put(item, powerSteering);
//                break;
//            case RADIATOR_FLUID:
//                carItemsHasMap.put(item, radiator);
//                break;
//            case ROTATION:
//                carItemsHasMap.put(item, rotatation);
//                break;
//            case ROTORS:
//                carItemsHasMap.put(item, rotors);
//                break;
//            case SOLENOID:
//                carItemsHasMap.put(item, solenoid);
//                break;
//            case STARTER:
//                carItemsHasMap.put(item, starter);
//                break;
//            case TIMING_BELTS:
//                carItemsHasMap.put(item, timingBelts);
//                break;
//            case TIRES:
//                carItemsHasMap.put(item, tires);
//                break;
//            default:
//                carItemsHasMap.put(item, 0);
//                break;
//        }
//    }

}