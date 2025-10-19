package com.instant.data.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import com.instant.data.entity.City;
import com.instant.data.repository.CityRepository;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Service to handle {@link City} entities.
 */
@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    /**
     * Finds a city by its name, ignoring case sensitivity.
     *
     * @param name the name of the city to find
     * @return the city matching the given name, if found
     */
    public Optional<City> findByName(String name) {
        return cityRepository.findByNameIgnoreCase(name);
    }

    /**
     * Finds all city entities in the repository.
     *
     * @return the list of all city entities
     */
    public List<City> findAll() {
        return cityRepository.findAll();
    }

    /**
     * Saves a city entity to the repository.
     *
     * @param city the city entity to save
     * @return the saved city entity
     */
    public City save(City city) {
        return cityRepository.save(city);
    }

    /**
     * Persists cities based on the given city names.
     * <p>
     * If a city already exists in the repository, it will be reused.
     *
     * @param cityNames the city names to persist
     * @return the persisted {@link City}s by their name
     */
    public Map<String, City> load(@NonNull Set<String> cityNames) {
        Map<String, City> cities = new HashMap<>();

        for (String cityName : cityNames) {
            cities.computeIfAbsent(cityName, k -> findByName(cityName).orElseGet(() -> save(new City(cityName))));
        }

        return cities;
    }
}
