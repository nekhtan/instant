package com.instant.api.model;

import lombok.NonNull;
import lombok.Value;

/**
 * JSON representation of a position.
 */
@Value
public class PositionDto {

    @NonNull
    String latitude;

    @NonNull
    String longitude;
}
