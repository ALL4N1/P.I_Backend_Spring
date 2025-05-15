package com.iset.spring_integration.services;

import com.iset.spring_integration.model.ChatRequest;
import com.iset.spring_integration.model.ChatResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import java.util.*;

@Service
public class ChatGPTService {
    private static final Logger logger = LoggerFactory.getLogger(ChatGPTService.class);

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${openai.model}")
    private String model;

    private final RestTemplate restTemplate;

    public ChatGPTService() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(30000); // 30 seconds
        requestFactory.setReadTimeout(30000);    // 30 seconds
        this.restTemplate = new RestTemplate(requestFactory);
    }

    public ChatResponse generateResponse(String message) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);
            headers.set("User-Agent", "Spring RestTemplate");

            Map<String, String> messageMap = new HashMap<>();
            messageMap.put("role", "user");
            messageMap.put("content", message);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", model);
            requestBody.put("messages", Collections.singletonList(messageMap));
            requestBody.put("temperature", 0.7);
            requestBody.put("max_tokens", 2000);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            String fullUrl = apiUrl + "/v1/chat/completions";
            logger.info("Attempting to send request to: {}", fullUrl);
            logger.debug("Request headers: {}", headers);
            logger.debug("Request body: {}", requestBody);

            try {
                ResponseEntity<Map> response = restTemplate.exchange(
                    fullUrl,
                    HttpMethod.POST,
                    request,
                    Map.class
                );

                logger.info("Response received successfully");
                logger.debug("Response status: {}", response.getStatusCode());
                logger.debug("Response body: {}", response.getBody());

                if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                    Map<String, Object> responseBody = response.getBody();
                    List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
                    
                    if (choices != null && !choices.isEmpty()) {
                        Map<String, Object> choice = choices.get(0);
                        Map<String, String> messageResponse = (Map<String, String>) choice.get("message");
                        String reply = messageResponse.get("content");
                        return new ChatResponse(reply);
                    }
                }
            } catch (Exception e) {
                logger.error("Error during API call: {} - {}", e.getClass().getName(), e.getMessage());
                throw e;
            }
            
            logger.error("Invalid response format from OpenAI API");
            return new ChatResponse("I apologize, but I couldn't generate a response at this time.");
        } catch (Exception e) {
            logger.error("Error while calling OpenAI API: {} - {}", e.getClass().getName(), e.getMessage(), e);
            return new ChatResponse("An error occurred while processing your request. Error details: " + e.getMessage());
        }
    }
}
