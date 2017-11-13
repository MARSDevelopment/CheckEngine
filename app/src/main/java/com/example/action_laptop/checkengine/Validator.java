package com.example.action_laptop.checkengine;

import java.util.List;
import java.util.Map;

/**
 * Created by Action Pants on 8/21/2017.
 */

public class Validator {

    //method to validate numeric input
    public static boolean TryParseToInt(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean TryParseToInt(Object input) {
        try {
            Integer.parseInt(input.toString());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //builds and returns a comma separated string
    public String ToCommaSeparatedString(List<String> list){
        //The Java 8 implementation of this method
        // ...isn't supported by our lower targeted API
        //return tableColumns.stream().collect(Collectors.joining(", "));

        final String separator = ", ";

        StringBuilder columnString = new StringBuilder();
        for (String columnName : list) {
            columnString.append(columnName + separator);
        }

        //remove the end comma and space
        return columnString.substring(0, columnString.length() - separator.length());
    }

    //checks if the string parameter exists in the Enum (Class that extends Enum)
    public <T extends Enum<T>> boolean IsStringAnEnum(String value, Class<T> tableColumnsEnum) {
        T[] tableColumns = tableColumnsEnum.getEnumConstants();

        for(Enum column : tableColumns) {
            if (value.equals(column.toString()))
                return true;
        }
        return false;
    }

}
