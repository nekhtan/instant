package com.instant.data.util;

import com.instant.data.entity.Position;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DistanceCalculatorTest {

    private final DistanceCalculator distanceCalculator = new DistanceCalculator();

    @Test
    void shouldCalculateDistanceBetweenTwoPositions() {
        Position nice = new Position("43.7031300", "7.2660800");
        Position paris = new Position("48.864716", "2.349014");

        double distance = distanceCalculator.between(nice, paris);

        assertEquals(686820.9156956414, distance);
    }
}