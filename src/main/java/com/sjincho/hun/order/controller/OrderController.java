package com.sjincho.hun.order.controller;

import com.sjincho.hun.auth.dto.User;
import com.sjincho.hun.delivery.exception.DeliveryNotFoundException;
import com.sjincho.hun.exception.DeliveryApplicationException;
import com.sjincho.hun.order.domain.OrderStatus;
import com.sjincho.hun.order.domain.OrderCreate;
import com.sjincho.hun.order.controller.response.OrderDetailResponse;
import com.sjincho.hun.order.controller.response.OrderSimpleResponse;
import com.sjincho.hun.order.service.OrderServiceImpl;
import com.sjincho.hun.utils.ClassUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
    private final OrderServiceImpl orderServiceImpl;

    public OrderController(final OrderServiceImpl orderServiceImpl) {
        this.orderServiceImpl = orderServiceImpl;
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("hasAnyAuthority('owner')")
    public ResponseEntity<OrderDetailResponse> get(@PathVariable final Long orderId) {
        final OrderDetailResponse response = orderServiceImpl.get(orderId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('owner')")
    public ResponseEntity<Page<OrderSimpleResponse>> getAll(final Pageable pageable) {
        final Page<OrderSimpleResponse> responses = orderServiceImpl.getAll(pageable);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/members/{memberId}")
    @PreAuthorize("hasAnyAuthority('owner')")
    public ResponseEntity<Page<OrderSimpleResponse>> getAllByMemberId(@PathVariable final Long memberId, final Pageable pageable) {
        final Page<OrderSimpleResponse> responses = orderServiceImpl.getAllByMemberId(memberId, pageable);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/in-accept")
    @PreAuthorize("hasAnyAuthority('owner')")
    public ResponseEntity<Page<OrderSimpleResponse>> getAllAcceptingOrder(final Pageable pageable) {
        final Page<OrderSimpleResponse> responses = orderServiceImpl.getAllAcceptingOrder(pageable);

        return ResponseEntity.ok(responses);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('customer')")
    public ResponseEntity<Void> order(@Valid @RequestBody final OrderCreate request, final Authentication authentication) {

        User orderer = ClassUtils.getSafeCastInstance(authentication.getPrincipal(), User.class)
                .orElseThrow(() -> new DeliveryApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, "typecasting error", "authentication typecasting error"));

        final Long orderId = orderServiceImpl.order(request, orderer.getId());

        return ResponseEntity.created(URI.create("/members/" + orderId)).build();
    }

    @PatchMapping("/{orderId}/cancel")
    @PreAuthorize("hasAnyAuthority('customer')")
    public ResponseEntity<OrderStatus> cancelOrder(@PathVariable final Long orderId, final Authentication authentication) {

        User requester = ClassUtils.getSafeCastInstance(authentication.getPrincipal(), User.class)
                .orElseThrow(() -> new DeliveryApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, "typecasting error", "authentication typecasting error"));

        final OrderStatus response = orderServiceImpl.cancelOrder(orderId, requester.getId());

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{orderId}/accept")
    @PreAuthorize("hasAnyAuthority('owner')")
    public ResponseEntity<OrderStatus> approveOrder(@PathVariable final Long orderId) {
        final OrderStatus response = orderServiceImpl.approveOrder(orderId);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{orderId}/reject")
    @PreAuthorize("hasAnyAuthority('owner')")
    public ResponseEntity<OrderStatus> rejectOrder(@PathVariable final Long orderId) {
        final OrderStatus response = orderServiceImpl.rejectOrder(orderId);

        return ResponseEntity.ok(response);
    }
}
