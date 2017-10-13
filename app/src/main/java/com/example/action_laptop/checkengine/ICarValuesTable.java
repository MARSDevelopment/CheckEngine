package com.example.action_laptop.checkengine;

/**
 * Created by Action-Laptop on 10/12/2017.
 */

public interface ICarValuesTable {
    String TABLE_NAME = "";

    enum TableColumns {
        ID_COLUMN("ID"),
        AIR_FILTER_COLUMN("AirFilter"),
        ALIGNMENT_COLUMN("Alignment"),
        ALTERNATOR_COLUMN("Alternator"),
        AUTOMATIC_TRANSMISSION_FLUID_COLUMN("AutomaticTransmissionFluid"),
        BRAKE_FLUID_COLUMN("BrakeFluid"),
        COOLANT_COLUMN("Coolant"),
        DISC_BRAKES_COLUMN("DiscBrakes"),
        DRUM_BRAKES_COLUMN("DrumBrakes"),
        MANUAL_TRANSMISSION_FLUID_COLUMN("ManualTransmissionFluid"),
        OIL_COLUMN("Oil"),
        POWER_STEERING_FLUID_COLUMN("PowerSteeringFluid"),
        RADIATOR_FLUID_COLUMN("RadiatorFluid"),
        ROTATION_COLUMN("Rotation"),
        ROTORS_COLUMN("Rotors"),
        SOLENOID_COLUMN("Solenoid"),
        STARTER_COLUMN("Starter"),
        TIMING_BELTS_COLUMN("TimingBelts"),
        TIRES_COLUMN("Tires");

        private String value;

        //one arg constructor
        //initializes each enum with the string hardcoded to it
        TableColumns(String value) {
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
}
