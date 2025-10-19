package com.instant.api.service;

import com.instant.api.mapper.ParkingLotMapper;
import com.instant.api.model.ParkingLotDto;
import com.instant.data.entity.ParkingLot;
import com.instant.data.service.ParkingLotService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParkingLotDtoServiceTest {

    @Mock
    private ParkingLotService parkingLotService;

    @Mock
    private ParkingLotMapper parkingLotMapper;

    @InjectMocks
    private ParkingLotDtoService parkingLotDtoService;

    @Mock
    private Pageable pageable;

    @Mock
    private ParkingLot parkingLot;

    @Mock
    private ParkingLotDto parkingLotDto;

    @Test
    void shouldFetchFromCityAndMap() {
        when(parkingLotService.findAllByCityName("nice", pageable)).thenReturn(new PageImpl<>(List.of(parkingLot)));
        when(parkingLotMapper.map(parkingLot)).thenReturn(parkingLotDto);

        assertEquals(new PageImpl<>(List.of(parkingLotDto)), parkingLotDtoService.getAllFrom("nice", pageable));
    }

    @Test
    void shouldFetchAllAndMap() {
        when(parkingLotService.findAll(pageable)).thenReturn(new PageImpl<>(List.of(parkingLot)));
        when(parkingLotMapper.map(parkingLot)).thenReturn(parkingLotDto);

        assertEquals(new PageImpl<>(List.of(parkingLotDto)), parkingLotDtoService.getAll(pageable));
    }

}