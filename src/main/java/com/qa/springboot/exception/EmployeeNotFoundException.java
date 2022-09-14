package com.qa.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.NOT_FOUND,reason="employee doesn't exist with this id")
public class EmployeeNotFoundException extends Exception {

}
