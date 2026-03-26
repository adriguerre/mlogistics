package com.logistics.mlogistics.controllers;

import com.logistics.mlogistics.domain.SupplyOrder;
import com.logistics.mlogistics.service.SupplyOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/supply-order")
public class SupplyOrderController {

    private final SupplyOrderService service;

    @Autowired
    public SupplyOrderController(SupplyOrderService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<SupplyOrder> list = service.getAll();
        if (list.isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        return service.getById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody SupplyOrder entity) {
        SupplyOrder created = service.create(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody SupplyOrder entity) {
        return service.update(id, entity)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
