package com.instant.api.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

/**
 * JSON representation of a parking lot.
 */
@Value
@Builder(toBuilder = true)
public class ParkingLotDto {

    @NonNull
    UUID id;

    @NonNull
    String name;

    @NonNull
    String city;

    @NonNull
    ParkingLotDetailsDto details;

    DistanceDto distance;
    PositionDto position;

    Instant lastUpdate;

}
