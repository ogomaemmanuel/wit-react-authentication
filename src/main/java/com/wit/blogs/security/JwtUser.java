package com.wit.blogs.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.lang.Nullable;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;
@Getter
@AllArgsConstructor
public class JwtUser implements AuthenticatedPrincipal {

    private String name;
    private Long id;

    private Map<String, Object> attributes;

    private Collection<? extends GrantedAuthority> authorities;


}
