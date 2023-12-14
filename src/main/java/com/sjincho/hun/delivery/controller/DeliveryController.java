package com.sjincho.hun.delivery.controller;

import com.sjincho.hun.delivery.controller.response.DeliveryResponse;
import com.sjincho.hun.delivery.service.DeliveryServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;

@RestController
@RequestMapping("/deliveries")
@Tag(name = "delivery-controller", description = "배달 서비스")
public class DeliveryController {
    private final DeliveryServiceImpl deliveryServiceImpl;

    public DeliveryController(final DeliveryServiceImpl deliveryServiceImpl) {
        this.deliveryServiceImpl = deliveryServiceImpl;
    }

    @GetMapping("/{deliveryId}")
    @PreAuthorize("hasAnyAuthority('owner')")
    public ResponseEntity<DeliveryResponse> get(@PathVariable final Long deliveryId) {
        final DeliveryResponse response = deliveryServiceImpl.get(deliveryId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('owner')")
    public ResponseEntity<Page<DeliveryResponse>> getAll(final Pageable pageable) {
        final Page<DeliveryResponse> responses = deliveryServiceImpl.getAll(pageable);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/in-delivery")
    @PreAuthorize("hasAnyAuthority('owner')")
    public ResponseEntity<Page<DeliveryResponse>> getAllDelivering(final Pageable pageable) {
        final Page<DeliveryResponse> responses = deliveryServiceImpl.getAllDelivering(pageable);

        return ResponseEntity.ok(responses);
    }

    @PostMapping("/orders/{orderId}")
    @PreAuthorize("hasAnyAuthority('owner')")
    public ResponseEntity<Void> resister(@PathVariable final Long orderId) {
        final Long deliveryId = deliveryServiceImpl.resister(orderId);

        return ResponseEntity.created(URI.create("/delivery/" + deliveryId)).build();
    }

    @PatchMapping("/{deliveryId}/start")
    @PreAuthorize("hasAnyAuthority('owner')")
    public ResponseEntity<DeliveryResponse> startDelivery(@PathVariable final Long deliveryId) {
        deliveryServiceImpl.startDelivery(deliveryId);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{deliveryId}/complete")
    @PreAuthorize("hasAnyAuthority('owner')")
    public ResponseEntity<DeliveryResponse> completeDelivery(@PathVariable final Long deliveryId) {
        deliveryServiceImpl.completeDelivery(deliveryId);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{deliveryId}/cancel")
    @PreAuthorize("hasAnyAuthority('owner')")
    public ResponseEntity<Void> cancelDelivery(@PathVariable final Long deliveryId) {
        deliveryServiceImpl.cancelDelivery(deliveryId);
        return ResponseEntity.ok().build();
    }
}
