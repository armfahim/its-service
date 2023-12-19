package com.its.service.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static com.its.service.enums.Permission.*;


@RequiredArgsConstructor
public enum Role {

    //    USER(Collections.emptySet()),
    ADMIN(Set.of(ADMIN_READ, ADMIN_UPDATE, ADMIN_DELETE, ADMIN_CREATE
            , EMPLOYEE_READ, EMPLOYEE_UPDATE, EMPLOYEE_DELETE, EMPLOYEE_CREATE)),
    EMPLOYEE(Set.of(EMPLOYEE_READ, EMPLOYEE_UPDATE, EMPLOYEE_DELETE, EMPLOYEE_CREATE));

    @Getter
    private final Set<Permission> permissions;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
