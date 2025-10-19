package com.instant.fetcher.service;

import lombok.Value;
import lombok.experimental.Accessors;

import com.instant.fetcher.model.ParkingLot;
import com.instant.fetcher.model.ParkingLotsResponse;
import com.instant.fetcher.model.Position;
import com.instant.fetcher.properties.SourceConfiguration;
import com.instant.fetcher.util.ParkingLotsResponseClassResolver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DataFetchServiceTest {

    @Mock
    private ParkingLotsResponseClassResolver responseClassResolver;

    @Mock
    private RestService restService;

    @InjectMocks
    private DataFetchService dataFetchService;

    @Mock
    private SourceConfiguration sourceConfiguration;

    @BeforeEach
    void setup() {
        when(sourceConfiguration.getUrl()).thenReturn("http://api.instant.com/parkinglots");
        when(sourceConfiguration.getCity()).thenReturn("Paris");
        when(responseClassResolver.resolve("Paris")).thenReturn((Class) TestResponse.class);
    }

    @Test
    void shouldFetchData() {
        List<TestLot> received = List.of(
            new TestLot("centralPark", 100, 20, new TestPosition("lat-cp", "lon-cp")),
            new TestLot("manhattan", 100, 20, new TestPosition("lat-man", "lon-man")));

        when(restService.get("http://api.instant.com/parkinglots", TestResponse.class)).thenReturn(ResponseEntity.ok(new TestResponse(received, 2)));

        List<ParkingLot> lots = dataFetchService.fetchData(sourceConfiguration);

        assertEquals(received, lots);
    }

    @Test
    void shouldHandleNullResponse() {
        when(restService.get("http://api.instant.com/parkinglots", TestResponse.class)).thenReturn(null);

        assertTrue(dataFetchService.fetchData(sourceConfiguration).isEmpty());
    }

    @Test
    void shouldHandleEmptyResponse() {
        when(restService.get("http://api.instant.com/parkinglots", TestResponse.class)).thenReturn(ResponseEntity.ok(null));

        assertTrue(dataFetchService.fetchData(sourceConfiguration).isEmpty());
    }

    @Test
    void shouldHandleMissingLots() {
        when(restService.get("http://api.instant.com/parkinglots", TestResponse.class)).thenReturn(ResponseEntity.ok(new TestResponse(List.of(), 0)));

        assertTrue(dataFetchService.fetchData(sourceConfiguration).isEmpty());
    }

    @Value
    @Accessors(fluent = true)
    private static class TestResponse implements ParkingLotsResponse<TestLot> {
        List<TestLot> parkingLots;
        int total;
    }

    @Value
    @Accessors(fluent = true)
    private static class TestLot implements ParkingLot {
        String name;
        int capacity;
        int availableSpots;
        TestPosition position;
    }

    @Value
    @Accessors(fluent = true)
    private static class TestPosition implements Position {
        String latitude;
        String longitude;
    }

}