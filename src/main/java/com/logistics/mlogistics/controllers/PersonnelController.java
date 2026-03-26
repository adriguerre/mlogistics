package com.logistics.mlogistics.controllers;

import com.logistics.mlogistics.domain.Personnel;
import com.logistics.mlogistics.service.PersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/personnel")
public class PersonnelController {

    private final PersonnelService personnelService;

    @Autowired
    public PersonnelController(PersonnelService personnelService) {
        this.personnelService = personnelService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Personnel> list = personnelService.getAll();
        if (list.isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No records found");
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        return personnelService.getById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found"));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Personnel personnel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personnelService.create(personnel));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Personnel personnel) {
        return personnelService.update(id, personnel)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        personnelService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
