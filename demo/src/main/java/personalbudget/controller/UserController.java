package personalbudget.controller;


import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin
public class UserController {
	

 @Autowired
	    private UserService userService;
	 
	 @Autowired
	 private PasswordEncoder passwordEncoder;
	
	    @PostMapping("/register")
	    public UserEntity register(@RequestBody UserEntity user) {

	        if (userService.existsByEmail(user.getEmail())) {
	            throw new RuntimeException("Email already exists");
	        }

	        return userService.save(user);
	    }


	    @PostMapping("/login")
	    public UserEntity login(@RequestBody UserEntity request) {

	        UserEntity user = userService.findByEmail(request.getEmail());

	        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
	            throw new RuntimeException("Wrong password");
	        }

	        return user;
	    }


	    @PostMapping("/forgot-password")
	    public String forgotPassword(@RequestParam String email) {

	        String token = userService.createResetToken(email);

	        return "Reset token created: " + token;
	    }


	    @PostMapping("/reset-password")
	    public String resetPassword(
	            @RequestParam String token,
	            @RequestParam String newPassword
	    ) {

	        userService.resetPassword(token, newPassword);

	        return "Password updated";
	    }
	


}
