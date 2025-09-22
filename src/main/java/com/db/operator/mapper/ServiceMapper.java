package com.db.operator.mapper;

import com.db.operator.dto.ServiceDto;
import com.db.operator.model.ServiceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceMapper {
    ServiceDto toDto(ServiceEntity e);
    ServiceEntity toEntity(ServiceDto d);
}
