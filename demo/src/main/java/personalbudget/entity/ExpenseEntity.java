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
@Table(name = "expenses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseEntity {
	
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(name = "amount")
	    private BigDecimal amount;

	    @Column(name = "category")
	    private String category;

	    @Column(name = "date")
	    private LocalDate date;

	    @Column(name = "description")
	    private String description;

	    @ManyToOne
	    @JoinColumn(name = "user_id")
	    private UserEntity user;

}
