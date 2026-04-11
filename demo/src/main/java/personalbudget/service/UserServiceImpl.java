package personalbudget.service;


import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import personalbudget.entity.UserEntity;
import personalbudget.exception.ResetTokenException;
import personalbudget.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	
	
	@Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

   @Value("${app.frontend.url}")
    private String frontendUrl;

    @Value("${app.support.inbox}")
    private String supportInbox;	
	  
   
	@Override
	public UserEntity findByEmail(String email) {
		 return userRepository.findByEmail(email).orElse(null);
	}
	

	@Override
	public UserEntity save(UserEntity user) {
		
		 user.setPassword(passwordEncoder.encode(user.getPassword()));
	        return userRepository.save(user);
	}

	
	

	@Override
	public boolean existsByEmail(String email) {
		 return userRepository.existsByEmail(email);
	}

	@Override
	public Optional<UserEntity> findByResetToken(String token) {
		   return userRepository.findByResetToken(token);
	}

	@Override
	public void resetPassword(String token, String newPassword) {

        UserEntity user = userRepository.findByResetToken(token)
                .orElseThrow(() -> new ResetTokenException("Invalid token"));

        if (user.getResetTokenExpiry() == null ||
            user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new ResetTokenException("Token expired");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);

        userRepository.save(user);
	    
	}

	

	@Override
	public String createResetToken(String email) {
		 UserEntity user = userRepository.findByEmail(email)
	                .orElseThrow(() -> new RuntimeException("User not found"));

	        String token = UUID.randomUUID().toString();

	        user.setResetToken(token);
	        user.setResetTokenExpiry(LocalDateTime.now().plusMinutes(15));

	        userRepository.save(user);

	        String link = frontendUrl + "/reset-password?token=" + token;

	        System.out.println("RESET LINK: " + link);

	        try {
	            emailService.sendResetToken(user.getEmail(), link);
	        } catch (Exception e) {
	            System.out.println("Email failed: " + e.getMessage());
	        }

	        return token;
	    }


	@Override
	public void sendSupportMessage(String email, String message) {

        String text =
                "New Support Message\n\n" +
                "From: " + email + "\n\n" +
                "Message:\n" + message;

        try {
            emailService.sendEmail(supportInbox, "Support Request", text);
        } catch (Exception e) {
            System.out.println("Support email failed: " + e.getMessage());
            throw new RuntimeException("Could not send support message. Please try again later.", e);
        }
    }
		
	}
	



	
		    
 
	
	
