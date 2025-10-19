package com.instant.fetcher.model.strasbourg;

import lombok.Data;
import lombok.experimental.Accessors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.instant.fetcher.model.ParkingLot;

/**
 * Parking lot information for Strasbourg.
 */
@Data
@Accessors(fluent = true)
public class StrasbourgLot implements ParkingLot {

    @JsonProperty("nom_parking")
    private String name;

    @JsonProperty("total")
    private int capacity;

    @JsonProperty("libre")
    private int availableSpots;

    @JsonProperty("position")
    private StrasbourgPosition position;

}
