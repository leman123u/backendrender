package personalbudget.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import personalbudget.entity.ExpenseEntity;
import personalbudget.entity.UserEntity;
import personalbudget.repository.ExpenseRepository;
@Service
public class ExpenseServiceImpl implements ExpenseService{
	
	@Autowired
	 private ExpenseRepository expenseRepository;

	@Override
	public ExpenseEntity save(ExpenseEntity expense) {
		
		return expenseRepository.save(expense);
	}

	@Override
	public List<ExpenseEntity> findByUser(UserEntity user) {
		return expenseRepository.findByUser(user);
	}

	@Override
	public void delete(Long id) {
		expenseRepository.deleteById(id);
		
	}

}
