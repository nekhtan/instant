package com.instant.api.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import com.instant.api.util.DoubleHelper;

/**
 * JSON representation of parking lot details.
 */
@Value
public class ParkingLotDetailsDto {

    @NonNull
    Integer capacity;

    @NonNull
    Integer availableSpaces;

    Double occupationRate;
    OccupationScale occupationScale;

    @Builder(toBuilder = true)
    public ParkingLotDetailsDto(@NonNull Integer capacity, @NonNull Integer availableSpaces) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative");
        }
        if (availableSpaces < 0) {
            throw new IllegalArgumentException("Available spaces cannot be negative");
        }

        this.capacity = capacity;
        this.availableSpaces = availableSpaces;

        if (availableSpaces > capacity) { // Should we reject this case instead?
            occupationRate = 0.0;
        }
        else {
            occupationRate = DoubleHelper.twoDigits(1 - (capacity != 0 ? (double) availableSpaces / capacity : 0.0));
        }

        occupationScale = OccupationScale.from(occupationRate);
    }
}