package com.sjincho.hun.order.controller;

import com.sjincho.hun.auth.dto.User;
import com.sjincho.hun.order.domain.OrderStatus;
import com.sjincho.hun.order.dto.request.OrderRequest;
import com.sjincho.hun.order.dto.response.OrderResponse;
import com.sjincho.hun.order.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
@Tag(name = "order-controller", description = "주문 서비스")
public class OrderController {
    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("hasAnyAuthority('owner')")
    public ResponseEntity<OrderResponse> get(@PathVariable final Long orderId) {
        final OrderResponse response = orderService.get(orderId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('owner')")
    public ResponseEntity<Page<OrderResponse>> getAll(final Pageable pageable) {
        final Page<OrderResponse> responses = orderService.getAll(pageable);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/members/{orderId}")
    @PreAuthorize("hasAnyAuthority('owner')")
    public ResponseEntity<Page<OrderResponse>> getAllByMemberId(@PathVariable final Long orderId, final Pageable pageable) {
        final Page<OrderResponse> responses = orderService.getAllByMemberId(orderId, pageable);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/accepting")
    @PreAuthorize("hasAnyAuthority('owner')")
    public ResponseEntity<Page<OrderResponse>> getAllAcceptingOrder(final Pageable pageable) {
        final Page<OrderResponse> responses = orderService.getAllAcceptingOrder(pageable);

        return ResponseEntity.ok(responses);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('customer')")
    public ResponseEntity<Void> order(@Valid @RequestBody final OrderRequest request, final Authentication authentication) {

        User orderer = null;
        if (authentication.getPrincipal() instanceof User) {
            orderer = (User) authentication.getPrincipal();
        }

        final Long orderId = orderService.order(request, orderer.getId());

        return ResponseEntity.created(URI.create("/members/" + orderId)).build();
    }

    @PatchMapping("/{orderId}/cancel")
    @PreAuthorize("hasAnyAuthority('customer')")
    public ResponseEntity<OrderStatus> cancelOrder(@PathVariable final Long orderId, final Authentication authentication) {

        User requester = null;
        if (authentication.getPrincipal() instanceof User) {
            requester = (User) authentication.getPrincipal();
        }

        final OrderStatus response = orderService.cancelOrder(orderId, requester.getId());

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{orderId}/accept")
    @PreAuthorize("hasAnyAuthority('owner')")
    public ResponseEntity<OrderStatus> approveOrder(@PathVariable final Long orderId) {
        final OrderStatus response = orderService.approveOrder(orderId);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{orderId}/reject")
    @PreAuthorize("hasAnyAuthority('owner')")
    public ResponseEntity<OrderStatus> rejectOrder(@PathVariable final Long orderId) {
        final OrderStatus response = orderService.rejectOrder(orderId);

        return ResponseEntity.ok(response);
    }
}
