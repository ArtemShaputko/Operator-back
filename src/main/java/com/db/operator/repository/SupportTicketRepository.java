package com.db.operator.repository;

import com.db.operator.model.SupportTicket;
import com.db.operator.model.id.SupportTicketId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SupportTicketRepository extends JpaRepository<SupportTicket, Long>, JpaSpecificationExecutor<SupportTicket> {
    List<SupportTicket> findByClientId(Long clientId);
    List<SupportTicket> findByEmployeeId(Long employeeId);
}