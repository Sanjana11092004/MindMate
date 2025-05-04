package com.mindmate.backend;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@RestController
@RequestMapping("/chat")
public class ChatController {
     @Value("${huggingface.api.key}")
     private String huggingFaceApiKey;


    @PostMapping
    public ResponseEntity<?> chat(@RequestBody Map<String, String> payload) {
        String userMessage = payload.get("message");
        if (userMessage == null || userMessage.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Message is required"));
        }

        String sentiment = callSentimentAPI(userMessage);
        String botReply = generateBotReply(sentiment);

        return ResponseEntity.ok(Map.of(
                "reply", botReply,
                "sentiment", sentiment
        ));
    }

    private String callSentimentAPI(String message) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String body = "{\"inputs\": \"" + message + "\"}";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api-inference.huggingface.co/models/distilbert-base-uncased-finetuned-sst-2-english"))
                    .header("Authorization", "Bearer " + huggingFaceApiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();
    
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();
    
            // Parse JSON properly
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);
    
            if (root.isArray() && root.size() > 0) {
                JsonNode firstArray = root.get(0);
                if (firstArray.isArray() && firstArray.size() > 0) {
                    JsonNode firstResult = firstArray.get(0);
                    if (firstResult.has("label")) {
                        String label = firstResult.get("label").asText();
                        if (label.equalsIgnoreCase("POSITIVE")) {
                            return "Positive";
                        } else if (label.equalsIgnoreCase("NEGATIVE")) {
                            return "Negative";
                        }
                    }
                }
            }
    
            return "Neutral"; // fallback if structure is unexpected
    
        } catch (Exception e) {
            e.printStackTrace();
            return "Neutral";
        }
    }
    
    private String generateBotReply(String sentiment) {
        switch (sentiment) {
            case "Positive":
                return "I'm glad to hear that! Tell me more.";
            case "Negative":
                return "I'm sorry you're feeling this way. Want to talk about it?";
            default:
                return "Thanks for sharing. Tell me more.";
        }
    }
}

