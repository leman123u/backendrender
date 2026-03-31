package personalbudget.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import personalbudget.entity.BudgetEntity;
import personalbudget.entity.UserEntity;
import personalbudget.service.BudgetService;
import personalbudget.service.UserService;

@RestController
@RequestMapping("/api/budgets")
@CrossOrigin(origins = "*")
public class BudgetController {

	
	 @Autowired
	    private BudgetService budgetService;

	    @Autowired
	    private UserService userService;


	    // ✅ GET ALL BY USER
	    @GetMapping("/user")
	    public List<BudgetEntity> getByUser(
	            @RequestParam String email
	    ) {

	        UserEntity user = userService.findByEmail(email);

	        return budgetService.findByUser(user);
	    }


	    // ✅ GET BY MONTH + YEAR
	    @GetMapping("/month")
	    public List<BudgetEntity> getByMonthYear(
	            @RequestParam String email,
	            @RequestParam Integer month,
	            @RequestParam Integer year
	    ) {

	        UserEntity user = userService.findByEmail(email);

	        return budgetService.findByUserAndBudgetMonthAndBudgetYear(
	                user,
	                month,
	                year
	        );
	    }


	    // ✅ GET BY CATEGORY
	    @GetMapping("/category")
	    public List<BudgetEntity> getByCategory(
	            @RequestParam String email,
	            @RequestParam String category
	    ) {

	        UserEntity user = userService.findByEmail(email);

	        return budgetService.findByUserAndCategory(
	                user,
	                category
	        );
	    }

	
	
}
