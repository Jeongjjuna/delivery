package com.sjincho.hun.auth.dto;

import com.sjincho.hun.member.domain.Member;
import com.sjincho.hun.member.domain.MemberRole;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class User implements UserDetails {
    private final Long id;
    private final String name;
    private final String email;
    private final String password;
    private final String cellPhone;
    private Collection<? extends GrantedAuthority> authorities;

    @Builder
    public User(final Long id, final String name, final String email,
                final String password, final String cellPhone,
                final Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.cellPhone = cellPhone;
        this.authorities = authorities;
    }

    public static User from(Member member) {
        Set<MemberRole> roleTypes = Set.of(member.getMemberRole());

        return User.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .password(member.getPassword())
                .cellPhone(member.getCellPhone())
                .authorities(
                        roleTypes.stream()
                                .map(MemberRole::getName)
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toUnmodifiableSet()))
                .build();
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
