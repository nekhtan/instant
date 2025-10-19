package com.instant.api.model;

/**
 * Occupation scale enumeration.
 */
public enum OccupationScale {

    /**
     * Parking lot is empty.
     */
    EMPTY,

    /**
     * Parking lot has low occupation.
     */
    LOW,

    /**
     * Parking lot has medium occupation.
     */
    MEDIUM,

    /**
     * Parking lot has high occupation.
     */
    HIGH,

    /**
     * Parking lot is critically occupied.
     */
    CRITICAL,

    /**
     * Parking lot is full.
     */
    FULL;

    /**
     * Get the occupation scale from the occupation rate.
     *
     * @param occupationRate the occupation rate
     * @return the occupation scale
     */
    public static OccupationScale from(Double occupationRate) {
        if (occupationRate == null || occupationRate <= 0) {
            return EMPTY;
        }
        if (occupationRate >= 1) {
            return FULL;
        }
        if (occupationRate >= 0.9) {
            return CRITICAL;
        }
        if (occupationRate >= 0.7) {
            return HIGH;
        }
        if (occupationRate >= 0.4) {
            return MEDIUM;
        }
        return LOW;
    }
}
