package com.instant.api.mapper;

import com.instant.api.model.DistanceDto;
import com.instant.api.model.ParkingLotDto;
import com.instant.data.entity.City;
import com.instant.data.entity.ParkingLot;
import com.instant.data.entity.ParkingLotDetails;
import com.instant.data.entity.Position;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ParkingLotMapperTest {

    private final ParkingLotMapper parkingLotMapper = Mappers.getMapper(ParkingLotMapper.class);

    @Nested
    class EntityToDto {

        @Test
        void shouldHandleNullEntity() {
            assertNull(parkingLotMapper.map((ParkingLot) null));
        }

        @Test
        void shouldMapToDto() {
            assertMappingOk(createParkingLot(), parkingLotMapper.map(createParkingLot()));
        }
    }

    @Nested
    class EntityToDtoWithDistance {

        @Test
        void shouldHandleNullEntity() {
            assertNull(parkingLotMapper.map(null, 1.0));
        }

        @Test
        void shouldHandleNullDistance() {
            ParkingLotDto parkingLotDto = parkingLotMapper.map(createParkingLot(), null);

            assertMappingOk(createParkingLot(), parkingLotDto);
            assertNull(parkingLotDto.getDistance());
        }

        @Test
        void shouldMapWithDistance() {
            ParkingLotDto parkingLotDto = parkingLotMapper.map(createParkingLot(), Math.PI * 10_000.0);

            assertMappingOk(createParkingLot(), parkingLotDto);
            assertEquals(31416, parkingLotDto.getDistance().getM());
            assertEquals(31.42, parkingLotDto.getDistance().getKm());
        }
    }

    @Nested
    class DistanceMapping {

        @Test
        void shouldHandleNull() {
            assertNull(parkingLotMapper.map((Double) null));
        }

        @Test
        void shouldMapDistance() {
            DistanceDto distanceDto = parkingLotMapper.map(Math.PI * 10_000.0);

            assertEquals(31416, distanceDto.getM());
            assertEquals(31.42, distanceDto.getKm());
        }
    }

    @Nested
    class CityMapping {

        @Test
        void shouldHandleNullCity() {
            assertNull(parkingLotMapper.map((City) null));
        }

        @Test
        void shouldMapCity() {
            assertEquals("Amsterdam", parkingLotMapper.map(new City("Amsterdam")));
        }
    }

    private static ParkingLot createParkingLot() {
        Position position = new Position();
        position.setLongitude("4.895168");
        position.setLatitude("52.370216");

        ParkingLotDetails details = new ParkingLotDetails();
        details.setCapacity(100);
        details.setAvailableSpaces(20);

        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setId(UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"));
        parkingLot.setName("Central Park");
        parkingLot.setCity(new City("New York"));
        parkingLot.setPosition(position);
        parkingLot.setDetails(details);
        parkingLot.setCreatedAt(Instant.parse("2016-07-12T22:23:45Z"));
        parkingLot.setModifiedAt(Instant.parse("2021-05-09T13:45:12Z"));

        return parkingLot;
    }

    private static void assertMappingOk(ParkingLot parkingLot, ParkingLotDto dto) {
        assertEquals(parkingLot.getId(), dto.getId());
        assertEquals(parkingLot.getName(), dto.getName());
        assertEquals(parkingLot.getCity().getName(), dto.getCity());
        assertEquals(parkingLot.getPosition().getLongitude(), dto.getPosition().getLongitude());
        assertEquals(parkingLot.getPosition().getLatitude(), dto.getPosition().getLatitude());
        assertEquals(parkingLot.getDetails().getCapacity(), dto.getDetails().getCapacity());
        assertEquals(parkingLot.getDetails().getAvailableSpaces(), dto.getDetails().getAvailableSpaces());
        assertEquals(parkingLot.getModifiedAt(), dto.getLastUpdate());
    }

}