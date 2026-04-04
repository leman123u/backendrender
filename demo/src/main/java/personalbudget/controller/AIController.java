package personalbudget.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
	  public String askAI(@RequestBody Map<String, String> body) {
	      return aiService.askAI(body.get("prompt"));
	  }
	
	

}

