package com.db.operator.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupportTicketDto {
    private Long id;
    private Long clientId;
    private Long employeeId;
    private LocalDateTime timestamp;
    private String response;
}
