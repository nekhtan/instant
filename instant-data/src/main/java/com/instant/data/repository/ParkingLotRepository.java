package com.instant.data.repository;

import lombok.NonNull;

import com.instant.data.entity.ParkingLot;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for {@link ParkingLot} entities.
 */
@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot, UUID> {

    /**
     * Finds a parking lot by its name and the name of its city, ignoring case sensitivity.
     *
     * @param name the name of the parking lot
     * @param cityName the name of the city
     * @return the parking lot matching the given name and city, if found
     */
    @Query("SELECT p FROM ParkingLot p " +
            "WHERE LOWER(p.name) = LOWER(:name) " +
            "AND LOWER(p.city.name) = LOWER(:cityName) " +
            "ORDER BY p.city.name ASC, p.name ASC")
    Optional<ParkingLot> findByNameAndCityName(@NonNull String name, @NonNull String cityName);

    /**
     * Finds all parking lots in a given city, ordered by name ascending.
     *
     * @param cityName the name of the city
     * @param pageable the pagination information
     * @return the parking lots in the specified city
     */
    @Query("SELECT p FROM ParkingLot p " +
            "WHERE LOWER(p.city.name) = LOWER(:cityName) " +
            "ORDER BY p.name ASC")
    Page<ParkingLot> findAllByCityName(@NonNull String cityName, @NonNull Pageable pageable);

    /**
     * Finds all parking lots in a given city, ordered by name ascending.
     *
     * @param cityName the name of the city
     * @return the parking lots in the specified city
     */
    @Query("SELECT p FROM ParkingLot p " +
            "WHERE LOWER(p.city.name) = LOWER(:cityName) " +
            "ORDER BY p.name ASC")
    List<ParkingLot> findAllByCityName(@NonNull String cityName);

    /**
     * Finds all parking lots, ordered by city name ascending and then by parking lot name ascending.
     *
     * @param pageable the pagination information
     * @return all parking lots
     */
    @Override
    @Query("SELECT p FROM ParkingLot p " +
            "ORDER BY p.city.name ASC, p.name ASC")
    Page<ParkingLot> findAll(@NonNull Pageable pageable);
}
