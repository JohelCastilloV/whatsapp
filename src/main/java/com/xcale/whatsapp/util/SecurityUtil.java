package com.xcale.whatsapp.util;

import com.xcale.whatsapp.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import reactor.core.publisher.Mono;

public class SecurityUtil {

    public static Mono<UserPrincipal> getPrincipal() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .cast(UserPrincipal.class);
    }
}
