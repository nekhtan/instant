package com.instant.fetcher.model.strasbourg;

import lombok.Data;
import lombok.experimental.Accessors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.instant.fetcher.model.ParkingLotsResponse;
import com.instant.fetcher.model.ParkingLotsResponseFor;
import com.instant.fetcher.model.SupportedCity;

import java.util.List;

/**
 * Response for Strasbourg.
 */
@Data
@Accessors(fluent = true)
@ParkingLotsResponseFor(city = SupportedCity.STRASBOURG)
public class StrasbourgResponse implements ParkingLotsResponse<StrasbourgLot> {

    @JsonProperty("total_count")
    int total;

    @JsonProperty("results")
    List<StrasbourgLot> parkingLots;

}
