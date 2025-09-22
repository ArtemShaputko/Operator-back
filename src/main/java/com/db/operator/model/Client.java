package com.db.operator.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "client")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="passport_number", unique = true, nullable = false)
    private String passportNumber;

    @Column(name="mobile_number", unique = true, nullable = false)
    private String mobileNumber;

    @Column(name="status", nullable = false)
    private String status;

    @Column(name="fio", nullable = false)
    private String fio;
}
