package com.employee.example.employee_Database.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.example.employee_Database.exception.ResourceNotFoundException;
import com.employee.example.employee_Database.model.Employee;
import com.employee.example.employee_Database.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	//create an employee 
	
	@PostMapping("employees")
	public Employee createEmployee(@RequestBody Employee employee) {
		return this.employeeRepository.save(employee);
	}
	
	//Find all the Employees in Table
	
	@GetMapping("/employees")
	public List<Employee> getAllEmployees() {
		return this.employeeRepository.findAll();
	}
	
	
	//Get an Employee by Id
	
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
		return ResponseEntity.ok().body(employee);
	}
	
	//Delete an employee using id
	
	@DeleteMapping("/employees/{id}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
		throws ResourceNotFoundException {
	Employee employee = employeeRepository.findById(employeeId)
			.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

	employeeRepository.delete(employee);
	Map<String, Boolean> response = new HashMap<>();
	response.put("deleted", Boolean.TRUE);
		return response;
	}
	
	// Update an employee using his Id
	
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId,
			@Validated @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
			.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
		employee.setLastName(employeeDetails.getLastName());
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setEmail(employeeDetails.getEmail());
	final Employee updatedEmployee = employeeRepository.save(employee);
	return ResponseEntity.ok(updatedEmployee);
	}
	 
}
