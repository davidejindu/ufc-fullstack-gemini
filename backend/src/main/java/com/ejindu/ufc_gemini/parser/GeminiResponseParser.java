package com.ejindu.ufc_gemini.parser;

import com.ejindu.ufc_gemini.dto.FightPrediction;
import com.google.gson.*;

public class GeminiResponseParser {

    public static FightPrediction extractJsonFromGeminiResponse(String rawResponse) {
        try {
            JsonObject root = JsonParser.parseString(rawResponse).getAsJsonObject();
            JsonArray candidates = root.getAsJsonArray("candidates");

            JsonObject firstCandidate = candidates.get(0).getAsJsonObject();
            JsonObject content = firstCandidate.getAsJsonObject("content");
            JsonArray parts = content.getAsJsonArray("parts");
            String text = parts.get(0).getAsJsonObject().get("text").getAsString();

            // Remove markdown wrapper if present
            if (text.startsWith("```json")) {
                text = text.replaceAll("```json", "").replaceAll("```", "").trim();
            }

            return new Gson().fromJson(text, FightPrediction.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract or parse Gemini response", e);
        }
    }
}
