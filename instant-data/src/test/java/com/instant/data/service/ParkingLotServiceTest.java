package com.instant.data.service;

import com.instant.data.entity.ParkingLot;
import com.instant.data.repository.ParkingLotRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParkingLotServiceTest {

    @Mock
    private ParkingLotRepository parkingLotRepository;

    @InjectMocks
    private ParkingLotService parkingLotService;

    @Mock
    private ParkingLot parkingLot;

    @Mock
    private ParkingLot storedParkingLot;

    @Mock
    private Pageable pageable;

    @Test
    void shouldSaveAll() {
        when(parkingLotRepository.saveAll(List.of(parkingLot))).thenReturn(List.of(storedParkingLot));

        assertEquals(List.of(storedParkingLot), parkingLotService.saveAll(List.of(parkingLot)));
    }

    @Test
    void shouldFindAll() {
        when(parkingLotRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(storedParkingLot)));

        assertEquals(new PageImpl<>(List.of(storedParkingLot)), parkingLotService.findAll(pageable));
    }

    @Test
    void shouldFindAllByCityNamePaged() {
        when(parkingLotRepository.findAllByCityName("nice", pageable)).thenReturn(new PageImpl<>(List.of(storedParkingLot)));

        assertEquals(new PageImpl<>(List.of(storedParkingLot)), parkingLotService.findAllByCityName("nice", pageable));
    }

    @Test
    void shouldFindAllByCityName() {
        when(parkingLotRepository.findAllByCityName("nice")).thenReturn(List.of(storedParkingLot));

        assertEquals(List.of(storedParkingLot), parkingLotService.findAllByCityName("nice"));
    }

    @Test
    void shouldFindByNameAndCityName() {
        when(parkingLotRepository.findByNameAndCityName("massena", "nice")).thenReturn(Optional.of(storedParkingLot));

        assertEquals(Optional.of(storedParkingLot), parkingLotService.findByNameAndCity("massena", "nice"));
    }

}