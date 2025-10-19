package com.instant.api.controller;

import com.instant.api.model.ParkingLotDto;
import com.instant.api.model.PositionDto;
import com.instant.api.service.ParkingLotDtoService;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.SequencedCollection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParkingLotControllerTest {

    @Mock
    private ParkingLotDtoService parkingLotDtoService;

    @InjectMocks
    private ParkingLotController parkingLotController;

    @Mock
    private Pageable pageable;

    @Mock
    private Page<ParkingLotDto> page;

    @Nested
    class GetAllIn {

        @Test
        void shouldGetEmptyFromService() {
            when(parkingLotDtoService.getAllFrom("nice", pageable)).thenReturn(Page.empty());

            ResponseEntity<Page<ParkingLotDto>> response = parkingLotController.getAllIn("nice", pageable);

            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
            assertNull(response.getBody());
        }

        @Test
        void shouldGetFromService() {
            when(page.hasContent()).thenReturn(true);
            when(parkingLotDtoService.getAllFrom("nice", pageable)).thenReturn(page);

            ResponseEntity<Page<ParkingLotDto>> response = parkingLotController.getAllIn("nice", pageable);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(page, response.getBody());
        }
    }

    @Nested
    class GetAll {

        @Test
        void shouldGetEmptyFromService() {
            when(parkingLotDtoService.getAll(pageable)).thenReturn(Page.empty());

            ResponseEntity<Page<ParkingLotDto>> response = parkingLotController.getAll(pageable);

            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
            assertNull(response.getBody());
        }

        @Test
        void shouldGetFromService() {
            when(page.hasContent()).thenReturn(true);
            when(parkingLotDtoService.getAll(pageable)).thenReturn(page);

            ResponseEntity<Page<ParkingLotDto>> response = parkingLotController.getAll(pageable);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(page, response.getBody());
        }
    }

    @Nested
    class Near {

        @Mock
        private SequencedCollection<ParkingLotDto> nearbyLots;

        @Test
        void shouldGetEmptyFromService() {
            when(nearbyLots.isEmpty()).thenReturn(true);
            when(parkingLotDtoService.getLotsAround(new PositionDto("1.0", "2.0"), 10)).thenReturn(nearbyLots);

            ResponseEntity<SequencedCollection<ParkingLotDto>> response = parkingLotController.getNear("1.0", "2.0", 10);

            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
            assertNull(response.getBody());
        }

        @Test
        void shouldGetFromService() {
            when(nearbyLots.isEmpty()).thenReturn(false);
            when(parkingLotDtoService.getLotsAround(new PositionDto("1.0", "2.0"), 10)).thenReturn(nearbyLots);

            ResponseEntity<SequencedCollection<ParkingLotDto>> response = parkingLotController.getNear("1.0", "2.0", 10);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(nearbyLots, response.getBody());
        }
    }
}