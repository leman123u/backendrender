package personalbudget.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
@Service
public class AIServiceImpl implements AIService {
	
	
	@Value("${openai.api.key}")
    private String apiKey;

	@Override
	public String askAI(String prompt) {
		RestTemplate restTemplate = new RestTemplate();

        String URL = "https://api.openai.com/v1/chat/completions";

        // Request body
        Map<String, Object> request = new HashMap<>();
        request.put("model", "gpt-4o-mini");
        request.put("messages", List.of(
                Map.of("role", "user", "content", prompt)
        ));

        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(request, headers);

        // Response
        @SuppressWarnings("unchecked")
        Map<String, Object> response =
                restTemplate.postForObject(URL, entity, Map.class);

        // Parsing response
        if (response == null) {
            throw new RuntimeException("Empty response from OpenAI");
        }

        List<?> choices = (List<?>) response.get("choices");
        if (choices == null || choices.isEmpty()) {
            throw new RuntimeException("No choices in OpenAI response");
        }

        Map<?, ?> firstChoice = (Map<?, ?>) choices.get(0);
        Map<?, ?> message = (Map<?, ?>) firstChoice.get("message");
        if (message == null || message.get("content") == null) {
            throw new RuntimeException("Invalid OpenAI response format");
        }

        return message.get("content").toString();
    }


}
