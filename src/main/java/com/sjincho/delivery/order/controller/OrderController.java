package com.sjincho.delivery.order.controller;

import com.sjincho.delivery.order.domain.OrderStatus;
import com.sjincho.delivery.order.dto.OrderAcceptRequest;
import com.sjincho.delivery.order.dto.OrderResponse;
import com.sjincho.delivery.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> get(@PathVariable final Long orderId) {
        final OrderResponse response = orderService.get(orderId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<OrderResponse>> getAll(final Pageable pageable) {
        final Page<OrderResponse> responses = orderService.getAll(pageable);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/members/{orderId}")
    public ResponseEntity<Page<OrderResponse>> getAllByMemberId(@PathVariable final Long orderId, final Pageable pageable) {
        final Page<OrderResponse> responses = orderService.getAllByMemberId(orderId, pageable);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/accepting")
    public ResponseEntity<Page<OrderResponse>> getAllAcceptingOrder(final Pageable pageable) {
        final Page<OrderResponse> responses = orderService.getAllAcceptingOrder(pageable);

        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<Void> order(@Valid @RequestBody final OrderAcceptRequest request) {
        final Long orderId = orderService.order(request);

        return ResponseEntity.created(URI.create("/members/" + orderId)).build();
    }

    @PatchMapping("/{orderId}/accept")
    public ResponseEntity<OrderStatus> approveOrder(@PathVariable final Long orderId) {
        final OrderStatus response = orderService.approveOrder(orderId);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{orderId}/reject")
    public ResponseEntity<OrderStatus> rejectOrder(@PathVariable final Long orderId) {
        final OrderStatus response = orderService.rejectOrder(orderId);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<OrderStatus> cancelOrder(@PathVariable final Long orderId) {
        final OrderStatus response = orderService.cancelOrder(orderId);

        return ResponseEntity.ok(response);
    }
}
