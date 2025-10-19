package com.instant.data.service;

import com.instant.data.entity.City;
import com.instant.data.repository.CityRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityService cityService;

    @Mock
    private City city;

    @Mock
    private City storedCity;

    @Test
    void shouldFetchByCityFromRepo() {
        when(cityRepository.findByNameIgnoreCase("nice")).thenReturn(Optional.of(city));

        assertEquals(city, cityService.findByName("nice").orElseThrow());
    }

    @Test
    void shouldFetchAllFromRepo() {
        when(cityRepository.save(city)).thenReturn(storedCity);

        assertEquals(storedCity, cityService.save(city));
    }

}