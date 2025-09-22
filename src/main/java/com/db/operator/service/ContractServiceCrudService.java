package com.db.operator.service;

import com.db.operator.dto.ContractServiceDto;
import com.db.operator.mapper.ContractServiceMapper;
import com.db.operator.model.Contract;
import com.db.operator.model.ContractService;
import com.db.operator.model.ServiceEntity;
import com.db.operator.model.id.ContractServiceId;
import com.db.operator.repository.ContractRepository;
import com.db.operator.repository.ContractServiceRepository;
import com.db.operator.repository.ServiceRepository;
import com.db.operator.specification.SpecificationBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ContractServiceCrudService {
    private final ContractServiceRepository repo;
    private final ContractRepository contractRepo;
    private final ServiceRepository serviceRepo;
    private final ContractServiceMapper mapper;

    public ContractServiceCrudService(ContractServiceRepository repo,
                                      ContractRepository contractRepo,
                                      ServiceRepository serviceRepo,
                                      ContractServiceMapper mapper) {
        this.repo = repo;
        this.contractRepo = contractRepo;
        this.serviceRepo = serviceRepo;
        this.mapper = mapper;
    }

    public List<ContractServiceDto> findAll(Map<String, String> filters) {
        SpecificationBuilder<ContractService> builder = new SpecificationBuilder<>();
        return repo.findAll(builder.build(filters)).stream().map(mapper::toDto).toList();
    }

    public ContractServiceDto findById(Long contractId, Long serviceId) {
        return mapper.toDto(repo.findById(new ContractServiceId(contractId, serviceId)).orElseThrow());
    }

    public ContractServiceDto create(ContractServiceDto d) {
        ContractService e = mapper.toEntity(d);
        if (d.getContractId() != null) {
            Contract c = contractRepo.findById(d.getContractId()).orElseThrow();
            e.setContract(c);
        }
        if (d.getServiceId() != null) {
            ServiceEntity s = serviceRepo.findById(d.getServiceId()).orElseThrow();
            e.setService(s);
        }
        e.setId(new ContractServiceId(d.getContractId(), d.getServiceId()));
        return mapper.toDto(repo.save(e));
    }

    public ContractServiceDto update(Long contractId, Long serviceId, ContractServiceDto d) {
        ContractService e = mapper.toEntity(d);
        e.setId(new ContractServiceId(contractId, serviceId));
        if (d.getContractId() != null) {
            Contract c = contractRepo.findById(d.getContractId()).orElseThrow();
            e.setContract(c);
        }
        if (d.getServiceId() != null) {
            ServiceEntity s = serviceRepo.findById(d.getServiceId()).orElseThrow();
            e.setService(s);
        }
        return mapper.toDto(repo.save(e));
    }

    public void delete(Long contractId, Long serviceId) {
        repo.deleteById(new ContractServiceId(contractId, serviceId));
    }
}
