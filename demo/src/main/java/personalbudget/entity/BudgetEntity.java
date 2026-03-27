package personalbudget.entity;

import java.math.BigDecimal;

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
@Table(name = "budgets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BudgetEntity {
	
	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(name = "category")
	    private String category;

	    @Column(name = "limit_amount")
	    private BigDecimal limitAmount;
	    
	    @Column(name = "budget_month")
	    private Integer budgetMonth;

	    @Column(name = "budget_year")
	    private Integer budgetYear;

	    @ManyToOne
	    @JoinColumn(name = "user_id")
	    private UserEntity user;

}
