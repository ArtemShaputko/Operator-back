package com.db.operator.model.id;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupportTicketId implements Serializable {
    private Long clientId;
    private Long employeeId;
}
