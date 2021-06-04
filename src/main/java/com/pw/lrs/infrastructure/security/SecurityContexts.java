package com.pw.lrs.infrastructure.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContexts {

    public static String getAuthenticatedUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
