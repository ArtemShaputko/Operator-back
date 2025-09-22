package com.db.operator.mapper;

import com.db.operator.dto.ContractServiceDto;
import com.db.operator.model.ContractService;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ContractServiceMapper {
    @Mapping(source = "id.contractId", target = "contractId")
    @Mapping(source = "id.serviceId", target = "serviceId")
    ContractServiceDto toDto(ContractService e);

    @Mapping(target = "contract", ignore = true)
    @Mapping(target = "service", ignore = true)
    ContractService toEntity(ContractServiceDto d);
}
