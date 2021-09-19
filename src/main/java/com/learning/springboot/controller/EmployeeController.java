package com.learning.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.springboot.exception.ResourceNotFoundException;
import com.learning.springboot.model.Employee;
import com.learning.springboot.repository.EmployeeRepository;

// Unblock localhost:3000 accesses http://localhost:8080/api/v1/employees
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {
	@Autowired
	private EmployeeRepository employeeRepository;
//	get all employees by Rest API
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
	}
//	create employee rest api
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);	
	}
//	Get employee by id
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		 Employee employee=employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("No employee exists with provided id: "+id));
		 return ResponseEntity.ok(employee);
	}
//	Update employee rest api
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployeeById(@PathVariable Long id,@RequestBody Employee employee){
		Employee empl=employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("No employee exists with provided id: "+id));
		empl.setFirstName(employee.getFirstName());
		empl.setLastName(employee.getLastName());
		empl.setEmailId(employee.getEmailId());
		Employee updatedEmpl=employeeRepository.save(empl);
		return ResponseEntity.ok(updatedEmpl);
	}
//	Delete employee rest api
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String,Boolean>> deleteEmployee(@PathVariable Long id) {
		Employee employee=employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("No employee exists with provided id: "+id));
		employeeRepository.delete(employee);
		Map<String,Boolean> result=new HashMap<>();
		result.put("Deleted", Boolean.TRUE);
		return ResponseEntity.ok(result);
	}
	}
