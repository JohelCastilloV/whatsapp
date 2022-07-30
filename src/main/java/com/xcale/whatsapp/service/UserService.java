package com.xcale.whatsapp.service;

import com.xcale.whatsapp.model.AuthRequest;
import com.xcale.whatsapp.model.User;
import com.xcale.whatsapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Mono<User> createUser(AuthRequest authRequest) {
        User user = new User();
        user.setUsername(authRequest.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Mono<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    public Mono<Void> deleteById(String id) {
        return userRepository.deleteById(id);
    }
}
