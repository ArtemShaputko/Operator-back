package com.db.operator.mapper;

import com.db.operator.dto.EmployeeDto;
import com.db.operator.model.Employee;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    @Mapping(source = "position.id", target = "positionId")
    EmployeeDto toDto(Employee e);

    // Only scalar fields; relation set in service
    @Mapping(target = "position", ignore = true)
    Employee toEntity(EmployeeDto d);
}
