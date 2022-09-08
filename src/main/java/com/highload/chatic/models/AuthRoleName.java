package com.highload.chatic.models;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public enum AuthRoleName implements GrantedAuthority {
    ADMIN,
    USER;

    @Override
    public String getAuthority() {
        return toString();
    }

    public static boolean isNotSuperUser(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .map(AuthRoleName::valueOf)
                .noneMatch(AuthRoleName.ADMIN::equals);
    }
}
