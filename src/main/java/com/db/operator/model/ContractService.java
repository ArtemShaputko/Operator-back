package com.db.operator.model;

import com.db.operator.model.id.ContractServiceId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "contract_service")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractService {
    @EmbeddedId
    private ContractServiceId id;

    @MapsId("contractId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @MapsId("serviceId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private ServiceEntity service;
}
