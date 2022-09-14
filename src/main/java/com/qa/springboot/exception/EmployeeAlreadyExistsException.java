package com.qa.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT,reason = "Employee Already Exists with this ID")
public class EmployeeAlreadyExistsException extends Exception {

}
