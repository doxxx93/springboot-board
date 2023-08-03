package com.doxxx.backend.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    private SecurityUtil() { }

    public static Long getCurrentMemberId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (hasCurrentMemberId(authentication)) {
            return Long.parseLong(authentication.getName());
        }

        return null;
    }

    private static boolean hasCurrentMemberId(Authentication authentication) {
        return authentication != null && authentication.getName() != null && !authentication.getName().equals("anonymousUser");
    }
}

