package com.db.operator.service;

import com.db.operator.dto.PositionDto;
import com.db.operator.mapper.PositionMapper;
import com.db.operator.model.Position;
import com.db.operator.repository.PositionRepository;
import com.db.operator.specification.SpecificationBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PositionCrudService {
    private final PositionRepository repo;
    private final PositionMapper mapper;

    public PositionCrudService(PositionRepository repo, PositionMapper mapper) {
        this.repo = repo; this.mapper = mapper;
    }

    public List<PositionDto> findAll(Map<String,String> filters) {
        SpecificationBuilder<Position> builder = new SpecificationBuilder<>();
        return repo.findAll(builder.build(filters)).stream().map(mapper::toDto).toList();
    }

    public PositionDto findById(Long id) { return mapper.toDto(repo.findById(id).orElseThrow()); }

    public PositionDto create(PositionDto d) { return mapper.toDto(repo.save(mapper.toEntity(d))); }

    public PositionDto update(Long id, PositionDto d) {
        var e = mapper.toEntity(d); e.setId(id);
        return mapper.toDto(repo.save(e));
    }

    public void delete(Long id) { repo.deleteById(id); }
}

