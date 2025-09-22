package com.db.operator.mapper;

import com.db.operator.dto.ContractDto;
import com.db.operator.model.Contract;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ContractMapper {
    @Mapping(source = "tariffPlan.id", target = "tariffPlanId")
    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "employee.id", target = "employeeId")
    ContractDto toDto(Contract e);

    @Mapping(target = "tariffPlan", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "employee", ignore = true)
    Contract toEntity(ContractDto d);
}
