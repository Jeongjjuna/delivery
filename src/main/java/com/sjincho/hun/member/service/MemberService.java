package com.sjincho.hun.member.service;

import com.sjincho.hun.member.domain.Member;
import com.sjincho.hun.member.dto.MemberCreateRequest;
import com.sjincho.hun.member.dto.MemberResponse;
import com.sjincho.hun.member.dto.MemberUpdateRequest;
import com.sjincho.hun.member.exception.MemberEmailDuplicatedException;
import com.sjincho.hun.member.exception.MemberErrorCode;
import com.sjincho.hun.member.exception.MemberNotFoundException;
import com.sjincho.hun.member.exception.UnAuthorizedUpdateException;
import com.sjincho.hun.member.service.port.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public MemberService(final MemberRepository memberRepository, final BCryptPasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public MemberResponse get(final Long memberId) {
        final Member member = findExistingMember(memberId);

        return MemberResponse.from(member);
    }

    public Page<MemberResponse> getAll(final Pageable pageable) {
        final Page<Member> members = memberRepository.findAll(pageable);

        return members.map(MemberResponse::from);
    }

    @Transactional
    public Long register(final MemberCreateRequest request) {
        checkDuplicatedEmail(request.getEmail());

        final Member member = request.toEntity();

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        member.applyEncodedPassword(encodedPassword);

        final Member saved = memberRepository.save(member);

        return saved.getId();
    }

    @Transactional
    public MemberResponse update(final Long memberId, final MemberUpdateRequest request, Long requesterId) {
        if (memberId != requesterId) {
            throw new UnAuthorizedUpdateException(MemberErrorCode.UNAUTHORIZED_UPDATE, memberId, requesterId);
        }

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
    public void delete(final Long memberId, Long requesterId) {
        if (memberId != requesterId) {
            throw new UnAuthorizedUpdateException(MemberErrorCode.UNAUTHORIZED_UPDATE, memberId, requesterId);
        }

        final Member member = findExistingMember(memberId);

        member.delete();

        memberRepository.save(member);
    }

    private void checkDuplicatedEmail(final String email) {
        memberRepository.findByEmail(email).ifPresent(value -> {
            throw new MemberEmailDuplicatedException(MemberErrorCode.DUPLICATED_EMAIL, email);
        });
    }

    private Member findExistingMember(final Long id) {
        return memberRepository.findById(id).orElseThrow(() ->
                new MemberNotFoundException(MemberErrorCode.NOT_FOUND, id));
    }

}
