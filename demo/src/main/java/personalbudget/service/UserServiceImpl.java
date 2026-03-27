package personalbudget.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

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

		    user.setResetToken(token);
		    user.setResetTokenExpiry(LocalDateTime.now().plusHours(1));

		    userRepository.save(user);

		    String link = frontendUrl + "/reset-password?token=" + token;

		    System.out.println("RESET LINK = " + link);

		    return token;	}

	@Override
	public void resetPassword(UUID token, String newPassword) {

	    UserEntity user = userRepository.findByResetToken(token)
	            .orElseThrow(() -> new RuntimeException("Invalid token"));

	    if (user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
	        throw new RuntimeException("Token expired");
	    }

	    user.setPassword(
	            passwordEncoder.encode(newPassword)
	    );

	    user.setResetToken(null);
	    user.setResetTokenExpiry(null);

	    userRepository.save(user);
		
	}

	@Override
	public boolean existsByEmail(String email) {
		 return userRepository.existsByEmail(email);
	}

}
