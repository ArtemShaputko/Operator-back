package com.db.operator.service;

import com.db.operator.dto.EmployeeDto;
import com.db.operator.mapper.EmployeeMapper;
import com.db.operator.model.Employee;
import com.db.operator.model.Position;
import com.db.operator.repository.EmployeeRepository;
import com.db.operator.repository.PositionRepository;
import com.db.operator.specification.SpecificationBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EmployeeCrudService {
    private final EmployeeRepository repo;
    private final PositionRepository positionRepo;
    private final EmployeeMapper mapper;

    public EmployeeCrudService(EmployeeRepository repo, PositionRepository positionRepo, EmployeeMapper mapper) {
        this.repo = repo;
        this.positionRepo = positionRepo;
        this.mapper = mapper;
    }

    public List<EmployeeDto> findAll(Map<String, String> filters) {
        SpecificationBuilder<Employee> builder = new SpecificationBuilder<>();
        return repo.findAll(builder.build(filters)).stream().map(mapper::toDto).toList();
    }

    public EmployeeDto findById(Long id) {
        return mapper.toDto(repo.findById(id).orElseThrow());
    }

    public EmployeeDto create(EmployeeDto d) {
        Employee e = mapper.toEntity(d);
        if (d.getPositionId() != null) {
            Position p = positionRepo.findById(d.getPositionId()).orElseThrow();
            e.setPosition(p);
        }
        return mapper.toDto(repo.save(e));
    }

    public EmployeeDto update(Long id, EmployeeDto d) {
        Employee e = mapper.toEntity(d);
        e.setId(id);
        if (d.getPositionId() != null) {
            Position p = positionRepo.findById(d.getPositionId()).orElseThrow();
            e.setPosition(p);
        }
        return mapper.toDto(repo.save(e));
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
