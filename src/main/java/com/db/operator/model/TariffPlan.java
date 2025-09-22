package com.db.operator.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tariff_plan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TariffPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="price", columnDefinition = "money")
    private BigDecimal price;

    @Column(name="internet")
    private Integer internet;

    @Column(name="status")
    private String status;

    @Column(name="minutes_in")
    private Integer minutesIn;

    @Column(name="minutes_out")
    private Integer minutesOut;
}
