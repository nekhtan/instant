package com.instant.fetcher.model;

import java.util.Optional;

/**
 * The service response interface describing a parking lot.
 */
public interface ParkingLot {

    /**
     * The name of the parking lot.
     *
     * @return the name
     */
    String name();

    /**
     * The total capacity of the parking lot.
     *
     * @return the capacity
     */
    int capacity();

    /**
     * The number of available spots in the parking lot.
     *
     * @return the available spots
     */
    int availableSpots();

    /**
     * The geo-position of the parking lot.
     *
     * @return the position
     */
    Position position();

    /**
     * The city where the parking lot is located.
     * <p>
     * Optional, since the information might not be available in the service response (e.g. city-dedicated services).
     *
     * @return the city
     */
    default Optional<String> city() {
        return Optional.empty();
    }

}
