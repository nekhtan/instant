package com.instant.data.service;

import com.instant.data.entity.ParkingLot;
import com.instant.data.entity.Position;
import com.instant.data.util.DistanceCalculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.SequencedMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DistanceServiceTest {

    @Mock
    private CityService cityService;

    @Mock
    private ParkingLotService parkingLotService;

    @Mock
    private DistanceCalculator distanceCalculator;

    @InjectMocks
    private DistanceService distanceService;

    @Mock
    private ParkingLot parkingLotNear;

    @Mock
    private ParkingLot parkingLotFar;

    @Mock
    private Position positionSource;

    @Mock
    private Position positionNear;

    @Mock
    private Position positionFar;

    @BeforeEach
    void setup() {
        when(cityService.findAll()).thenReturn(List.of());
        when(parkingLotService.findAll()).thenReturn(List.of(parkingLotNear, parkingLotFar));
        when(parkingLotFar.getPosition()).thenReturn(positionFar);
        when(parkingLotNear.getPosition()).thenReturn(positionNear);
        when(distanceCalculator.between(positionNear, positionSource)).thenReturn(50.0);
        when(distanceCalculator.between(positionFar, positionSource)).thenReturn(500.0);
    }

    @Test
    void shouldFindExcludeFarAwayLots() {
        SequencedMap<ParkingLot, Double> lots = distanceService.getParkingLotsAround(positionSource, 100.0);

        assertEquals(Map.of(parkingLotNear, 50.0), lots);
    }

    @Test
    void shouldSortFromNearestToFarthest() {
        SequencedMap<ParkingLot, Double> lots = distanceService.getParkingLotsAround(positionSource, 500.0);

        assertEquals(Map.of(parkingLotNear, 50.0, parkingLotFar, 500.0), lots);
    }
}