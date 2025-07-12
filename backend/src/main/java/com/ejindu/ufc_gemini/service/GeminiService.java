package com.ejindu.ufc_gemini.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.ejindu.ufc_gemini.parser.GeminiResponseParser;
import com.google.auth.oauth2.GoogleCredentials;

import lombok.Data;
import reactor.core.publisher.Mono;

@Service
public class GeminiService {

        private static final Logger log = LoggerFactory.getLogger(GeminiService.class);
        private final WebClient webClient;
        private final Map<String, CacheEntry<Object>> comparisonCache = new ConcurrentHashMap<>();
        private static final long CACHE_TTL_MILLIS = 5 * 60 * 1000; // 5 minutes

        public GeminiService(WebClient webClient) {
                this.webClient = webClient;
        }

        @Data
        private static class CacheEntry<T> {
                private final T value;
                private final long expiryTimeMillis;

                public boolean isExpired() {
                        return System.currentTimeMillis() > expiryTimeMillis;
                }
        }

        public Mono<Object> getGeminiResponse(String fighter1, String fighter2) {
                // Order-insensitive key
                String[] fighters = { fighter1.toLowerCase(), fighter2.toLowerCase() };
                Arrays.sort(fighters);
                String key = fighters[0] + "|" + fighters[1];

                CacheEntry<Object> entry = comparisonCache.get(key);
                if (entry != null && !entry.isExpired()) {
                        return Mono.just(entry.getValue());
                }

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
                                .map(GeminiResponseParser::extractJsonFromGeminiResponse)
                                .doOnNext(result -> {
                                        comparisonCache.put(key, new CacheEntry<>(result,
                                                        System.currentTimeMillis() + CACHE_TTL_MILLIS));
                                }));
        }

        private Mono<String> getAccessToken() {
                try {
                        String base64Key = System.getenv("GEMINI_KEY_BASE64");
                        GoogleCredentials credentials;
                        if (base64Key != null) {
                                byte[] decoded = Base64.getDecoder().decode(base64Key);
                                InputStream keyStream = new ByteArrayInputStream(decoded);
                                credentials = GoogleCredentials.fromStream(keyStream)
                                                .createScoped("https://www.googleapis.com/auth/cloud-platform");
                        } else {
                                // fallback for local dev
                                credentials = GoogleCredentials
                                                .fromStream(getClass().getClassLoader()
                                                                .getResourceAsStream("gemini-key.json"))
                                                .createScoped("https://www.googleapis.com/auth/cloud-platform");
                        }
                        credentials.refreshIfExpired();
                        return Mono.just(credentials.getAccessToken().getTokenValue());
                } catch (IOException e) {
                        return Mono.error(new RuntimeException("Failed to get Google access token", e));
                }
        }

}
