package com.db.operator.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {
    private Long id;
    private String passportNumber;
    private String mobileNumber;
    private String status;
    private String fio;
}
