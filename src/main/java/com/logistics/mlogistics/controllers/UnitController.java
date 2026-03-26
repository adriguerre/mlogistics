package com.logistics.mlogistics.controllers;

import com.logistics.mlogistics.domain.Unit;
import com.logistics.mlogistics.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/unit")
public class UnitController {

    private final UnitService unitService;

    @Autowired
    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Unit> list = unitService.getAll();
        if (list.isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No records found");
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        return unitService.getById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found"));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Unit unit) {
        return ResponseEntity.status(HttpStatus.CREATED).body(unitService.create(unit));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Unit unit) {
        return unitService.update(id, unit)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        unitService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
