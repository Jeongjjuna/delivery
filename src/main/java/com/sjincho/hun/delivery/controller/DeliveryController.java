package com.sjincho.hun.delivery.controller;

import com.sjincho.hun.delivery.dto.DeliveryResponse;
import com.sjincho.hun.delivery.service.DeliveryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deliveries")
public class DeliveryController {
    private final DeliveryService deliveryService;

    public DeliveryController(final DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping("/{deliveryId}")
    public ResponseEntity<DeliveryResponse> get(@PathVariable final Long deliveryId) {
        final DeliveryResponse response = deliveryService.get(deliveryId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<DeliveryResponse>> getAll(final Pageable pageable) {
        final Page<DeliveryResponse> responses = deliveryService.getAll(pageable);

        return ResponseEntity.ok(responses);
    }
}
