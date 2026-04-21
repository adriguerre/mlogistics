package com.logistics.mlogistics.controllers;

import com.logistics.mlogistics.dto.personnel.PersonnelRequest;
import com.logistics.mlogistics.dto.personnel.PersonnelResponse;
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
    public ResponseEntity<List<PersonnelResponse>> getAll() {
        List<PersonnelResponse> list = personnelService.getAll();
        if (list.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonnelResponse> getById(@PathVariable UUID id) {
        return personnelService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PersonnelResponse> create(@RequestBody PersonnelRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personnelService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonnelResponse> update(@PathVariable UUID id,
                                                     @RequestBody PersonnelRequest request) {
        return personnelService.update(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (!personnelService.delete(id)) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}
