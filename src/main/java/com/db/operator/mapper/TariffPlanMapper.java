package com.db.operator.mapper;

import com.db.operator.dto.TariffPlanDto;
import com.db.operator.model.TariffPlan;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TariffPlanMapper {
    TariffPlanDto toDto(TariffPlan e);
    TariffPlan toEntity(TariffPlanDto d);
}
