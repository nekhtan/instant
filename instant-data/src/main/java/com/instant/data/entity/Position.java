package com.instant.data.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Position of {@link ParkingLot}.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
@Embeddable
public class Position {

    @NotNull
    private String latitude;

    @NotNull
    private String longitude;

}
