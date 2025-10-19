package com.instant.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

import com.instant.api.model.ParkingLotDto;
import com.instant.api.model.PositionDto;
import com.instant.api.service.ParkingLotDtoService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.SequencedCollection;

/**
 * Controller that manages parking lots.
 */
@RestController
@RequestMapping("/parkingLots")
@RequiredArgsConstructor
public class ParkingLotController {

    private final ParkingLotDtoService parkingLotDtoService;

    /**
     * Get all parking lots in a given city.
     *
     * @param city the city name
     * @param pageable the pagination information
     * @return the parking lots page
     */
    @Operation(
        summary = "Get all parking lots in a given city",
        description = "Returns a paginated list of parking lots for the specified city.")
    @GetMapping("/{city}/all")
    public ResponseEntity<Page<ParkingLotDto>> getAllIn(@PathVariable String city, @PageableDefault(size = 15) Pageable pageable) {
        return wrap(parkingLotDtoService.getAllFrom(city, pageable));
    }

    /**
     * Get all parking lots.
     *
     * @param pageable the pagination information
     * @return the parking lots page
     */
    @Operation(
        summary = "Get all parking lots",
        description = "Returns a paginated list of all the parking lots.")
    @GetMapping("/all")
    public ResponseEntity<Page<ParkingLotDto>> getAll(@PageableDefault(size = 15) Pageable pageable) {
        return wrap(parkingLotDtoService.getAll(pageable));
    }

    /**
     * Get parking lots near a given position.
     *
     * @param latitude the latitude
     * @param longitude the longitude
     * @param radiusKm the radius in kilometers
     * @return the parking lots near the given position, ordered from nearest to farthest
     */
    @Operation(
        summary = "Get parking lots near a position",
        description = "Returns a sequenced collection of parking lots near the given latitude and longitude, ordered from nearest to farthest.")
    @GetMapping("/near")
    public ResponseEntity<SequencedCollection<ParkingLotDto>> getNear(
            @Parameter(description = "Latitude", example = "48.5839200") @RequestParam String latitude,
            @Parameter(description = "Longitude", example = "7.7455300") @RequestParam String longitude,
            @Parameter(description = "Search radius (in km)", example = "5") @RequestParam(value = "radius", required = false, defaultValue = "10") int radiusKm) {
        return wrap(parkingLotDtoService.getLotsAround(new PositionDto(latitude, longitude), radiusKm));
    }

    private static ResponseEntity<Page<ParkingLotDto>> wrap(Page<ParkingLotDto> page) {
        return page.hasContent()
            ? ResponseEntity.ok(page)
            : ResponseEntity.noContent().build();
    }

    private static <T extends Collection<ParkingLotDto>> ResponseEntity<T> wrap(T page) {
        return page.isEmpty()
            ? ResponseEntity.noContent().build()
            : ResponseEntity.ok(page);
    }
}
