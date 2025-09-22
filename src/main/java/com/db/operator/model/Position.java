package com.db.operator.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "position")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", unique = true, nullable = false)
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="salary", columnDefinition = "money")
    private BigDecimal salary;

    @Column(name="category")
    private String category;
}
