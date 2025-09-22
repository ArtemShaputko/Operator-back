package com.db.operator.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractDto {
    private Long id;
    private LocalDate concluded;
    private LocalDate expire;
    private BigDecimal prepayment;
    private String status;
    private Long tariffPlanId;
    private Long clientId;
    private Long employeeId;
}
