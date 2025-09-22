package com.db.operator.controller;

import com.db.operator.dto.ServiceDto;
import com.db.operator.service.ServiceCrudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/services")
public class ServiceController {
    private final ServiceCrudService service;
    public ServiceController(ServiceCrudService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<List<ServiceDto>> all(@RequestParam Map<String, String> filters) {
        return ResponseEntity.ok(service.findAll(filters));
    }
    @GetMapping("/{id}") public ResponseEntity<ServiceDto> one(@PathVariable Long id) { return ResponseEntity.ok(service.findById(id)); }
    @PostMapping public ResponseEntity<ServiceDto> create(@RequestBody ServiceDto d) {
        ServiceDto created = service.create(d);
        return ResponseEntity.created(URI.create("/api/services/" + created.getId())).body(created);
    }
    @PutMapping("/{id}") public ResponseEntity<ServiceDto> update(@PathVariable Long id, @RequestBody ServiceDto d) {
        return ResponseEntity.ok(service.update(id, d));
    }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

