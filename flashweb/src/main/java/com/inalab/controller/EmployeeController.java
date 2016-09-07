package com.inalab.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.inalab.dao.EmployeeDao;
import com.inalab.model.ApiResponse;
import com.inalab.model.Employee;
import com.inalab.model.Login;
import com.inalab.util.JSONResponse;

@RestController
@RequestMapping(value = "/employee")
public class EmployeeController {

	@Autowired
	private EmployeeDao employeeDao;

	/**
	 * Get all employees based on matching pattern
	 * 
	 * @param input
	 * @return
	 */
	@RequestMapping(value = "/{matchPattern}", method = RequestMethod.GET)
	ResponseEntity<?> getAllEmployees(@PathVariable String matchPattern) {

		List<Employee> employeeList = this.employeeDao.getAllEmployees(matchPattern);
		HttpHeaders httpHeaders = new HttpHeaders();
		ApiResponse response = JSONResponse.getResponse(employeeList);
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);

	}

	/**
	 * get employee based on first and last name
	 * @param input
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	ResponseEntity<?> getEmployee(@RequestBody Employee input) {

		Employee employee = this.employeeDao.findEmployee(input.getFirstname(), input.getLastname());
		HttpHeaders httpHeaders = new HttpHeaders();
		ApiResponse response = JSONResponse.getResponse(employee);
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);

	}

}