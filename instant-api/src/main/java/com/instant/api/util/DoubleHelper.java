package com.instant.api.util;

import lombok.experimental.UtilityClass;

/**
 * Utility class for Double operations.
 */
@UtilityClass
public class DoubleHelper {

    /**
     * Rounds a double to two decimal places.
     *
     * @param number the number to round
     * @return the rounded number
     */
    public static Double twoDigits(Double number) {
        if (number == null) {
            return null;
        }
        return Math.round(number * 100.0) / 100.0;
    }
}
