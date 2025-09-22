package com.db.operator.service;

import com.db.operator.dto.ServiceDto;
import com.db.operator.mapper.ServiceMapper;
import com.db.operator.model.ServiceEntity;
import com.db.operator.repository.ServiceRepository;
import com.db.operator.specification.SpecificationBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ServiceCrudService {
    private final ServiceRepository repo;
    private final ServiceMapper mapper;

    public ServiceCrudService(ServiceRepository repo, ServiceMapper mapper) {
        this.repo = repo; this.mapper = mapper;
    }

    public List<ServiceDto> findAll(Map<String,String> filters) {
        SpecificationBuilder<ServiceEntity> builder = new SpecificationBuilder<>();
        return repo.findAll(builder.build(filters)).stream().map(mapper::toDto).toList();
    }

    public ServiceDto findById(Long id) { return mapper.toDto(repo.findById(id).orElseThrow()); }

    public ServiceDto create(ServiceDto d) { return mapper.toDto(repo.save(mapper.toEntity(d))); }

    public ServiceDto update(Long id, ServiceDto d) {
        var e = mapper.toEntity(d); e.setId(id);
        return mapper.toDto(repo.save(e));
    }

    public void delete(Long id) { repo.deleteById(id); }
}

