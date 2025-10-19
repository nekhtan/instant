package com.instant.fetcher.model.amp;

import lombok.Data;
import lombok.experimental.Accessors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.instant.fetcher.model.ParkingLotsResponse;
import com.instant.fetcher.model.ParkingLotsResponseFor;
import com.instant.fetcher.model.SupportedCity;

import java.util.List;

/**
 * Response for AMP (Aix-Marseille-Provence).
 */
@Data
@Accessors(fluent = true)
@ParkingLotsResponseFor(city = SupportedCity.AMP)
public class AmpResponse implements ParkingLotsResponse<AmpLot> {

    @JsonProperty("total_count")
    int total;

    @JsonProperty("results")
    List<AmpLot> parkingLots;

}
