package com.xcale.whatsapp.security;

import com.xcale.whatsapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();

        return Mono.just(jwtUtil.verifyToken(authToken))
                .map(decodedJWT -> jwtUtil.getName(decodedJWT))
                .flatMap(username -> userRepository.findByUsername(username))
                .map(UserPrincipal::new)
                .map(user -> new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()));
    }
}
