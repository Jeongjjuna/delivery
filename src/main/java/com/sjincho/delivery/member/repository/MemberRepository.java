package com.sjincho.delivery.member.repository;

import com.sjincho.delivery.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
