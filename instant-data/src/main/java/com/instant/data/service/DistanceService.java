package com.instant.data.service;

import lombok.RequiredArgsConstructor;

import com.instant.data.entity.City;
import com.instant.data.entity.ParkingLot;
import com.instant.data.entity.Position;
import com.instant.data.util.DistanceCalculator;

import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SequencedMap;
import java.util.stream.Collectors;

/**
 * Service for distance calculations and related operations.
 */
@Service
@RequiredArgsConstructor
public class DistanceService {

    private final CityService cityService;
    private final ParkingLotService parkingLotService;
    private final DistanceCalculator distanceCalculator;

    /**
     * Finds parking lots around a given position within a certain radius.
     * <p>
     * While currently the cities don't have positions, the method tries to narrow down the search of parking lots
     * by first finding the nearest city to the source position.
     *
     * @param position the source position
     * @param radiusMeters the search radius in meters
     * @return a sequenced map of parking lots and their distances from the source position, sorted by distance
     */
    public SequencedMap<ParkingLot, Double> getParkingLotsAround(Position position, double radiusMeters) {
        List<City> allCities = cityService.findAll();

        // Could loop and increase radius until we find cities, but for now just one is enough
        Optional<City> nearestCity = allCities.stream()
            .filter(c -> c.getPosition() != null)
            .min(Comparator.comparingDouble(city -> distanceCalculator.between(city.getPosition(), position)));

        // Either all cities should have positions (mandatory) or none, otherwise results could be crazy
        List<ParkingLot> parkingLots = nearestCity.isPresent()
            ? parkingLotService.findAllByCityName(nearestCity.get().getName())
            : parkingLotService.findAll();

        return parkingLots.stream()
            .filter(lot -> lot.getPosition() != null)
            .map(lot -> new AbstractMap.SimpleEntry<>(lot, distanceCalculator.between(lot.getPosition(), position)))
            .filter(entry -> entry.getKey().getPosition() != null && entry.getValue() <= radiusMeters)
            .sorted(Map.Entry.comparingByValue())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (d1, d2) -> d1, LinkedHashMap::new));
    }

}
