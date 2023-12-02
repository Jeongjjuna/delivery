package com.sjincho.hun.member.controller;

import com.sjincho.hun.auth.dto.User;
import com.sjincho.hun.member.dto.MemberCreateRequest;
import com.sjincho.hun.member.dto.MemberResponse;
import com.sjincho.hun.member.dto.MemberUpdateRequest;
import com.sjincho.hun.member.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;

@RestController
@RequestMapping("/members")
@Tag(name = "member-controller", description = "회원 서비스")
public class MemberController {
    private final MemberService memberService;

    public MemberController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/{memberId}")
    @PreAuthorize("hasAnyAuthority('owner')")
    public ResponseEntity<MemberResponse> get(@PathVariable final Long memberId) {
        final MemberResponse response = memberService.get(memberId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/my")
    @PreAuthorize("hasAnyAuthority('customer', 'owner')")
    public ResponseEntity<MemberResponse> my(Authentication authentication) {
        User user = null;
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            user = (User) authentication.getPrincipal();
        }
        final MemberResponse response = memberService.get(user.getId());

        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('owner')")
    public ResponseEntity<Page<MemberResponse>> getAll(final Pageable pageable) {
        final Page<MemberResponse> responses = memberService.getAll(pageable);

        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<Void> resister(@Valid @RequestBody final MemberCreateRequest request) {
        final Long memberId = memberService.register(request);

        return ResponseEntity.created(URI.create("/members/" + memberId)).build();
    }

    @PutMapping("/{memberId}")
    @PreAuthorize("hasAnyAuthority('customer', 'owner')")
    public ResponseEntity<MemberResponse> update(
            @PathVariable final Long memberId,
            @Valid @RequestBody final MemberUpdateRequest request,
            final Authentication authentication) {

        User requester = null;
        if (authentication.getPrincipal() instanceof User) {
            requester = (User) authentication.getPrincipal();
        }

        final MemberResponse response = memberService.update(memberId, request, requester.getId());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{memberId}")
    @PreAuthorize("hasAnyAuthority('customer', 'owner')")
    public ResponseEntity<Void> delete(@PathVariable final Long memberId, Authentication authentication) {

        User requester = null;
        if (authentication.getPrincipal() instanceof User) {
            requester = (User) authentication.getPrincipal();
        }

        memberService.delete(memberId, requester.getId());
        return ResponseEntity.ok().build();
    }

}
