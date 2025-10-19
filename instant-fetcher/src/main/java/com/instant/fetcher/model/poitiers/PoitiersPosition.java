package com.instant.fetcher.model.poitiers;

import lombok.Data;
import lombok.experimental.Accessors;

import com.instant.fetcher.model.Position;

/**
 * Position for Poitiers response.
 */
@Data
@Accessors(fluent = true)
public class PoitiersPosition implements Position {
    private String latitude;
    private String longitude;
}
