package com.db.operator.controller;

import com.db.operator.dto.SupportTicketDto;
import com.db.operator.service.SupportTicketCrudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/support-tickets")
@CrossOrigin
public class SupportTicketController {
    private final SupportTicketCrudService service;

    public SupportTicketController(SupportTicketCrudService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<SupportTicketDto>> all(@RequestParam Map<String, String> filters) {
        return ResponseEntity.ok(service.findAll(filters));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupportTicketDto> one(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<SupportTicketDto> create(@RequestBody SupportTicketDto d) {
        SupportTicketDto created = service.create(d);
        return ResponseEntity
                .created(URI.create("/api/support-tickets/" + created.getId()))
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupportTicketDto> update(@PathVariable Long id, @RequestBody SupportTicketDto d) {
        return ResponseEntity.ok(service.update(id, d));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

