package personalbudget.service;

import java.util.List;

import personalbudget.entity.BudgetEntity;
import personalbudget.entity.UserEntity;

public interface BudgetService {
	
	 List<BudgetEntity> findByUser(UserEntity user);

	 List<BudgetEntity> findByUserAndBudgetMonthAndBudgetYear(
	        UserEntity user,
	        Integer budgetMonth,
	        Integer budgetYear
	 );

	 List<BudgetEntity> findByUserAndCategory(UserEntity user, String category);
	    
     
}
