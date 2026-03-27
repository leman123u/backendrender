package personalbudget.service;



import java.util.List;

import personalbudget.entity.SavingsGoalEntity;
import personalbudget.entity.UserEntity;

public interface SavingsGoalService {
	
	  SavingsGoalEntity save(SavingsGoalEntity goal);

	    List<SavingsGoalEntity> findByUser(UserEntity user);

	    void delete(Long id);


}
