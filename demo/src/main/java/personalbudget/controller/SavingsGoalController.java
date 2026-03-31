package personalbudget.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import personalbudget.entity.SavingsGoalEntity;
import personalbudget.entity.UserEntity;
import personalbudget.service.SavingsGoalService;

@RestController
@RequestMapping("/api/savings_goals")
@CrossOrigin(origins = "*")
public class SavingsGoalController {

	
	   @Autowired 
	private  SavingsGoalService  service;
	   
	   
	   
	   
	   @PostMapping("/save")
	    public SavingsGoalEntity save(@RequestBody SavingsGoalEntity goal) {

	        return service.save(goal);
	   
}
	   
	   // ✅ GET GOALS BY USER
	    @PostMapping("/by-user")
	    public List<SavingsGoalEntity> getByUser(@RequestBody UserEntity user) {

	        return service.findByUser(user);
	    }

	    
	   // ✅ DELETE GOAL
	    @DeleteMapping("/delete")
	    public String delete(@RequestParam Long id) {

	        service.delete(id);

	        return "Goal deleted";
	    }
}
