package com.instant.fetcher.model.amp;

import lombok.Data;
import lombok.experimental.Accessors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.instant.fetcher.model.Position;

/**
 * Position for AMP response.
 */
@Data
@Accessors(fluent = true)
public class AmpPosition implements Position {

    @JsonProperty("lat")
    private String latitude;

    @JsonProperty("lon")
    private String longitude;

}
