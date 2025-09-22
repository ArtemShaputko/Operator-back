package com.db.operator.service;

import com.db.operator.dto.ContractDto;
import com.db.operator.mapper.ContractMapper;
import com.db.operator.model.*;
import com.db.operator.repository.*;
import com.db.operator.specification.SpecificationBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ContractCrudService {
    private final ContractRepository repo;
    private final ClientRepository clientRepo;
    private final TariffPlanRepository tariffRepo;
    private final ContractMapper mapper;

    public ContractCrudService(ContractRepository repo, ClientRepository clientRepo,
                               TariffPlanRepository tariffRepo, ContractMapper mapper) {
        this.repo = repo; this.clientRepo = clientRepo; this.tariffRepo = tariffRepo; this.mapper = mapper;
    }

    public List<ContractDto> findAll(Map<String,String> filters) {
        SpecificationBuilder<Contract> builder = new SpecificationBuilder<>();
        return repo.findAll(builder.build(filters)).stream().map(mapper::toDto).toList();
    }

    public ContractDto findById(Long id) { return mapper.toDto(repo.findById(id).orElseThrow()); }

    public ContractDto create(ContractDto d) {
        Contract e = mapper.toEntity(d);
        if (d.getClientId()!=null) e.setClient(clientRepo.findById(d.getClientId()).orElseThrow());
        if (d.getTariffPlanId()!=null) e.setTariffPlan(tariffRepo.findById(d.getTariffPlanId()).orElseThrow());
        return mapper.toDto(repo.save(e));
    }

    public ContractDto update(Long id, ContractDto d) {
        Contract e = mapper.toEntity(d); e.setId(id);
        if (d.getClientId()!=null) e.setClient(clientRepo.findById(d.getClientId()).orElseThrow());
        if (d.getTariffPlanId()!=null) e.setTariffPlan(tariffRepo.findById(d.getTariffPlanId()).orElseThrow());
        return mapper.toDto(repo.save(e));
    }

    public void delete(Long id) { repo.deleteById(id); }
}
