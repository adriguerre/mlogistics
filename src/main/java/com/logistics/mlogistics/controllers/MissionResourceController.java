package com.logistics.mlogistics.controllers;

import com.logistics.mlogistics.domain.MissionResource;
import com.logistics.mlogistics.service.MissionResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/mission-resource")
public class MissionResourceController {

    private final MissionResourceService service;

    @Autowired
    public MissionResourceController(MissionResourceService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<MissionResource> list = service.getAll();
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
    public ResponseEntity<?> create(@RequestBody MissionResource entity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(entity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody MissionResource entity) {
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
