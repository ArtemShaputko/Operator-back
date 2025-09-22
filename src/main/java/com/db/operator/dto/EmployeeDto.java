package com.db.operator.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private Long id;
    private String passportNumber;
    private BigDecimal bonus;
    private LocalDate hired;
    private String fio;
    private Long positionId;
}
