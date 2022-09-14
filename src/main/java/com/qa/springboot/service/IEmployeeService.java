package com.qa.springboot.service;

import java.util.List;

import com.qa.springboot.entity.Employee;
import com.qa.springboot.exception.EmployeeAlreadyExistsException;
import com.qa.springboot.exception.EmployeeNotFoundException;

public interface IEmployeeService {
	
	public Employee saveEmployee(Employee employee) throws EmployeeAlreadyExistsException;
	public List<Employee> getAllEmployees();
	public Employee getEmployeeById(int id) throws EmployeeNotFoundException;
	public Employee updateEmployee(Employee employee) throws EmployeeNotFoundException;
	public boolean deleteEmployee(Integer id) throws EmployeeNotFoundException;
}
