package personalbudget.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import personalbudget.service.AIService;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "*")
public class AIController {
	
	@Autowired
    private AIService aiService;

    // ✅ AI CHAT ENDPOINT
    @PostMapping("/ask")
    public ResponseEntity<?> askAI(@RequestBody Map<String, String> body) {

        String prompt = body.get("prompt");

        // 🔥 VALIDATION
        if (prompt == null || prompt.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Prompt is required");
        }

        try {
            String response = aiService.askAI(prompt);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("AI error: " + e.getMessage());
        }
    }
	
	

}
