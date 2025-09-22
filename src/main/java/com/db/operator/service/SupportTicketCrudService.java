package com.db.operator.service;

import com.db.operator.dto.SupportTicketDto;
import com.db.operator.mapper.SupportTicketMapper;
import com.db.operator.model.*;
import com.db.operator.model.id.SupportTicketId;
import com.db.operator.repository.*;
import com.db.operator.specification.SpecificationBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class SupportTicketCrudService {
    private final SupportTicketRepository repo;
    private final ClientRepository clientRepo;
    private final EmployeeRepository employeeRepo;
    private final SupportTicketMapper mapper;

    public SupportTicketCrudService(SupportTicketRepository repo,
                                    ClientRepository clientRepo,
                                    EmployeeRepository employeeRepo,
                                    SupportTicketMapper mapper) {
        this.repo = repo;
        this.clientRepo = clientRepo;
        this.employeeRepo = employeeRepo;
        this.mapper = mapper;
    }

    public List<SupportTicketDto> findAll(Map<String, String> filters) {
        SpecificationBuilder<SupportTicket> builder = new SpecificationBuilder<>();
        return repo.findAll(builder.build(filters)).stream().map(mapper::toDto).toList();
    }

    public SupportTicketDto findById(Long id) {
        return mapper.toDto(repo.findById(id).orElseThrow());
    }

    public SupportTicketDto create(SupportTicketDto d) {
        SupportTicket e = mapper.toEntity(d);
        e.setClient(clientRepo.findById(d.getClientId()).orElseThrow());
        e.setEmployee(employeeRepo.findById(d.getEmployeeId()).orElseThrow());
        e.setTimestamp(d.getTimestamp() != null ? d.getTimestamp() : LocalDateTime.now());
        return mapper.toDto(repo.save(e));
    }

    public SupportTicketDto update(Long id, SupportTicketDto d) {
        SupportTicket existing = repo.findById(id).orElseThrow();
        existing.setResponse(d.getResponse());
        existing.setTimestamp(d.getTimestamp() != null ? d.getTimestamp() : existing.getTimestamp());
        existing.setClient(clientRepo.findById(d.getClientId()).orElseThrow());
        existing.setEmployee(employeeRepo.findById(d.getEmployeeId()).orElseThrow());
        return mapper.toDto(repo.save(existing));
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
