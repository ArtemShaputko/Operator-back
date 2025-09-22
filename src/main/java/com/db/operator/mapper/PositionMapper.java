package com.db.operator.mapper;

import com.db.operator.dto.PositionDto;
import com.db.operator.model.Position;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PositionMapper {
    PositionDto toDto(Position e);
    @Mapping(target = "id", ignore = true)
    Position toEntity(PositionDto d);
}
