package com.instant.fetcher.mapper;

import lombok.NonNull;

import com.instant.data.entity.ParkingLotDetails;
import com.instant.fetcher.model.ParkingLot;

import org.springframework.stereotype.Component;

/**
 * Mapper for {@link ParkingLotDetails}.
 */
@Component
public class ParkingLotDetailsMapper {

    /**
     * Maps the details from the {@link ParkingLot} to a {@link ParkingLotDetails} entity.
     *
     * @param parkingLotDto the parking lot
     * @return the mapped parking lot details
     */
    public ParkingLotDetails map(@NonNull ParkingLot parkingLotDto) {
        ParkingLotDetails parkingLotDetails = new ParkingLotDetails();
        parkingLotDetails.setCapacity(parkingLotDto.capacity());
        parkingLotDetails.setAvailableSpaces(parkingLotDto.availableSpots());
        return parkingLotDetails;
    }
}
