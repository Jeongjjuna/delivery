package com.sjincho.hun.member.controller.port;

import com.sjincho.hun.member.controller.response.MemberResponse;
import com.sjincho.hun.member.domain.MemberCreate;
import com.sjincho.hun.member.domain.MemberUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberService {

    MemberResponse get(final Long memberId);

    Page<MemberResponse> getAll(final Pageable pageable);

    Long register(final MemberCreate request);

    MemberResponse update(final Long memberId, final MemberUpdate memberUpdate, Long requesterId);

    void delete(final Long memberId, Long requesterId);
}
