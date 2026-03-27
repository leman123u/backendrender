package personalbudget.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import personalbudget.entity.BudgetEntity;
import personalbudget.entity.UserEntity;
import personalbudget.repository.BudgetRepository;

@Service
public class BudgetServiceImpl implements BudgetService  {
	
	@Autowired 
	private BudgetRepository budgetRepository;

	@Override
	public List<BudgetEntity> findByUser(UserEntity user) {
		
		return budgetRepository.findByUser(user);
	}

	@Override
	public List<BudgetEntity> findByUserAndBudgetMonthAndBudgetYear(UserEntity user, Integer budgetMonth,
			Integer budgetYear) {
		
		return budgetRepository.findByUserAndBudgetMonthAndBudgetYear(user, budgetMonth, budgetYear);
	}

	@Override
	public List<BudgetEntity> findByUserAndCategory(UserEntity user, String category) {
		
		return budgetRepository.findByUserAndCategory(user, category);
	}

	
	

}
