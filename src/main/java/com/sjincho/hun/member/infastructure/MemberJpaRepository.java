package com.sjincho.hun.member.infastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByEmail(final String email);

    Optional<MemberEntity> findByName(final String email);
}
