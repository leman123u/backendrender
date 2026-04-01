package personalbudget.service;


import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import personalbudget.entity.UserEntity;
import personalbudget.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private JavaMailSender mailSender;
	
	  @Autowired 
	 private UserRepository userRepository;
	  @Autowired 
	  private  PasswordEncoder passwordEncoder;
	  
	  
	  @Value("${app.frontend.url}")
	    private String frontendUrl;
   
	@Override
	public UserEntity findByEmail(String email) {
		 return userRepository.findByEmail(email).orElse(null);
	}
	

	@Override
	public UserEntity save(UserEntity user) {
		
		 user.setPassword(
		            passwordEncoder.encode(user.getPassword())
		    );

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
	            .orElseThrow(() -> new RuntimeException("Invalid token"));

	    // 🔥 expiry check
	    if (user.getResetTokenExpiry() == null ||
	        user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {

	        throw new RuntimeException("Token expired");
	    }

	    user.setPassword(passwordEncoder.encode(newPassword));

	    // 🔥 token silinir
	    user.setResetToken(null);
	    user.setResetTokenExpiry(null);

	    userRepository.save(user);
	    
	    
	}
	@Override
	public void sendEmail(String to, String link) {
		 try {
		        SimpleMailMessage message = new SimpleMailMessage();

		        message.setTo(to);
		        message.setSubject("Reset Password");
		        message.setText("Click this link:\n" + link);

		        mailSender.send(message);

		    } catch (Exception e) {
		        System.out.println("Email failed to send: " + e.getMessage());
		    }
	}

	@Override
	public String createResetToken(String email) {
		 UserEntity user = userRepository.findByEmail(email)
		            .orElseThrow(() -> new RuntimeException("User not found"));

		    String token = UUID.randomUUID().toString();

		    user.setResetToken(token);

		    // 🔥 15 dəqiqəlik expiry
		    user.setResetTokenExpiry(LocalDateTime.now().plusMinutes(15));

		    userRepository.save(user);

		    String link = frontendUrl + "/reset-password?token=" + token;

		    System.out.println("RESET LINK: " + link);

		    sendEmail(user.getEmail(), link);

		    return token;
		
}

}
