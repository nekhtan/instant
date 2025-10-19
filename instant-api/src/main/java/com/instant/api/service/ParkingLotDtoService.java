package com.instant.api.service;

import com.instant.api.mapper.ParkingLotMapper;
import com.instant.api.mapper.PositionMapper;
import com.instant.api.model.ParkingLotDto;
import com.instant.api.model.PositionDto;
import com.instant.data.service.DistanceService;
import com.instant.data.service.ParkingLotService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.SequencedCollection;
import java.util.stream.Collectors;

/**
 * Service that manages parking lot DTOs.
 */
@RequiredArgsConstructor
@Service
public class ParkingLotDtoService {

    private final ParkingLotService parkingLotService;
    private final DistanceService distanceService;
    private final ParkingLotMapper parkingLotMapper;
    private final PositionMapper positionMapper;

    /**
     * Get all parking lots from a given city.
     *
     * @param city the city name
     * @param pageable the pagination information
     * @return the parking lots of the city
     */
    @Cacheable("parking-lots")
    public Page<ParkingLotDto> getAllFrom(@NonNull String city, @NonNull Pageable pageable) {
        return parkingLotService.findAllByCityName(city, pageable)
            .map(parkingLotMapper::map);
    }

    /**
     * Get all parking lots.
     *
     * @param pageable the pagination information
     * @return all the parking lots
     */
    @Cacheable("parking-lots")
    public Page<ParkingLotDto> getAll(@NonNull Pageable pageable) {
        return parkingLotService.findAll(pageable)
            .map(parkingLotMapper::map);
    }

    /**
     * Get parking lots around a given position within a certain radius.
     *
     * @param positionDto the source position
     * @param radiusInKm the radius (in kilometers) to search
     * @return the parking lots around the position
     */
    public SequencedCollection<ParkingLotDto> getLotsAround(@NonNull PositionDto positionDto, int radiusInKm) {
        return distanceService.getParkingLotsAround(positionMapper.map(positionDto), radiusInKm * 1000.0)
            .entrySet()
            .stream()
            .map(p -> parkingLotMapper.map(p.getKey(), p.getValue()))
            .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
