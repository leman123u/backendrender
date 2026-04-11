package personalbudget.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import personalbudget.entity.UserEntity;
import personalbudget.exception.ResetTokenException;
import personalbudget.security.JwtUtil;
import personalbudget.service.UserService;

@RestController
@RequestMapping("/api/app_users")
@CrossOrigin(origins = "*")
public class UserController {
	
	@Autowired
	private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ REGISTER
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserEntity user) {

        if (user.getEmail() == null || user.getPassword() == null) {
            return ResponseEntity.badRequest().body("Email and password required");
        }

        if (userService.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        UserEntity savedUser = userService.save(user);

        // 🔐 password gizlət
        savedUser.setPassword(null);

        return ResponseEntity.ok(savedUser);
    }

    /**
     * Handles GET (e.g. browser address bar or probes) so the path does not return 405.
     * Authentication still uses POST with JSON body.
     */
    @GetMapping("/login")
    public ResponseEntity<Map<String, String>> loginGet() {
        return ResponseEntity.ok(Map.of(
                "message", "Use POST with Content-Type: application/json and body {\"email\":\"...\",\"password\":\"...\"}"
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserEntity request) {

        if (request == null || request.getEmail() == null || request.getPassword() == null) {
            return ResponseEntity.badRequest().body("Email and password required");
        }

        UserEntity user = userService.findByEmail(request.getEmail());

        if (user == null) {
            return ResponseEntity.status(401).body("User not found");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        user.setPassword(null);

        return ResponseEntity.ok(Map.of(
                "token", token,
                "user", user
        ));
    }
    

    // ✅ FORGOT PASSWORD
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody UserEntity request) {

        if (request.getEmail() == null) {
            return ResponseEntity.badRequest().body("Email required");
        }

        userService.createResetToken(request.getEmail());

        return ResponseEntity.ok("Reset link sent to email");
    }

    // ✅ RESET PASSWORD
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestParam String token,
            @RequestParam String password
    ) {

        if (token == null || password == null) {
            return ResponseEntity.badRequest().body("Token and password required");
        }

        userService.resetPassword(token, password);

        return ResponseEntity.ok("Password reset successful");
    }

    @ExceptionHandler(ResetTokenException.class)
    public ResponseEntity<String> handleResetToken(ResetTokenException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    
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
