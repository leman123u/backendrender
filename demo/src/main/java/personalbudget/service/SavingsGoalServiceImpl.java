package personalbudget.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import personalbudget.entity.SavingsGoalEntity;
import personalbudget.entity.UserEntity;
import personalbudget.repository.SavingsGoalRepository;
@Service
public class SavingsGoalServiceImpl implements SavingsGoalService {
          
	    @Autowired 
	    private SavingsGoalRepository  savingrepository;
	
	@Override
	public SavingsGoalEntity save(SavingsGoalEntity goal) {
		
		return savingrepository.save(goal);
	}

	@Override
	public List<SavingsGoalEntity> findByUser(UserEntity user) {
		
		return savingrepository.findByUser(user);
	}

	@Override
	public void delete(Long id) {
		
		savingrepository.deleteById(id);
	}
	
	
	

}
