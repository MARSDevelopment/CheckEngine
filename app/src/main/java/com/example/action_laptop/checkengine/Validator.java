package com.example.action_laptop.checkengine;

import android.text.Editable;

/**
 * Created by Action Pants on 8/21/2017.
 */

public class Validator {

    //method to validate numeric input
    public static boolean ValidateNumericInput(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
