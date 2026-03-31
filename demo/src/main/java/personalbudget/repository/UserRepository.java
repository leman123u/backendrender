package personalbudget.repository;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import personalbudget.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>{
	 Optional<UserEntity> findByEmail(String email);

    
    boolean existsByEmail(String email);
    
    Optional<UserEntity> findByResetToken(String token);
	

}
