package personalbudget.controller;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

	        if (userService.existsByEmail(user.getEmail())) {
	            return ResponseEntity.badRequest().body("Email already exists");
	        }

	        return ResponseEntity.ok(userService.save(user));
	    }

	    // ✅ LOGIN
	    @PostMapping("/login")
	    public ResponseEntity<?> login(@RequestBody UserEntity request) {

	        UserEntity user = userService.findByEmail(request.getEmail());

	        if (user == null || 
	            !passwordEncoder.matches(request.getPassword(), user.getPassword())) {

	            return ResponseEntity.status(401).body("Invalid email or password");
	        }

	        return ResponseEntity.ok(user);
	    }

	    // ✅ FORGOT PASSWORD (SendGrid burada işləyir)
	    @PostMapping("/forgot-password")
	    public ResponseEntity<?> forgotPassword(@RequestBody UserEntity request) {

	        userService.createResetToken(request.getEmail());

	        return ResponseEntity.ok("Reset link sent to email");
	    }

	    // ✅ RESET PASSWORD
	    @PostMapping("/reset-password")
	    public ResponseEntity<?> resetPassword(
	            @RequestParam String token,
	            @RequestParam String password
	    ) {

	        userService.resetPassword(token, password);

	        return ResponseEntity.ok("Password reset successful");
	    }
	
	    @PostMapping("/support")
	    public ResponseEntity<?> support(@RequestBody SupportRequest request) {

	        userService.sendSupportMessage(
	            request.getEmail(),
	            request.getMessage()
	        );

	        return ResponseEntity.ok("Support message sent");
	    }
	    

}
