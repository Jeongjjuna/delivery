package com.sjincho.hun.config;

import com.sjincho.hun.member.domain.MemberRole;
import org.springframework.security.test.context.support.WithSecurityContext;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface OwnerMockUser {

    String email() default "owner@gmail.com";
    MemberRole role() default MemberRole.OWNER;
}