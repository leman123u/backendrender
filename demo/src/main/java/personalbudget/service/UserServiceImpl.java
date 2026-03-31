package personalbudget.service;

import java.time.LocalDateTime;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import personalbudget.entity.UserEntity;
import personalbudget.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	  @Autowired 
	 private UserRepository userRepository;
	  @Autowired 
	  private  PasswordEncoder passwordEncoder;

	@Autowired
	private JavaMailSender mailSender;
	  
	  
	  @Value("${app.frontend.url}")
	    private String frontendUrl;
   
	@Override
	public UserEntity findByEmail(String email) {
				return userRepository.findByEmail(email)
				        .orElseThrow(() -> new RuntimeException("User not found"));
	}

	@Override
	public UserEntity save(UserEntity user) {
		
		 user.setPassword(
		            passwordEncoder.encode(user.getPassword())
		    );

		    return userRepository.save(user);
	}

	@Override
	public Optional<UserEntity> findByResetToken(UUID token) {
		
		return userRepository.findByResetToken(token);
	}

	@Override
  public UUID createResetToken(String email) {
		 UserEntity user = userRepository.findByEmail(email)
		            .orElseThrow(() -> new RuntimeException("User not found"));

		    UUID token = UUID.randomUUID();

		    user.setResetToken(token.toString());
		    user.setResetTokenExpiry(LocalDateTime.now().plusHours(1));

		    userRepository.save(user);
		    String link = frontendUrl + "/reset-password?token=" + token;

		    try {
		        sendEmail(user.getEmail(), link);
		    } catch (Exception e) {
		        System.out.println("EMAIL ERROR: " + e.getMessage());
		    }

		    System.out.println("RESET LINK = " + link);

		    return token;}

	@Override
	public void resetPassword(UUID token, String newPassword) {

	    UserEntity user = userRepository.findByResetToken(token)
		            .orElseThrow(() -> new RuntimeException("Invalid or expired token"));

		    // 🔐 yeni password encode olunur
		    user.setPassword(passwordEncoder.encode(newPassword));

		    // ♻️ token silinir (təkrar istifadə olunmasın)
		    user.setResetToken(null);

		    userRepository.save(user);
		
	}

	@Override
	public boolean existsByEmail(String email) {
		 return userRepository.existsByEmail(email);
	}


	

}
