package personalbudget.service;

import java.util.List;

import personalbudget.entity.ExpenseEntity;
import personalbudget.entity.UserEntity;

public interface ExpenseService {
	
	 ExpenseEntity save(ExpenseEntity expense);

	    List<ExpenseEntity> findByUser(UserEntity user);

	    void delete(Long id);

}
