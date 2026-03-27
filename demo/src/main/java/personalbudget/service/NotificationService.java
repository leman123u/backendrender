package personalbudget.service;

import java.util.List;

import personalbudget.entity.NotificationEntity;
import personalbudget.entity.UserEntity;

public interface NotificationService {
	
	
	
	 NotificationEntity save(NotificationEntity notification);

	    List<NotificationEntity> findByUser(UserEntity user);

	    void markAsRead(Long id);

	    void delete(Long id);

}
