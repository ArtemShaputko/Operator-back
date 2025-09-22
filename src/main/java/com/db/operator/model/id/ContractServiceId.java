package com.db.operator.model.id;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractServiceId implements Serializable {
    private Long contractId;
    private Long serviceId;
}
