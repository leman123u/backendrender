package personalbudget.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import personalbudget.entity.ExpenseEntity;
import personalbudget.entity.UserEntity;
import personalbudget.service.ExpenseService;
import personalbudget.service.UserService;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin
public class ExpenseController {
	
	
	 @Autowired
	    private ExpenseService expenseService;

	    @Autowired
	    private UserService userService;


	    // ✅ CREATE EXPENSE
	    @PostMapping("/create")
	    public ExpenseEntity createExpense(
	            @RequestParam String email,
	            @RequestBody ExpenseEntity expense
	    ) {

	        UserEntity user = userService.findByEmail(email);

	        expense.setUser(user);

	        return expenseService.save(expense);
	    }


	    // ✅ GET USER EXPENSES
	    @GetMapping("/user")
	    public List<ExpenseEntity> getUserExpenses(
	            @RequestParam String email
	    ) {

	        UserEntity user = userService.findByEmail(email);

	        return expenseService.findByUser(user);
	    }


	    // ✅ DELETE EXPENSE
	    @DeleteMapping("/delete")
	    public String deleteExpense(
	            @RequestParam Long id
	    ) {

	        expenseService.delete(id);

	        return "Expense deleted";
	    }

}
