package com.instant.fetcher.mapper;

import com.instant.data.entity.Position;

import org.springframework.stereotype.Component;

/**
 * Mapper for {@link Position}.
 */
@Component
public class PositionMapper {

    /**
     * Maps the given position from the service response to an entity position.
     *
     * @param position the position from the service response
     * @return the position as an entity
     *
     */
    public Position mapPosition(com.instant.fetcher.model.Position position) {
        if (position == null) {
            return null;
        }
        Position mapped = new Position();
        mapped.setLatitude(position.latitude());
        mapped.setLongitude(position.longitude());
        return mapped;
    }

}
