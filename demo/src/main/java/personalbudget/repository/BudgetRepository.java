package personalbudget.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import personalbudget.entity.BudgetEntity;
import personalbudget.entity.UserEntity;

public interface BudgetRepository extends JpaRepository<BudgetEntity, Long>{

	
	 List<BudgetEntity> findByUser(UserEntity user);

	 List<BudgetEntity> findByUserAndBudgetMonthAndBudgetYear(
		        UserEntity user,
		        Integer budgetMonth,
		        Integer budgetYear
		);

	    List<BudgetEntity> findByUserAndCategory(UserEntity user, String category);
}
