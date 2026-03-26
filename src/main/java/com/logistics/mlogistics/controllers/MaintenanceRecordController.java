package com.logistics.mlogistics.controllers;

import com.logistics.mlogistics.domain.MaintenanceRecord;
import com.logistics.mlogistics.service.MaintenanceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/maintenance-record")
public class MaintenanceRecordController {

    private final MaintenanceRecordService service;

    @Autowired
    public MaintenanceRecordController(MaintenanceRecordService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<MaintenanceRecord> list = service.getAll();
        if (list.isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No records found");
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        return service.getById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found"));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody MaintenanceRecord entity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(entity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody MaintenanceRecord entity) {
        return service.update(id, entity)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
