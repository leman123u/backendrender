package personalbudget.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import personalbudget.service.UserService;

/**
 * Alias for contact/support: frontend and proxies often call {@code POST /api/support}
 * while {@link UserController} exposes {@code POST /api/app_users/support}.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class SupportController {

    @Autowired
    private UserService userService;

    @PostMapping("/support")
    public ResponseEntity<?> support(@RequestBody Map<String, String> request) {

        String email = request.get("email");
        String message = request.get("message");

        if (email == null || message == null) {
            return ResponseEntity.badRequest().body("Email and message required");
        }

        userService.sendSupportMessage(email, message);

        return ResponseEntity.ok("Message sent successfully");
    }
}

