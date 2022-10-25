package com.qa.springboot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.qa.springboot.entity.Employee;
import com.qa.springboot.exception.EmployeeAlreadyExistsException;
import com.qa.springboot.exception.EmployeeNotFoundException;
import com.qa.springboot.repository.EmployeeRepository;
//Extend the Mockito Support to JUnit
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
	
	@Mock //creates the Mock Object
	private EmployeeRepository empRepository;
	
	@Autowired
	@InjectMocks
	private EmployeeService empService;
	
	Employee emp1;
	Employee emp2;
	Employee emp3;
	
	List<Employee> empList;
	
	@BeforeEach
	public void setUp() {
		/*
		 * Create the necessary instances
		 * Create dummy data
		 */
		emp1 = new Employee(1,"emp1","emp1@gmail.com","34523432");
		emp2 = new Employee(2,"emp2","emp2@gmail.com","45645645");
		emp3 = new Employee(3,"emp3","emp3@gmail.com","55645645");
		empList = Arrays.asList(emp1,emp2,emp3);
	}
	
	@AfterEach
	public void tearDown() {
		emp1 = emp2 = emp3 = null;
		empList = null;
		
	}
	
	@Test
	@DisplayName("save-employee-test")
	
	public void given_Employee_To_Save_Should_Return_The_Saved_Employee() throws EmployeeAlreadyExistsException {
		when(empRepository.findByName(any())).thenReturn(null);
		when(empRepository.save(any())).thenReturn(emp1);
		Employee savedEmployee = empService.saveEmployee(emp1);
		assertNotNull(savedEmployee);
		assertEquals(1,savedEmployee.getId());
		verify(empRepository,times(1)).findByName(any());
		verify(empRepository,times(1)).save(any());
	}
	
	@Test
	@DisplayName("save-employee-throws-exception-test")
	
	public void given_Existing_Employee_To_Save_Method_Should_Throw_Exception() throws EmployeeAlreadyExistsException {
		when(empRepository.findByName(any())).thenReturn(emp1);
		
		//empService.saveEmployee(emp1);
		assertThrows(EmployeeAlreadyExistsException.class,()-> empService.saveEmployee(emp1));
	}
	


}
