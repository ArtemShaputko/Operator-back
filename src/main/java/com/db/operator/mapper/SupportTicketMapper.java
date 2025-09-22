package com.db.operator.mapper;

import com.db.operator.dto.SupportTicketDto;
import com.db.operator.model.SupportTicket;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface SupportTicketMapper {
    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "employee.id", target = "employeeId")
    SupportTicketDto toDto(SupportTicket entity);

    @Mapping(source = "clientId", target = "client.id")
    @Mapping(source = "employeeId", target = "employee.id")
    SupportTicket toEntity(SupportTicketDto dto);
}

