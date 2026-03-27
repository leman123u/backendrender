package personalbudget.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import personalbudget.entity.NotificationEntity;
import personalbudget.entity.UserEntity;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long>{

	
	
	List<NotificationEntity> findByUser(UserEntity user);

    List<NotificationEntity> findByUserAndIsRead(UserEntity user, Boolean isRead);
}
