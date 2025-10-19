package com.instant.data.util;

import com.instant.data.entity.Position;

import org.springframework.stereotype.Component;

/**
 * Utility class to calculate the distance between 2 {@link Position}s.
 */
@Component
public class DistanceCalculator {

    private static final int EARTH_RADIUS = 6371;

    /**
     * Calculates the distance in meters between 2 positions.
     * <p>
     * Based on Haversine formula.
     *
     * @param position1 the first position
     * @param position2 the second position
     * @return the distance in meters between the 2 positions
     *
     * @see <a href="https://stackoverflow.com/a/16794680">Source</a>
     * @see <a href="https://en.wikipedia.org/wiki/Haversine_formula">Wiki</a>
     */
    public double between(Position position1, Position position2) {
        double lat1 = Double.parseDouble(position1.getLatitude());
        double lon1 = Double.parseDouble(position1.getLongitude());
        double lat2 = Double.parseDouble(position2.getLatitude());
        double lon2 = Double.parseDouble(position2.getLongitude());

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = EARTH_RADIUS * c * 1000; // convert to meters
        double height = 0; // double height = a.elevation - b.elevation; -> Elevation difference not considered here

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }
}
