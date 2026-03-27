package personalbudget.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin
public class UserController {
	
	 @Autowired
	    private UserService userService;
	 
	
	    @PostMapping("/register")
	    public UserEntity register(@RequestBody UserEntity user) {

	        if (userService.existsByEmail(user.getEmail())) {
	            throw new RuntimeException("Email already exists");
	        }

	        return userService.save(user);
	    }


	    // ✅ LOGIN
	    @PostMapping("/login")
	    public UserEntity login(
	            @RequestParam String email,
	            @RequestParam String password
	    ) {

	        UserEntity user = userService.findByEmail(email);

	        return user;
	    }


	    // ✅ FORGOT PASSWORD
	    @PostMapping("/forgot-password")
	    public String forgotPassword(@RequestParam String email) {

	        UUID token = userService.createResetToken(email);

	        return "Reset token created: " + token;
	    }


	    // ✅ RESET PASSWORD
	    @PostMapping("/reset-password")
	    public String resetPassword(
	            @RequestParam UUID token,
	            @RequestParam String newPassword
	    ) {

	        userService.resetPassword(token, newPassword);

	        return "Password updated";
	    }

	
	

}
