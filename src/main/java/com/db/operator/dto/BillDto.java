package com.db.operator.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillDto {
    private Long id;
    private LocalDate issued;
    private Boolean paid;
    private BigDecimal size;
    private String status;
    private Long clientId;
}
