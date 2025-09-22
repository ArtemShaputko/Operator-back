package com.db.operator.mapper;

import com.db.operator.dto.ClientDto;
import com.db.operator.model.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientDto toDto(Client entity);
    Client toEntity(ClientDto dto);
}
