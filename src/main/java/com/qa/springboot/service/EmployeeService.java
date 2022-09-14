package com.qa.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.springboot.entity.Employee;
import com.qa.springboot.exception.EmployeeAlreadyExistsException;
import com.qa.springboot.exception.EmployeeNotFoundException;
import com.qa.springboot.repository.EmployeeRepository;

@Service
public class EmployeeService implements IEmployeeService {
	
	@Autowired
	private EmployeeRepository empRepository;

	@Override
	public Employee saveEmployee(Employee employee) throws EmployeeAlreadyExistsException {
		
		Employee createdEmployee = null;
		/*
		 * 1. Check whether employee already exists
		 * 2. If yes, throw EmployeeAlreadyExistsException
		 * 3. If no, save employee object to database
		 * 4. Return the created employee object
		 */
		
		Employee employeeByName = this.empRepository.findByName(employee.getName());
		if(employeeByName != null) {
			throw new EmployeeAlreadyExistsException();
		} else {
		createdEmployee = this.empRepository.save(employee);
		}
		
		return createdEmployee;
	}

	@Override
	public List<Employee> getAllEmployees() {
		
		return this.empRepository.findAll();
	}

	@Override
	public Employee getEmployeeById(int id) throws EmployeeNotFoundException {
		Employee employee = null;
		
			Optional<Employee> findById = this.empRepository.findById(id);
			if(findById.isPresent()) {
				employee = findById.get();
			} else {
				throw new EmployeeNotFoundException();
			}
		
			
	
		return employee;
	}

	@Override
	public Employee updateEmployee(Employee employee) throws EmployeeNotFoundException {
		Employee updatedEmployee = null;
		
		Optional<Employee> findById = this.empRepository.findById(employee.getId());
		if(findById.isPresent()) {
			updatedEmployee = this.empRepository.save(employee);
		} else {
			throw new EmployeeNotFoundException();
		}
	
		

	return updatedEmployee;
	}

	@Override
	public boolean deleteEmployee(Integer id) throws EmployeeNotFoundException {
		boolean status = false;
		Optional<Employee> findById = this.empRepository.findById(id);
		if(findById.isPresent()) {
		this.empRepository.deleteById(id);
		status = true;
		} else {
			throw new EmployeeNotFoundException();
		}
		return status;
	}

}
