package com.company.common.utils.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal().toString();
    }
}
