package com.qa.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.springboot.entity.Employee;
import com.qa.springboot.exception.EmployeeAlreadyExistsException;
import com.qa.springboot.exception.EmployeeNotFoundException;
import com.qa.springboot.service.EmployeeService;

@RestController
@RequestMapping("api/v1/employee-service")
public class EmployeeController {
	
	@Autowired
	private EmployeeService empService;
	/*
	//CRUD operations
	 * 
	 * create -> api/v1/employee-management/employee --> HttpReqeust -> POST
	 * read all employees -> api/v1/employee-management/employee -> HttpRequest - GET
	 * read one employee -> api/v1/employee-management/employee/{id} -> HttpRequest - GET
	 * update employee -> api/v1/employee-management/employee -> HttpREqeust - PUT / PATCH
	 * delete employee -> api/v1/employee-management/employee/{id} -> HttpRequest - DELETE
	 * 
	 */
	
	@PostMapping("employee")
	public ResponseEntity<?> saveEmployee(@RequestBody Employee employee) throws EmployeeAlreadyExistsException{
		ResponseEntity<?> responseEntity = null;
		
		try {
			Employee createdEmployee = this.empService.saveEmployee(employee);
			responseEntity = new ResponseEntity<>(createdEmployee,HttpStatus.CREATED);
		} catch (EmployeeAlreadyExistsException e) {
			throw e;
		} catch(Exception e) {
			System.out.println(e);
			responseEntity = new ResponseEntity<>("some internal error occured. Please try again!!!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;
	}
	
	@GetMapping("employee")
	public ResponseEntity<?> getAllEmployees(){
		ResponseEntity<?>  responseEntity;
		try {
		List<Employee> empList = this.empService.getAllEmployees();
		responseEntity = new ResponseEntity<>(empList,HttpStatus.OK);
		} catch(Exception e) {
			responseEntity = new ResponseEntity<>("some internal error occured. Please try again!!!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
	
	
	@GetMapping("employee/{id}")
	public ResponseEntity<?> getEmployeeById(@PathVariable("id") int id ) throws EmployeeNotFoundException{
		ResponseEntity<?> responseEntity = null;
		try {
			responseEntity = new ResponseEntity<>(this.empService.getEmployeeById(id),HttpStatus.OK);
		} catch (EmployeeNotFoundException e) {
			throw e;
		} catch(Exception e) {
			responseEntity = new ResponseEntity<>("some internal error occured. Please try again!!!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;
	}
	
	@PutMapping("employee")
	public ResponseEntity<?> updateEmployee(@RequestBody Employee employee) throws EmployeeNotFoundException{
		ResponseEntity<?> responseEntity = null;
		
		try {
			Employee createdEmployee = this.empService.updateEmployee(employee);
			responseEntity = new ResponseEntity<>(createdEmployee,HttpStatus.CREATED);
		} catch (EmployeeNotFoundException e) {
			throw e;
		} catch(Exception e) {
			responseEntity = new ResponseEntity<>("some internal error occured. Please try again!!!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;
	}
	
	@DeleteMapping("employee/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable("id") int id) throws EmployeeNotFoundException{
		ResponseEntity<?> responseEntity = null;
		try {
			responseEntity = new ResponseEntity<>(this.empService.deleteEmployee(id),HttpStatus.OK);
		} catch (EmployeeNotFoundException e) {
			throw e;
		} catch(Exception e) {
			responseEntity = new ResponseEntity<>("some internal error occured. Please try again!!!", HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
		return responseEntity;
	}

}
