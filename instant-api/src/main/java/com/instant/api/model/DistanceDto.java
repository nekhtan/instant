package com.instant.api.model;

import lombok.Value;

/**
 * JSON representation of a distance.
 */
@Value
public class DistanceDto {

    Integer m;
    Double km;

}
