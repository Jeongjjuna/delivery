package com.sjincho.hun.member.repository;

import com.sjincho.hun.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(final String email);

    Optional<Member> findByName(final String email);
}
