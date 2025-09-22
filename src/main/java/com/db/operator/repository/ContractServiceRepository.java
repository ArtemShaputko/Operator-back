package com.db.operator.repository;

import com.db.operator.model.ContractService;
import com.db.operator.model.id.ContractServiceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContractServiceRepository extends JpaRepository<ContractService, ContractServiceId>, JpaSpecificationExecutor<ContractService> {}