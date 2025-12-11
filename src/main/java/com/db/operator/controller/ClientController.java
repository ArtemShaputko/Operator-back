package com.db.operator.controller;

import com.db.operator.dto.ClientDto;
import com.db.operator.service.ClientCrudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin
public class ClientController {
    private final ClientCrudService service;

    public ClientController(ClientCrudService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ClientDto>> all(@RequestParam Map<String, String> filters) {
        return ResponseEntity.ok(service.findAll(filters));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> one(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ClientDto> create(@RequestBody ClientDto d) {
        ClientDto created = service.create(d);
        return ResponseEntity.created(URI.create("/api/clients/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> update(@PathVariable Long id, @RequestBody ClientDto d) {
        return ResponseEntity.ok(service.update(id, d));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
