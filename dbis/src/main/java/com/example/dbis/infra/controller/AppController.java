package com.example.dbis.infra.controller;

import com.example.dbis.app.EstateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AppController {

    private final EstateService service;

    @GetMapping("/estates")
    public String getEstates() {
        return service.findAllEstates().toString();
    }
}
