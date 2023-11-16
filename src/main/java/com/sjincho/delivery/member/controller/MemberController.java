package com.sjincho.delivery.member.controller;

import com.sjincho.delivery.member.dto.MemberResponse;
import com.sjincho.delivery.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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

}
