package com.instant.api.mapper;

import com.instant.api.model.PositionDto;
import com.instant.data.entity.Position;

import org.mapstruct.Mapper;

/**
 * Mapper for {@link Position} and {@link PositionDto}.
 */
@Mapper(componentModel = "spring")
public interface PositionMapper {

    /**
     * Maps a {@link PositionDto} to a {@link Position}.
     *
     * @param positionDto the position DTO
     * @return the mapped position entity
     */
    Position map(PositionDto positionDto);

    /**
     * Maps a {@link Position} to a {@link PositionDto}.
     *
     * @param position the position entity
     * @return the mapped position DTO
     */
    PositionDto map(Position position);

}
