package com.db.operator.service;

import com.db.operator.dto.ClientDto;
import com.db.operator.mapper.ClientMapper;
import com.db.operator.model.Client;
import com.db.operator.repository.ClientRepository;
import com.db.operator.specification.SpecificationBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ClientCrudService {
    private final ClientRepository repo;
    private final ClientMapper mapper;

    public ClientCrudService(ClientRepository repo, ClientMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public List<ClientDto> findAll(Map<String, String> filters) {
        SpecificationBuilder<Client> builder = new SpecificationBuilder<>();
        return repo.findAll(builder.build(filters)).stream().map(mapper::toDto).toList();
    }

    public ClientDto findById(Long id) {
        return mapper.toDto(repo.findById(id).orElseThrow());
    }

    public ClientDto create(ClientDto dto) {
        return mapper.toDto(repo.save(mapper.toEntity(dto)));
    }

    public ClientDto update(Long id, ClientDto dto) {
        var entity = mapper.toEntity(dto);
        entity.setId(id);
        return mapper.toDto(repo.save(entity));
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
