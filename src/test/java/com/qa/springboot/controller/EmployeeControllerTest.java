package com.qa.springboot.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.springboot.entity.Employee;
import com.qa.springboot.service.EmployeeService;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {
	
	@Mock
	private EmployeeService empService;
	
	@Autowired
	@InjectMocks
	private EmployeeController empController;
	
	@Autowired
	MockMvc mockMvc;
	
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
		
		mockMvc = MockMvcBuilders.standaloneSetup(empController).build();
	}
	
	@AfterEach
	public void tearDown() {
		emp1 = emp2 = emp3 = null;
		empList = null;
		
	}
	
	@Test
	@DisplayName("save-employee-test")
	public void given_Employee_To_Save_Employee_Should_Return_Employee_As_JSON_With_Status_Created() throws Exception {
		when(empService.saveEmployee(any())).thenReturn(emp1);
		mockMvc.perform(post("/api/v1/employee-service/employee")
				        .contentType(MediaType.APPLICATION_JSON)
				        .content(asJsonString(emp1)))
		        .andExpect(status().isCreated())
		        .andExpect(jsonPath("$.name").value("emp1"));
	}
	
	@Test
	@DisplayName("get-employee-test")
	public void given_GetAllEmployees_Should_Return_List() throws Exception {
		when(empService.getAllEmployees()).thenReturn(empList);
		mockMvc.perform(get("/api/v1/employee-service/employee")
				        .accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[1].name").value("emp2"));
	}

	
	public static String asJsonString(Object obj) {
		ObjectMapper Obj = new ObjectMapper();
		String jsonStr = null;
		 
        // Try block to check for exceptions
        try {
 
            // Getting organisation object as a json string
            jsonStr = Obj.writeValueAsString(obj);
 
            // Displaying JSON String on console
            System.out.println(jsonStr);
        }
 
        // Catch block to handle exceptions
        catch (IOException e) {
 
            // Display exception along with line number
            // using printStackTrace() method
            e.printStackTrace();
        }
        return jsonStr;
	}

}
