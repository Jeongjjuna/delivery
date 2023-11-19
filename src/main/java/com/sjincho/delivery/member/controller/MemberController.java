package com.sjincho.delivery.member.controller;

import com.sjincho.delivery.member.dto.MemberCreateRequest;
import com.sjincho.delivery.member.dto.MemberResponse;
import com.sjincho.delivery.member.dto.MemberUpdateRequest;
import com.sjincho.delivery.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;

@RestController
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<MemberResponse> get(@PathVariable final Long id) {
        final MemberResponse response = memberService.get(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/members")
    public ResponseEntity<Page<MemberResponse>> getAll(final Pageable pageable) {
        final Page<MemberResponse> responses = memberService.getAll(pageable);

        return ResponseEntity.ok(responses);
    }

    @PostMapping("/members")
    public ResponseEntity<Void> resister(@RequestBody final MemberCreateRequest request) {
        final Long memberId = memberService.register(request);

        return ResponseEntity.created(URI.create("/members/" + memberId)).build();
    }

    @PutMapping("/members/{id}")
    public ResponseEntity<MemberResponse> update(
            @PathVariable final Long id,
            @RequestBody final MemberUpdateRequest request) {
        final MemberResponse response = memberService.update(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        memberService.delete(id);
        return ResponseEntity.ok().build();
    }

}
