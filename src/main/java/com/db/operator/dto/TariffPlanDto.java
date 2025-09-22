package com.db.operator.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TariffPlanDto {
    private Long id;
    private BigDecimal price;
    private Integer internet;
    private String status;
    private Integer minutesIn;
    private Integer minutesOut;
}
