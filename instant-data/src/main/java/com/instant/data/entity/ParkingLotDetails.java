package com.instant.data.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Min;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Details of a {@link ParkingLot}.
 */
@Data
@Accessors(chain = true)
@Embeddable
public class ParkingLotDetails {

    @Min(0)
    private Integer capacity;

    @Min(0)
    private Integer availableSpaces;

}
