package com.logistics.mlogistics.controllers;

import com.logistics.mlogistics.domain.Base;
import com.logistics.mlogistics.service.BaseService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/base")
public class BaseController {

    private final BaseService baseService;

    @Autowired
    public BaseController(BaseService baseService) {
        this.baseService = baseService;
    }

    @GetMapping()
    public ResponseEntity<?> getAllBases(){
        List<Base> bases = baseService.getAllBases();

        if(bases.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Bases not found");

        return ResponseEntity.ok(bases);
    }
}
