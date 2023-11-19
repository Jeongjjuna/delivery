package com.sjincho.delivery.member.service;

import com.sjincho.delivery.exception.DeliveryApplicationException;
import com.sjincho.delivery.exception.ErrorCode;
import com.sjincho.delivery.member.domain.Member;
import com.sjincho.delivery.member.dto.MemberCreateRequest;
import com.sjincho.delivery.member.dto.MemberResponse;
import com.sjincho.delivery.member.dto.MemberUpdateRequest;
import com.sjincho.delivery.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberResponse get(final Long memberId) {
        final Member member = findExistingMember(memberId);

        return MemberResponse.from(member);
    }

    public Page<MemberResponse> getAll(Pageable pageable) {
        final Page<Member> members = memberRepository.findAll(pageable);

        return members.map(MemberResponse::from);
    }

    @Transactional
    public Long register(final MemberCreateRequest request) {
        checkDuplicatedEmail(request.getEmail());

        final Member member = Member.create(
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getCellPhone(),
                request.getMemberRole()
        );

        final Member saved = memberRepository.save(member);

        return saved.getId();
    }

    @Transactional
    public MemberResponse update(final Long memberId, final MemberUpdateRequest request) {
        final Member member = findExistingMember(memberId);

        checkDuplicatedEmail(request.getEmail());

        member.update(
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getCellPhone(),
                request.getMemberRole()
        );

        final Member updatedMember = memberRepository.save(member);

        return MemberResponse.from(updatedMember);
    }

    @Transactional
    public void delete(final Long memberId) {
        final Member member = findExistingMember(memberId);

        memberRepository.delete(member);
    }

    private void checkDuplicatedEmail(final String email) {
        memberRepository.findByEmail(email).ifPresent(value -> {
            throw new DeliveryApplicationException(ErrorCode.DUPLICATED_USER_EMAIL, String.format("email:%s Duplicated", email));
        });
    }

    private Member findExistingMember(final Long id) {
        return memberRepository.findById(id).orElseThrow(() ->
                new DeliveryApplicationException(ErrorCode.MEMBER_NOT_FOUND, String.format("id:%d Not Found", id)));
    }

}
