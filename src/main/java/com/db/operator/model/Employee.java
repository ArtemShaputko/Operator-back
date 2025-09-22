package com.db.operator.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="passport_number", unique = true, nullable = false)
    private String passportNumber;

    @Column(name="bonus", columnDefinition = "money")
    private BigDecimal bonus;

    @Column(name="hired")
    private LocalDate hired;

    @Column(name="fio", nullable = false)
    private String fio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private Position position;
}
