package com.db.operator.service;

import com.db.operator.dto.TariffPlanDto;
import com.db.operator.mapper.TariffPlanMapper;
import com.db.operator.model.TariffPlan;
import com.db.operator.repository.TariffPlanRepository;
import com.db.operator.specification.SpecificationBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TariffPlanCrudService {
    private final TariffPlanRepository repo;
    private final TariffPlanMapper mapper;

    public TariffPlanCrudService(TariffPlanRepository repo, TariffPlanMapper mapper) {
        this.repo = repo; this.mapper = mapper;
    }

    public List<TariffPlanDto> findAll(Map<String,String> filters) {
        SpecificationBuilder<TariffPlan> builder = new SpecificationBuilder<>();
        return repo.findAll(builder.build(filters)).stream().map(mapper::toDto).toList();
    }

    public TariffPlanDto findById(Long id) { return mapper.toDto(repo.findById(id).orElseThrow()); }

    public TariffPlanDto create(TariffPlanDto d) { return mapper.toDto(repo.save(mapper.toEntity(d))); }

    public TariffPlanDto update(Long id, TariffPlanDto d) {
        var e = mapper.toEntity(d); e.setId(id);
        return mapper.toDto(repo.save(e));
    }

    public void delete(Long id) { repo.deleteById(id); }
}

