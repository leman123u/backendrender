package personalbudget.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import personalbudget.entity.NotificationEntity;
import personalbudget.entity.UserEntity;
import personalbudget.repository.NotificationRepository;

@Service
public class NotificationServiceImpl  implements NotificationService{

	 @Autowired 
	  private NotificationRepository notficationrepository;
	   
	@Override
	public NotificationEntity save(NotificationEntity notification) {
	
		return notficationrepository.save(notification);
	}

	@Override
	public List<NotificationEntity> findByUser(UserEntity user) {
		
		return notficationrepository.findByUser(user);
	}

	@Override
	public void markAsRead(Long id) {
		NotificationEntity notification =
				notficationrepository .findById(id).orElseThrow();

        notification.setIsRead(true);

        notficationrepository.save(notification);
		
	}

	@Override
	public void delete(Long id) {
		notficationrepository.deleteById(id);
		
	}

}
