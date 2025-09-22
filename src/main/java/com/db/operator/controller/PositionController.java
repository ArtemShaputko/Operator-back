package com.db.operator.controller;

import com.db.operator.dto.PositionDto;
import com.db.operator.service.PositionCrudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/positions")
public class PositionController {
    private final PositionCrudService service;
    public PositionController(PositionCrudService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<List<PositionDto>> all(@RequestParam Map<String, String> filters) {
        return ResponseEntity.ok(service.findAll(filters));
    }

    @GetMapping("/{id}") public ResponseEntity<PositionDto> one(@PathVariable Long id) { return ResponseEntity.ok(service.findById(id)); }
    @PostMapping public ResponseEntity<PositionDto> create(@RequestBody PositionDto d) {
        PositionDto created = service.create(d);
        return ResponseEntity.created(URI.create("/api/positions/" + created.getId())).body(created);
    }
    @PutMapping("/{id}") public ResponseEntity<PositionDto> update(@PathVariable Long id, @RequestBody PositionDto d) {
        return ResponseEntity.ok(service.update(id, d));
    }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

