package com.db.operator.controller;

import com.db.operator.dto.ContractServiceDto;
import com.db.operator.service.ContractServiceCrudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contract-services")
@CrossOrigin
public class ContractServiceController {
    private final ContractServiceCrudService service;

    public ContractServiceController(ContractServiceCrudService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<List<ContractServiceDto>> all(@RequestParam Map<String, String> filters) {
        return ResponseEntity.ok(service.findAll(filters));
    }

    @GetMapping("/{contractId}/{serviceId}")
    public ResponseEntity<ContractServiceDto> one(@PathVariable Long contractId,
                                                  @PathVariable Long serviceId) {
        return ResponseEntity.ok(service.findById(contractId, serviceId));
    }

    @PostMapping
    public ResponseEntity<ContractServiceDto> create(@RequestBody ContractServiceDto d) {
        ContractServiceDto created = service.create(d);
        return ResponseEntity.created(URI.create("/api/contract-services/" +
                created.getContractId() + "/" + created.getServiceId())).body(created);
    }

    @PutMapping("/{contractId}/{serviceId}")
    public ResponseEntity<ContractServiceDto> update(@PathVariable Long contractId,
                                                     @PathVariable Long serviceId,
                                                     @RequestBody ContractServiceDto d) {
        return ResponseEntity.ok(service.update(contractId, serviceId, d));
    }

    @DeleteMapping("/{contractId}/{serviceId}")
    public ResponseEntity<Void> delete(@PathVariable Long contractId,
                                       @PathVariable Long serviceId) {
        service.delete(contractId, serviceId);
        return ResponseEntity.noContent().build();
    }
}
