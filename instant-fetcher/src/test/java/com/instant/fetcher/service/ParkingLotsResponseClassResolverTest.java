package com.instant.fetcher.service;

import com.instant.fetcher.model.SupportedCity;
import com.instant.fetcher.model.amp.AmpResponse;
import com.instant.fetcher.model.poitiers.PoitiersResponse;
import com.instant.fetcher.model.strasbourg.StrasbourgResponse;
import com.instant.fetcher.util.ParkingLotsResponseClassResolver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class ParkingLotsResponseClassResolverTest {

    private final ParkingLotsResponseClassResolver resolver = new ParkingLotsResponseClassResolver();

    @Test
    void shouldNotFindMissingCities() {
        assertNull(resolver.resolve("paris"));
    }

    @ParameterizedTest
    @EnumSource(SupportedCity.class)
    void shouldFindConfiguration(SupportedCity city) {
        assertNotNull(resolver.resolve(city.name()));
    }

    @Test
    void shouldFindConfiguration() {
        assertSame(StrasbourgResponse.class, resolver.resolve("strasbourg"));
        assertSame(PoitiersResponse.class, resolver.resolve("poitiers"));
        assertSame(AmpResponse.class, resolver.resolve("amp"));
    }
}