package personalbudget.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import personalbudget.entity.SavingsGoalEntity;
import personalbudget.entity.UserEntity;

public interface SavingsGoalRepository extends JpaRepository<SavingsGoalEntity, Long>{
	
	 List<SavingsGoalEntity> findByUser(UserEntity user);

}
