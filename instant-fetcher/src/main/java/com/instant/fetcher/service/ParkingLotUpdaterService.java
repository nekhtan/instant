package com.instant.fetcher.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.instant.data.entity.City;
import com.instant.data.entity.ParkingLot;
import com.instant.data.entity.ParkingLotDetails;
import com.instant.data.entity.Position;
import com.instant.fetcher.mapper.ParkingLotDetailsMapper;
import com.instant.fetcher.mapper.PositionMapper;

import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Updater to update existing {@link ParkingLot} entities with new data.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ParkingLotUpdaterService {

    private final PositionMapper positionMapper;
    private final ParkingLotDetailsMapper detailsMapper;

    /**
     * Updates the stored parking lot with the up-to-date data.
     *
     * @param storedLot the currently stored parking lot
     * @param upToDateLot the newly received (up-to-date) parking lot data
     * @param upToDateCity the newly received (up-to-date) city
     * @return {@code true} if any field was updated, {@code false} otherwise
     */
    public boolean update(ParkingLot storedLot, com.instant.fetcher.model.ParkingLot upToDateLot, City upToDateCity) {
        boolean updated = false;

        if (!upToDateLot.name().equalsIgnoreCase(storedLot.getName())) {
            log.info("{}/{}: name changed from {} to {}", upToDateCity.getName(), storedLot.getName(), storedLot.getName(), upToDateLot.name());
            storedLot.setName(upToDateLot.name());
            updated = true;
        }

        Position position = positionMapper.mapPosition(upToDateLot.position());
        if (!Objects.equals(position, storedLot.getPosition())) {
            log.info("{}/{}: position changed from {} to {}", upToDateCity.getName(), storedLot.getName(), storedLot.getPosition(), position);
            storedLot.setPosition(position);
            updated = true;
        }

        ParkingLotDetails parkingLotDetails = detailsMapper.map(upToDateLot);
        if (!parkingLotDetails.equals(storedLot.getDetails())) {
            log.info("{}/{}: details changed from {} to {}", upToDateCity.getName(), storedLot.getName(), storedLot.getDetails(), parkingLotDetails);
            storedLot.setDetails(parkingLotDetails);
            updated = true;
        }

        if (!upToDateCity.equals(storedLot.getCity())) {
            log.info("{}/{}: city changed from {} to {}", upToDateCity.getName(), storedLot.getName(), storedLot.getCity(), upToDateCity.getName());
            storedLot.setCity(upToDateCity);
            updated = true;
        }

        return updated;
    }
}
