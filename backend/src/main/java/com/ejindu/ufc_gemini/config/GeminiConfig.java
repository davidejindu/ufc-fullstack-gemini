package com.ejindu.ufc_gemini.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;


    @Configuration
    public class GeminiConfig {

        @Bean
        public WebClient webClient(WebClient.Builder builder) {
            return builder
                    .baseUrl("https://aiplatform.googleapis.com/v1/projects/gen-lang-client-0069366386/locations/global/publishers/google/models/gemini-2.5-pro:generateContent")
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .build();
        }
    }


