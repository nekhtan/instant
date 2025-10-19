package com.instant.fetcher.model.amp;

import lombok.Data;
import lombok.experimental.Accessors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.instant.fetcher.model.ParkingLot;

import java.util.Optional;

/**
 * Parking lot information for AMP (Aix-Marseille-Provence).
 */
@Data
@Accessors(fluent = true)
public class AmpLot implements ParkingLot {

    @JsonProperty("nom")
    private String name;

    @JsonProperty("voitureplacescapacite")
    private int capacity;

    @JsonProperty("voitureplacesdisponibles")
    private int availableSpots;

    @JsonProperty("commune")
    private String cityName;

    @JsonProperty("pointgeo")
    private AmpPosition position;

    @Override
    public Optional<String> city() {
        return Optional.ofNullable(cityName);
    }
}
