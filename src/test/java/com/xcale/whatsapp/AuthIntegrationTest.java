package com.xcale.whatsapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureWebTestClient
class AuthIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void signup() {
        Map<String, String> payload = new LinkedHashMap<>();
        payload.put("username", "user");
        payload.put("password", "password");

        webTestClient
                .post().uri("/auth/signup")
                .contentType(APPLICATION_JSON)
                .bodyValue(payload)
                .exchange()
                .expectStatus().isOk();
    }
}
