package com.instant.data.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import com.instant.data.entity.ParkingLot;
import com.instant.data.repository.ParkingLotRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Service to handle {@link ParkingLot} entities.
 */
@Service
@RequiredArgsConstructor
public class ParkingLotService {

    private final ParkingLotRepository parkingLotRepository;

    /**
     * Persists all given parking lots.
     *
     * @param parkingLots the parking lots to persist
     * @return the persisted parking lots
     */
    public List<ParkingLot> saveAll(@NonNull Collection<ParkingLot> parkingLots) {
        return parkingLots.isEmpty()
            ? List.of()
            : parkingLotRepository.saveAll(parkingLots);
    }

    /**
     * Finds all parking lots with pagination.
     *
     * @param pageable the pagination information
     * @return the page of parking lots
     */
    public Page<ParkingLot> findAll(@NonNull Pageable pageable) {
        return parkingLotRepository.findAll(pageable);
    }

    /**
     * Finds all parking lots.
     *
     * @return the list of all parking lots
     */
    public List<ParkingLot> findAll() {
        return parkingLotRepository.findAll();
    }

    /**
     * Finds all parking lots by city name with pagination.
     *
     * @param cityName the city name
     * @param pageable the pagination information
     * @return the page of parking lots in the specified city
     */
    public Page<ParkingLot> findAllByCityName(@NonNull String cityName, @NonNull Pageable pageable) {
        return parkingLotRepository.findAllByCityName(cityName, pageable);
    }

    /**
     * Finds all parking lots by city name.
     *
     * @param cityName the city name
     * @return the list of parking lots in the specified city
     */
    public List<ParkingLot> findAllByCityName(@NonNull String cityName) {
        return parkingLotRepository.findAllByCityName(cityName);
    }

    /**
     * Finds a parking lot by its name and city name.
     *
     * @param name the parking lot name
     * @param cityName the city name
     * @return the optional parking lot
     */
    public Optional<ParkingLot> findByNameAndCity(@NonNull String name, @NonNull String cityName) {
        return parkingLotRepository.findByNameAndCityName(name, cityName);
    }
}
