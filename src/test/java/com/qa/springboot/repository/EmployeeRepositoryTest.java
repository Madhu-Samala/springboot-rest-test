package com.qa.springboot.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.qa.springboot.entity.Employee;
//This annotation extends Spring Functionality to JUnit
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class EmployeeRepositoryTest {
	
	@Autowired
	EmployeeRepository empRepository;

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
		empRepository.deleteAll();
		empList = null;
		
	}
	
	@Test
	@DisplayName("save-employee-test")
	
	public void given_Employee_To_Save_Should_Return_The_Saved_Employee() {
		
		Employee savedEmployee = empRepository.save(emp1);
		assertNotNull(savedEmployee);
		assertEquals("emp1", savedEmployee.getName());
	}
	
	@Test
	
	@DisplayName("get-employee-list-test")
	public void given_GetAllEmployees_Should_Return_Employee_List() {
		empRepository.save(emp1);
		empRepository.save(emp2);
		empRepository.save(emp3);
		
		List<Employee> empList = empRepository.findAll();
		assertEquals(3, empList.size(),"employee list size should be 3");
		assertEquals("emp1",empList.get(0).getName());
	}
	
	@Test
	@DisplayName("get-employee-non-existing-id-test")
	//@Disabled
	public void given_Non_Existing_Id_Should_Return_Optional_Empty() {
		empRepository.save(emp1);
		assertThat(empRepository.findById(2)).isEmpty();
	}
}
