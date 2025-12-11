package com.db.operator.controller;

import com.db.operator.dto.ContractDto;
import com.db.operator.service.ContractCrudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contracts")
@CrossOrigin
public class ContractController {
    private final ContractCrudService service;
    public ContractController(ContractCrudService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<List<ContractDto>> all(@RequestParam Map<String, String> filters) {
        return ResponseEntity.ok(service.findAll(filters));
    }
    @GetMapping("/{id}") public ResponseEntity<ContractDto> one(@PathVariable Long id) { return ResponseEntity.ok(service.findById(id)); }
    @PostMapping public ResponseEntity<ContractDto> create(@RequestBody ContractDto d) {
        ContractDto created = service.create(d);
        return ResponseEntity.created(URI.create("/api/contracts/" + created.getId())).body(created);
    }
    @PutMapping("/{id}") public ResponseEntity<ContractDto> update(@PathVariable Long id, @RequestBody ContractDto d) {
        return ResponseEntity.ok(service.update(id, d));
    }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

