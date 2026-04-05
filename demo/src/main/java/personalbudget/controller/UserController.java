package personalbudget.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import personalbudget.entity.UserEntity;
import personalbudget.service.UserService;

@RestController
@RequestMapping("/api/app_users")
@CrossOrigin(origins = "*")
public class UserController {

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

    // ✅ LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        UserEntity user = userService.findByEmail(request.getEmail());

        if (user == null) {
            return ResponseEntity.status(401).body("User not found");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid password");
        }

        user.setPassword(null);

        return ResponseEntity.ok(user);
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
}
