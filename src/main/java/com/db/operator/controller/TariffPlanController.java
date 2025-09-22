package com.db.operator.controller;

import com.db.operator.dto.TariffPlanDto;
import com.db.operator.service.TariffPlanCrudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tariff-plans")
public class TariffPlanController {
    private final TariffPlanCrudService service;
    public TariffPlanController(TariffPlanCrudService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<List<TariffPlanDto>> all(@RequestParam Map<String, String> filters) {
        return ResponseEntity.ok(service.findAll(filters));
    }
    @GetMapping("/{id}") public ResponseEntity<TariffPlanDto> one(@PathVariable Long id) { return ResponseEntity.ok(service.findById(id)); }
    @PostMapping public ResponseEntity<TariffPlanDto> create(@RequestBody TariffPlanDto d) {
        TariffPlanDto created = service.create(d);
        return ResponseEntity.created(URI.create("/api/tariff-plans/" + created.getId())).body(created);
    }
    @PutMapping("/{id}") public ResponseEntity<TariffPlanDto> update(@PathVariable Long id, @RequestBody TariffPlanDto d) {
        return ResponseEntity.ok(service.update(id, d));
    }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
