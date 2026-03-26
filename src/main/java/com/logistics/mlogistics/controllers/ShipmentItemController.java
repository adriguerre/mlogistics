package com.logistics.mlogistics.controllers;

import com.logistics.mlogistics.domain.ShipmentItem;
import com.logistics.mlogistics.service.ShipmentItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/shipment-item")
public class ShipmentItemController {

    private final ShipmentItemService service;

    @Autowired
    public ShipmentItemController(ShipmentItemService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<ShipmentItem> list = service.getAll();
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
    public ResponseEntity<?> create(@RequestBody ShipmentItem entity) {
        ShipmentItem created = service.create(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody ShipmentItem entity) {
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
