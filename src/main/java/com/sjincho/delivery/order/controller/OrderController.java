package com.sjincho.delivery.order.controller;

import com.sjincho.delivery.order.domain.OrderStatus;
import com.sjincho.delivery.order.dto.OrderAcceptRequest;
import com.sjincho.delivery.order.dto.OrderResponse;
import com.sjincho.delivery.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;

@RestController
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderResponse> get(@PathVariable final Long id) {
        final OrderResponse response = orderService.get(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/orders")
    public ResponseEntity<Page<OrderResponse>> getAll(Pageable pageable) {
        final Page<OrderResponse> responses = orderService.getAll(pageable);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/orders/members/{id}")
    public ResponseEntity<Page<OrderResponse>> getAllByMemberId(@PathVariable final Long id, Pageable pageable) {
        final Page<OrderResponse> responses = orderService.getAllByMemberId(id, pageable);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/orders/accepting")
    public ResponseEntity<Page<OrderResponse>> getAllAcceptingOrder(Pageable pageable) {
        final Page<OrderResponse> responses = orderService.getAllAcceptingOrder(pageable);

        return ResponseEntity.ok(responses);
    }

    @PostMapping("/orders")
    public ResponseEntity<Void> acceptOrder(@RequestBody final OrderAcceptRequest request) {
        final Long orderId = orderService.acceptOrder(request);

        return ResponseEntity.created(URI.create("/members/" + orderId)).build();
    }

    @PatchMapping("/orders/{id}/accept")
    public ResponseEntity<OrderStatus> approveOrder(@PathVariable final Long id) {
        final OrderStatus response = orderService.approveOrder(id);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/orders/{id}/reject")
    public ResponseEntity<OrderStatus> rejectOrder(@PathVariable final Long id) {
        final OrderStatus response = orderService.rejectOrder(id);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/orders/{id}/cancel")
    public ResponseEntity<OrderStatus> cancelOrder(@PathVariable final Long id) {
        final OrderStatus response = orderService.cancelOrder(id);

        return ResponseEntity.ok(response);
    }
}
