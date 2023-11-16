package com.sjincho.delivery.member.service;

import com.sjincho.delivery.exception.DeliveryApplicationException;
import com.sjincho.delivery.exception.ErrorCode;
import com.sjincho.delivery.member.domain.Member;
import com.sjincho.delivery.member.dto.MemberResponse;
import com.sjincho.delivery.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberResponse get(final Long memberId) {
        final Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new DeliveryApplicationException(ErrorCode.MEMBER_NOT_FOUND, String.format("id:%d Not Found", memberId)));

        return MemberResponse.from(member);
    }
}
