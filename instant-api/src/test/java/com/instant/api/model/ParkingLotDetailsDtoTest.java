package com.instant.api.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParkingLotDetailsDtoTest {

    @ParameterizedTest
    @CsvSource({
        "100, 0, 1.0, FULL",
        "100, 10, 0.9, CRITICAL",
        "100, 25, 0.75, HIGH",
        "100, 50, 0.5, MEDIUM",
        "100, 75, 0.25, LOW",
        "100, 100, 0.0, EMPTY",
        "100, 200, 0.0, EMPTY"
    })
    void shouldCalculateOccupationRateAndScale(int capacity, int availableSpaces, double expectedRate, OccupationScale expectedScale) {
        ParkingLotDetailsDto details = new ParkingLotDetailsDto(capacity, availableSpaces);
        assertEquals(expectedRate, details.getOccupationRate());
        assertEquals(expectedScale, details.getOccupationScale());
    }
}