package com.xcale.whatsapp.controller;


import com.xcale.whatsapp.model.AuthRequest;
import com.xcale.whatsapp.model.AuthResponse;
import com.xcale.whatsapp.model.User;
import com.xcale.whatsapp.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("signup")
    public Mono<User> signup(@RequestBody AuthRequest authRequest){
        return authService.createUser(authRequest);
    }

    @PostMapping("login")
    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody AuthRequest authRequest){
        return authService.login(authRequest)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));

    }
}
