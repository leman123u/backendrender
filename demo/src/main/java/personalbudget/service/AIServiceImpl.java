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

	        String URL = "https://api.openai.com/v1/responses";

	        // Request body
	        Map<String, Object> request = new HashMap<>();
	        request.put("model", "gpt-4o-mini");
	        request.put("input", prompt);

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
	        List<?> output = (List<?>) response.get("output");
	        Map<?, ?> first = (Map<?, ?>) output.get(0);
	        List<?> content = (List<?>) first.get("content");
	        Map<?, ?> textObj = (Map<?, ?>) content.get(0);

	        return textObj.get("text").toString();
	    }

}

