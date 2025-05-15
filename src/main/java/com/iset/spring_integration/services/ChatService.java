package com.iset.spring_integration.services;

import com.iset.spring_integration.model.ChatRequest;
import com.iset.spring_integration.model.ChatResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import java.util.*;

@Service
public class ChatService {

    @Value("${qwen.api.key}")
    private String apiKey;

    @Value("${qwen.api.url}")
    private String apiUrl;

    @Value("${qwen.model}")
    private String model;

    private final RestTemplate restTemplate;

    public ChatService() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(30000);
        requestFactory.setReadTimeout(30000);
        this.restTemplate = new RestTemplate(requestFactory);
    }

    public ChatResponse processMessage(ChatRequest request) {
        try {
            String url = apiUrl + "/chat/completions";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + apiKey);
            // Optionally add OpenRouter-specific headers here if desired
            // headers.set("HTTP-Referer", "<YOUR_SITE_URL>");
            // headers.set("X-Title", "<YOUR_SITE_NAME>");

            Map<String, Object> messageContent = new HashMap<>();
            messageContent.put("role", "user");
            messageContent.put("content", request.getMessage());

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", model);
            requestBody.put("messages", Collections.singletonList(messageContent));
            requestBody.put("temperature", 0.7);
            requestBody.put("max_tokens", 2000);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
                if (choices != null && !choices.isEmpty()) {
                    Map<String, Object> choice = choices.get(0);
                    Map<String, Object> message = (Map<String, Object>) choice.get("message");
                    String reply = (String) message.get("content");
                    return new ChatResponse(reply);
                }
            }
            return new ChatResponse("I'm sorry, I couldn't generate a response.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ChatResponse("I encountered an error processing your request. Please try again.");
        }
    }
}