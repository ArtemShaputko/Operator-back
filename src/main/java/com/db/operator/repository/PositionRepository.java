package com.db.operator.repository;

import com.db.operator.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PositionRepository extends JpaRepository<Position, Long>, JpaSpecificationExecutor<Position> {}