package personalbudget.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import personalbudget.entity.NotificationEntity;
import personalbudget.entity.UserEntity;
import personalbudget.service.NotificationService;
import personalbudget.service.UserService;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {
	
	
	@Autowired
	private NotificationService service;
	@Autowired 
     private  UserService userService;
	
	
	
	  // ✅ CREATE NOTIFICATION
    @PostMapping("/create")
    public NotificationEntity createNotification(
            @RequestParam String email,
            @RequestBody NotificationEntity notification
    ) {

        UserEntity user = userService.findByEmail(email);

        notification.setUser(user);
        notification.setIsRead(false);

        return service.save(notification);
    }


    // ✅ GET USER NOTIFICATIONS
    @GetMapping("/user")
    public List<NotificationEntity> getUserNotifications(
            @RequestParam String email
    ) {

        UserEntity user = userService.findByEmail(email);

        return service.findByUser(user);
    }


    // ✅ MARK AS READ
    @PutMapping("/read")
    public String markAsRead(
            @RequestParam Long id
    ) {

        service.markAsRead(id);

        return "Notification marked as read";
    }


    // ✅ DELETE
    @DeleteMapping("/delete")
    public String deleteNotification(
            @RequestParam Long id
    ) {

        service.delete(id);

        return "Notification deleted";
    }

}
