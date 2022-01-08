package com.bikram.appliedproject.controller;

import com.bikram.appliedproject.service.ShippingService;
import com.bikram.appliedproject.service.dto.ShippingDto;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/shippingApi")
public class ShippingController {

    @Autowired
    private ShippingService shippingService;

    @PostMapping("/save")
    public ResponseEntity<ShippingDto> save(@RequestBody ShippingDto shippingDto){
        return ResponseEntity.ok().body(shippingService.save(shippingDto));
    }

    @GetMapping("/calculatePostage")
    public ResponseEntity<JsonNode> calculatePostage() throws Exception {
        return ResponseEntity.ok().body(shippingService.calculatePostage());
    }
}
