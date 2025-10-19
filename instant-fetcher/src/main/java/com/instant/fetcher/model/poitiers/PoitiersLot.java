package com.instant.fetcher.model.poitiers;

import lombok.Data;
import lombok.experimental.Accessors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.instant.fetcher.model.ParkingLot;
import com.instant.fetcher.model.Position;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parking lot information for Poitiers.
 */
@Data
@Accessors(fluent = true)
public class PoitiersLot implements ParkingLot {

    private static final Pattern POSITION_PATTERN = Pattern.compile("(\\d+.\\d+),\\s*(\\d+.\\d+)");

    @JsonProperty("Nom")
    private String name;

    @JsonProperty("Capacite")
    private int capacity;

    @JsonProperty("Places")
    private int availableSpots;

    @JsonProperty("_geopoint")
    private String points;

    @JsonIgnore
    private PoitiersPosition position;

    @Override
    public Position position() {
        if (position == null && StringUtils.hasText(points)) {
            Matcher matcher = POSITION_PATTERN.matcher(points);

            if (!matcher.matches() || matcher.groupCount() != 2) {
                throw new RuntimeException("Position does not match pattern: " + points + " with pattern: " + POSITION_PATTERN.pattern());
            }

            PoitiersPosition poitiersPosition = new PoitiersPosition();
            poitiersPosition.latitude(matcher.group(1).trim());
            poitiersPosition.longitude(matcher.group(2).trim());

            position = poitiersPosition;
        }
        return position;
    }

}
