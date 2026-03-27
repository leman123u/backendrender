package personalbudget.service;

import java.util.Optional;
import java.util.UUID;

import personalbudget.entity.UserEntity;

public interface UserService {
	
	UserEntity findByEmail(String email);

    UserEntity save(UserEntity user);

    Optional<UserEntity> findByResetToken(UUID token);


    UUID createResetToken(String email);

    void resetPassword(UUID token, String newPassword);

    boolean existsByEmail(String email);
}
