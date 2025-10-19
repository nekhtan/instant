package com.instant.fetcher.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.instant.fetcher.model.ParkingLot;
import com.instant.fetcher.model.ParkingLotsResponse;
import com.instant.fetcher.properties.SourceConfiguration;
import com.instant.fetcher.util.ParkingLotsResponseClassResolver;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service to fetch parking lot data from external sources.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class DataFetchService {

    private final ParkingLotsResponseClassResolver responseClassResolver;
    private final RestService restService;

    /**
     * Fetches parking lot data from the given source.
     *
     * @param source the source configuration
     * @return the list of parking lots fetched from the source
     */
    List<ParkingLot> fetchData(@NonNull SourceConfiguration source) {
        ResponseEntity<? extends ParkingLotsResponse<?>> data = restService.get(source.getUrl(), responseClassResolver.resolve(source.getCity()));
        if (data == null) {
            log.error("Could not refresh data for source {}, no data found", source.getUrl());
            return List.of();
        }

        ParkingLotsResponse<?> response = data.getBody();
        if (response == null) {
            log.error("Could not refresh data for source {}, empty body", source.getUrl());
            return List.of();
        }
        log.info("Successfully refreshed data for source {}: {}", source.getUrl(), response);

        @SuppressWarnings("unchecked") // Response is already typed
        List<ParkingLot> parkingLots = (List<ParkingLot>) response.parkingLots();
        if (parkingLots == null) {
            log.error("Could not refresh data for source {}, no parking lots found: {}", source.getUrl(), response);
            return List.of();
        }

        return parkingLots;
    }
}
