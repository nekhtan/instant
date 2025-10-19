package com.instant.fetcher.service;

import jakarta.transaction.Transactional;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.instant.data.entity.City;
import com.instant.data.entity.ParkingLot;
import com.instant.data.service.CityService;
import com.instant.data.service.ParkingLotService;
import com.instant.fetcher.properties.SourceConfiguration;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service to refresh parking lot data from external sources.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DataRefreshService {

    private final CityService cityService;
    private final ParkingLotService parkingLotService;
    private final DataFetchService dataFetchService;
    private final ParkingLotUpdaterService parkingLotUpdater;

    /**
     * Refreshes parking lot data for the given source: calls the service, updates existing entities, and persists changes.
     *
     * @param source the source configuration
     */
    @Transactional
    void refreshDataForSource(@NonNull SourceConfiguration source) {
        List<com.instant.fetcher.model.ParkingLot> parkingLots = dataFetchService.fetchData(source);

        // Some services provide parking lots in multiple cities (e.g. AMP) -> load them once and for all
        Map<String, City> citiesByName = initCities(parkingLots, source);

        List<ParkingLot> updatedParkingLots = new ArrayList<>();
        for (com.instant.fetcher.model.ParkingLot parkingLotDto : parkingLots) {
            City city = citiesByName.get(parkingLotDto.city().orElse(source.getCity()));

            ParkingLot parkingLotEntity = parkingLotService
                .findByNameAndCity(parkingLotDto.name(), city.getName())
                .orElseGet(() -> {
                    log.info("{}/{}: creating...", city.getName(), parkingLotDto.name());
                    return new ParkingLot();
                });

            if (parkingLotUpdater.update(parkingLotEntity, parkingLotDto, city)) {
                updatedParkingLots.add(parkingLotEntity);
            }
        }

        log.info("Persisting {} parking lots for source={}", updatedParkingLots.size(), source.getCity());
        parkingLotService.saveAll(updatedParkingLots);
    }

    private Map<String, City> initCities(List<com.instant.fetcher.model.ParkingLot> parkingLots, SourceConfiguration source) {

        // Extract the cities provided by the parking lot data
        Set<Optional<String>> cityNames = parkingLots.stream()
            .map(com.instant.fetcher.model.ParkingLot::city)
            .collect(Collectors.toSet());

        // If any of the parking lots does not provide a city, we add the default city from the source configuration
        if (cityNames.stream().anyMatch(Optional::isEmpty)) {
            cityNames.add(Optional.of(source.getCity()));
        }

        // Load or create the cities in the database
        return cityService.load(cityNames.stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toSet()));
    }

}