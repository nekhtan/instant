package com.instant.api.mapper;

import com.instant.api.model.DistanceDto;
import com.instant.api.model.ParkingLotDto;
import com.instant.api.util.DoubleHelper;
import com.instant.data.entity.City;
import com.instant.data.entity.ParkingLot;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for {@link ParkingLot} and {@link ParkingLotDto}.
 *
 * @apiNote may deserve to be split in multiple sub-mappers in the future
 */
@Mapper(componentModel = "spring")
public interface ParkingLotMapper {

    /**
     * Maps a {@link ParkingLot} to a {@link ParkingLotDto}.
     *
     * @param parkingLot the parking lot entity
     * @return the parking lot dto
     */
    @Mapping(target = "lastUpdate", source = "modifiedAt")
    ParkingLotDto map(ParkingLot parkingLot);

    /**
     * Maps a {@link ParkingLot} to a {@link ParkingLotDto} including distance information.
     *
     * @param parkingLot the parking lot entity
     * @param meters the distance in meters to the source
     * @return the parking lot dto with distance
     */
    default ParkingLotDto map(ParkingLot parkingLot, Double meters) {
        ParkingLotDto dto = map(parkingLot);

        return dto == null
            ? null
            : map(parkingLot).toBuilder()
                .distance(map(meters))
                .build();
    }

    /**
     * Maps a distance in meters to a {@link DistanceDto}.
     *
     * @param meters the distance in meters
     * @return the distance dto
     */
    default DistanceDto map(Double meters) {
        if (meters == null) {
            return null;
        }

        int m = (int) Math.round(meters);
        Double km = null;

        if (m >= 1000) {
            km = DoubleHelper.twoDigits(meters / 1000.0);
        }

        return new DistanceDto(m, km);
    }

    /**
     * Maps a {@link City} to its name.
     *
     * @param city the city entity
     * @return the city name
     */
    default String map(City city) {
        return city == null ? null : city.getName();
    }

}
