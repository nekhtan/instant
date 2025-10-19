package com.instant.fetcher.model.strasbourg;

import lombok.Data;
import lombok.experimental.Accessors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.instant.fetcher.model.Position;

/**
 * Position for Strasbourg response.
 */
@Data
@Accessors(fluent = true)
public class StrasbourgPosition implements Position {

    @JsonProperty("lat")
    private String latitude;

    @JsonProperty("lon")
    private String longitude;

}
