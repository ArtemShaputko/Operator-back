package com.db.operator.repository;

import com.db.operator.model.TariffPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TariffPlanRepository extends JpaRepository<TariffPlan, Long>, JpaSpecificationExecutor<TariffPlan> {}