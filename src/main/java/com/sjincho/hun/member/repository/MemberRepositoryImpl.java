package com.sjincho.hun.member.repository;

import com.sjincho.hun.member.domain.Member;
import com.sjincho.hun.member.service.port.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    public MemberRepositoryImpl(final MemberJpaRepository memberJpaRepository) {
        this.memberJpaRepository = memberJpaRepository;
    }

    @Override
    public Optional<Member> findById(final Long id) {
        return memberJpaRepository.findById(id);
    }

    @Override
    public Optional<Member> findByEmail(final String email) {
        return memberJpaRepository.findByEmail(email);
    }

    @Override
    public Page<Member> findAll(final Pageable pageable) {
        return memberJpaRepository.findAll(pageable);
    }

    @Override
    public Member save(final Member member) {
        return memberJpaRepository.save(member);
    }

    @Override
    public void delete(final Member member) {
        memberJpaRepository.delete(member);
    }
}
