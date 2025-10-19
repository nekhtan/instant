package com.instant.data.repository;

import jakarta.transaction.Transactional;

import com.instant.data.entity.City;
import com.instant.data.entity.ParkingLot;
import com.instant.data.entity.ParkingLotDetails;
import com.instant.data.entity.Position;
import com.instant.data.integration.ITConfiguration;
import com.instant.data.integration.PostgreSQLExtension;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = ITConfiguration.class)
@ExtendWith(PostgreSQLExtension.class)
@ActiveProfiles("IT")
class ParkingLotRepositoryIT {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ParkingLotRepository repository;

    private ParkingLot centralPark;
    private ParkingLot manhattan;
    private ParkingLot massena;

    @BeforeEach
    void setup() {
        City newYork = createCity("New York");
        City nice = createCity("Nice");

        centralPark = createParkingLot("Central Park", newYork);
        manhattan = createParkingLot("Manhattan", newYork);
        massena = createParkingLot("Massena", nice);
    }

    @Test
    @Transactional
    void shouldFindByNameAndCity() {
        ParkingLot found = repository.findByNameAndCityName("central park", "new york").orElseThrow();

        assertEquals(centralPark, found);
    }

    @Test
    @Transactional
    void shouldFindByCityPaged() {
        Page<ParkingLot> found = repository.findAllByCityName("new york", PageRequest.of(0, 10));

        assertEquals(List.of(centralPark, manhattan), found.stream().toList());
    }

    @Test
    @Transactional
    void shouldFindByCity() {
        List<ParkingLot> found = repository.findAllByCityName("new york");

        assertEquals(List.of(centralPark, manhattan), found.stream().toList());
    }

    @Test
    @Transactional
    void shouldFindAll() {
        Page<ParkingLot> found = repository.findAll(PageRequest.of(0, 10));

        assertEquals(List.of(centralPark, manhattan, massena), found.stream().toList());
    }

    private City createCity(String name) {
        City city = new City();
        city.setName(name);

        return cityRepository.save(city);
    }

    private ParkingLot createParkingLot(String name, City city) {
        Position position = new Position();
        position.setLongitude("4.895168");
        position.setLatitude("52.370216");

        ParkingLotDetails details = new ParkingLotDetails();
        details.setCapacity(100);
        details.setAvailableSpaces(20);

        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName(name);
        parkingLot.setCity(city);
        parkingLot.setPosition(position);
        parkingLot.setDetails(details);
        parkingLot.setCreatedAt(Instant.parse("2016-07-12T22:23:45Z"));
        parkingLot.setModifiedAt(Instant.parse("2021-05-09T13:45:12Z"));

        return repository.save(parkingLot);
    }
}