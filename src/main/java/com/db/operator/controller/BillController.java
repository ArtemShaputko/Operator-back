package com.db.operator.controller;

import com.db.operator.dto.BillDto;
import com.db.operator.service.BillCrudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bills")
@CrossOrigin
public class BillController {
    private final BillCrudService service;

    public BillController(BillCrudService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<BillDto>> all(@RequestParam Map<String, String> filters) {
        return ResponseEntity.ok(service.findAll(filters));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillDto> one(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<BillDto> create(@RequestBody BillDto d) {
        BillDto created = service.create(d);
        return ResponseEntity.created(URI.create("/api/bills/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BillDto> update(@PathVariable Long id, @RequestBody BillDto d) {
        return ResponseEntity.ok(service.update(id, d));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
