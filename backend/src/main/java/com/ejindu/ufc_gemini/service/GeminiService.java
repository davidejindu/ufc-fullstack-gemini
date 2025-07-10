package com.ejindu.ufc_gemini.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.ejindu.ufc_gemini.dto.FightPrediction;
import com.ejindu.ufc_gemini.parser.GeminiResponseParser;
import com.google.auth.oauth2.GoogleCredentials;

import reactor.core.publisher.Mono;

@Service
public class GeminiService {

        private static final Logger log = LoggerFactory.getLogger(GeminiService.class);
        private final WebClient webClient;

        public GeminiService(WebClient webClient) {
                this.webClient = webClient;
        }

        public Mono<FightPrediction> getGeminiResponse(String fighter1, String fighter2) {
                String prompt = String.format(
                                "Compare %s vs %s in a UFC fight. Give me the probability in a number with a percent and no decimal. Also for outcome only say what round the outcome would occur and what the outcome would be in standard format Respond in JSON format: "
                                                +
                                                "{ \"winner\": string, \"probability\": string, \"reasons\": [string, string], \"outcome\": string }. "
                                                +
                                                "Only return the JSON object.",
                                fighter1, fighter2);

                Map<String, Object> requestBody = Map.of(
                                "contents", List.of(Map.of(
                                                "role", "user",
                                                "parts", List.of(Map.of(
                                                                "text", prompt)))));

                return getAccessToken().flatMap(token -> webClient.post()
                                .uri(uriBuilder -> uriBuilder.build())
                                .header("Authorization", "Bearer " + token)
                                .bodyValue(requestBody)
                                .retrieve()
                                .bodyToMono(String.class)
                                .map(GeminiResponseParser::extractJsonFromGeminiResponse));
        }

        private Mono<String> getAccessToken() {
                try {
                        GoogleCredentials credentials = GoogleCredentials
                                        .fromStream(getClass().getClassLoader().getResourceAsStream("gemini-key.json"))
                                        .createScoped("https://www.googleapis.com/auth/cloud-platform");

                        credentials.refreshIfExpired();
                        return Mono.just(credentials.getAccessToken().getTokenValue());

                } catch (IOException e) {
                        return Mono.error(new RuntimeException("Failed to get Google access token", e));
                }
        }

}
