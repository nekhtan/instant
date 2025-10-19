package com.instant.fetcher.model.poitiers;

import lombok.Data;
import lombok.experimental.Accessors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.instant.fetcher.model.ParkingLotsResponse;
import com.instant.fetcher.model.ParkingLotsResponseFor;
import com.instant.fetcher.model.SupportedCity;

import java.util.List;

/**
 * Response for Poitiers.
 */
@Data
@Accessors(fluent = true)
@ParkingLotsResponseFor(city = SupportedCity.POITIERS)
public class PoitiersResponse implements ParkingLotsResponse<PoitiersLot> {

    @JsonProperty("total_count")
    int total;

    @JsonProperty("results")
    List<PoitiersLot> parkingLots;

}
