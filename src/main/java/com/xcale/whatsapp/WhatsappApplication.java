package com.xcale.whatsapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;

@SpringBootApplication
@EnableReactiveMongoAuditing
public class WhatsappApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhatsappApplication.class, args);
    }

}
