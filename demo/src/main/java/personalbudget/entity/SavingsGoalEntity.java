package personalbudget.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "savings_goals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SavingsGoalEntity {
	
	
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(name = "goal_name", nullable = false)
	    private String goalName;

	    @Column(name = "current_amount")
	    private BigDecimal currentAmount;

	    @Column(name = "target_amount")
	    private BigDecimal targetAmount;

	    @Column(name = "deadline")
	    private LocalDate deadline;

	    // relation with user
	    @ManyToOne
	    @JoinColumn(name = "user_id")
	    private UserEntity user;

	

}
