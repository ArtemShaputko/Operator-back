package com.db.operator.service;

import com.db.operator.dto.BillDto;
import com.db.operator.mapper.BillMapper;
import com.db.operator.model.Bill;
import com.db.operator.model.Client;
import com.db.operator.repository.BillRepository;
import com.db.operator.repository.ClientRepository;
import com.db.operator.specification.SpecificationBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BillCrudService {
    private final BillRepository repo;
    private final ClientRepository clientRepo;
    private final BillMapper mapper;

    public BillCrudService(BillRepository repo, ClientRepository clientRepo, BillMapper mapper) {
        this.repo = repo;
        this.clientRepo = clientRepo;
        this.mapper = mapper;
    }

    public List<BillDto> findAll(Map<String, String> filters) {
        SpecificationBuilder<Bill> builder = new SpecificationBuilder<>();
        return repo.findAll(builder.build(filters)).stream().map(mapper::toDto).toList();
    }

    public BillDto findById(Long id) {
        return mapper.toDto(repo.findById(id).orElseThrow());
    }

    public BillDto create(BillDto d) {
        Bill e = mapper.toEntity(d);
        if (d.getClientId() != null) {
            Client c = clientRepo.findById(d.getClientId()).orElseThrow();
            e.setClient(c);
        }
        return mapper.toDto(repo.save(e));
    }

    public BillDto update(Long id, BillDto d) {
        Bill e = mapper.toEntity(d);
        e.setId(id);
        if (d.getClientId() != null) {
            Client c = clientRepo.findById(d.getClientId()).orElseThrow();
            e.setClient(c);
        }
        return mapper.toDto(repo.save(e));
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
