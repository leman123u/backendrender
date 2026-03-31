package personalbudget.entity;

import java.time.LocalDateTime;


import jakarta.persistence.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "app_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
	
	
	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	 
	 @Column(name = "name", nullable = false)
	    private String name;

	    @Column(name = "email", nullable = false, unique = true)
	    private String email;

	    @Column(name = "password", nullable = false)
	    private String password;

	    @Column(name = "subscription_type")
	    private String subscriptionType;

	    @Column(name = "reset_token_new")
	    private String  resetToken;

	    @Column(name = "reset_token_expiry")
	    private LocalDateTime resetTokenExpiry;

	    @Column(name = "created_at")
	    private LocalDateTime createdAt;

	    @Column(name = "updated_at")
	    private LocalDateTime updatedAt;

}
