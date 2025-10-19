package com.instant.data.repository;

import com.instant.data.entity.City;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for {@link City} entities.
 */
@Repository
public interface CityRepository extends JpaRepository<City, UUID> {

    /**
     * Finds a city by its name, ignoring case sensitivity.
     *
     * @param name the name of the city
     * @return the city matching the given name, if found
     */
    Optional<City> findByNameIgnoreCase(String name);

}
