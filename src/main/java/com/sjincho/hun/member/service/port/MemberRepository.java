package com.sjincho.hun.member.service.port;

import com.sjincho.hun.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface MemberRepository {

    Optional<Member> findById(Long id);

    Optional<Member> findByEmail(final String email);

    Page<Member> findAll(Pageable pageable);

    Member save(Member member);
}
