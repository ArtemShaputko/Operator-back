package com.db.operator.mapper;

import com.db.operator.dto.BillDto;
import com.db.operator.model.Bill;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BillMapper {
    @Mapping(source = "client.id", target = "clientId")
    BillDto toDto(Bill e);

    @Mapping(target = "client", ignore = true)
    Bill toEntity(BillDto d);
}
