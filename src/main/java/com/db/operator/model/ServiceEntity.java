package com.db.operator.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "service")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="type")
    private String type;

    @Column(name="description")
    private String description;

    @Column(name="status")
    private String status;
}
