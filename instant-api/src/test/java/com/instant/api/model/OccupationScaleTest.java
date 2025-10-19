package com.instant.api.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OccupationScaleTest {

    @Test
    void shouldSetScaleToCritical() {
        assertEquals(OccupationScale.CRITICAL, OccupationScale.from(0.9));
        assertEquals(OccupationScale.CRITICAL, OccupationScale.from(0.95));
        assertEquals(OccupationScale.CRITICAL, OccupationScale.from(0.99));
    }

    @Test
    void shouldSetScaleToFull() {
        assertEquals(OccupationScale.FULL, OccupationScale.from(1.0));
        assertEquals(OccupationScale.FULL, OccupationScale.from(1.1));
        assertEquals(OccupationScale.FULL, OccupationScale.from(2.0));
    }

    @Test
    void shouldSetScaleToHigh() {
        assertEquals(OccupationScale.HIGH, OccupationScale.from(0.7));
        assertEquals(OccupationScale.HIGH, OccupationScale.from(0.75));
        assertEquals(OccupationScale.HIGH, OccupationScale.from(0.89));
    }

    @Test
    void shouldSetScaleToMedium() {
        assertEquals(OccupationScale.MEDIUM, OccupationScale.from(0.4));
        assertEquals(OccupationScale.MEDIUM, OccupationScale.from(0.5));
        assertEquals(OccupationScale.MEDIUM, OccupationScale.from(0.69));
    }

    @Test
    void shouldSetScaleToLow() {
        assertEquals(OccupationScale.LOW, OccupationScale.from(0.1));
        assertEquals(OccupationScale.LOW, OccupationScale.from(0.2));
        assertEquals(OccupationScale.LOW, OccupationScale.from(0.39));
    }

    @Test
    void shouldSetScaleToEmpty() {
        assertEquals(OccupationScale.EMPTY, OccupationScale.from(0.0));
        assertEquals(OccupationScale.EMPTY, OccupationScale.from(-0.1));
        assertEquals(OccupationScale.EMPTY, OccupationScale.from(null));
    }
}