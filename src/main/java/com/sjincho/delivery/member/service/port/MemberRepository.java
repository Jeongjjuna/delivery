package com.sjincho.delivery.member.service.port;

import com.sjincho.delivery.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface MemberRepository {

    Optional<Member> findById(Long id);

    Optional<Member> findByEmail(final String email);

    Page<Member> findAll(Pageable pageable);

    Member save(Member member);

    void delete(Member member);
}
