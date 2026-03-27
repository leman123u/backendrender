package personalbudget.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import personalbudget.entity.ExpenseEntity;
import personalbudget.entity.UserEntity;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long>{
             
	
	List<ExpenseEntity> findByUser(UserEntity user);

    List<ExpenseEntity> findByUserAndCategory(UserEntity user, String category);
}
