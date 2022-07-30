package com.xcale.whatsapp.service;

import com.xcale.whatsapp.model.AuthRequest;
import com.xcale.whatsapp.model.AuthResponse;
import com.xcale.whatsapp.model.User;
import com.xcale.whatsapp.repository.UserRepository;
import com.xcale.whatsapp.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    public Mono<User> createUser(AuthRequest authRequest) {
        User user = new User();
        user.setUsername(authRequest.getUsername());
        user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        return userRepository.save(user);
    }

    public Mono<AuthResponse> login(@RequestBody AuthRequest authRequest){
        return userRepository.findByUsername(authRequest.getUsername())
                .filter(userDetails -> passwordEncoder.matches(authRequest.getPassword(), userDetails.getPassword()))
                .map(userDetails -> new AuthResponse(jwtUtil.createToken(userDetails)));

    }
}
