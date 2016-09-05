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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.inalab.dao.EmployeeDao;
import com.inalab.model.ApiResponse;
import com.inalab.model.Employee;
import com.inalab.util.JSONResponse;

@RestController
@RequestMapping(value = "/employees")
public class EmployeeController {

	@Autowired
	private EmployeeDao employeeDao;

	/**
	 * Get all employees based on matching pattern
	 * 
	 * @param input
	 * @return
	 */
	@RequestMapping(value = "/matchPattern/{matchPattern}", method = RequestMethod.GET)
	ResponseEntity<?> getAllEmployees(@PathVariable String matchPattern) {

		List<Employee> employeeList = this.employeeDao.getAllEmployees(matchPattern);
		HttpHeaders httpHeaders = new HttpHeaders();
		ApiResponse response = JSONResponse.getResponse(employeeList);
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);

	}

	/**
	 * Get employee by joining date
	 * @param date
	 * @return
	 */
	@RequestMapping(value = "/date/{date}", method = RequestMethod.GET)
	ResponseEntity<?> getAllEmployeesByJoinDate(@PathVariable String date) {

		List<Employee> employeeList = this.employeeDao.findAllEmployeesJoinedByDate(date);
		HttpHeaders httpHeaders = new HttpHeaders();
		ApiResponse response = JSONResponse.getResponse(employeeList);
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);

	}
	
	/**
	 * get employees by department Id
	 * @param departmentId
	 * @return
	 */
	@RequestMapping(value = "/department/{departmentId}", method = RequestMethod.GET)
	ResponseEntity<?> getAllEmployeesByDepartment(@PathVariable int departmentId) {

		List<Employee> employeeList = this.employeeDao.findAllEmployeesByDepartment(departmentId);
		HttpHeaders httpHeaders = new HttpHeaders();
		ApiResponse response = JSONResponse.getResponse(employeeList);
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);

	}
	
	/**
	 * Get employee by department Id
	 * @param input
	 * @return
	 */
	@RequestMapping(value = "/department", method = RequestMethod.POST, consumes = "application/json")
	ResponseEntity<?> getEmployeeByDepartmentId(@RequestBody Employee input) {

		Employee employee = this.employeeDao.getEmployeeByDepartmentId(input.getId().intValue(),
				input.getDepartmentid().intValue());
		HttpHeaders httpHeaders = new HttpHeaders();
		ApiResponse response = JSONResponse.getResponse(employee);
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);

	}
	
	
	/**
	 * get employee based on first and last name
	 * @param input
	 * @return
	 */
	@RequestMapping(value = "/name",method = RequestMethod.POST, consumes = "application/json")
	ResponseEntity<?> getEmployeeByFirstAndLastName(@RequestBody Employee input) {

		Employee employee = this.employeeDao.findEmployee(input.getFirstname(), input.getLastname());
		HttpHeaders httpHeaders = new HttpHeaders();
		ApiResponse response = JSONResponse.getResponse(employee);
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);

	}

	/**
	 * Create new employee record
	 * @param input
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	ResponseEntity<?> create(@RequestBody Employee input) {

		Employee employee = this.employeeDao.findEmployee(input.getFirstname(), input.getLastname());
		
		long id = -1;
		if (employee == null) {
			
			id = this.employeeDao.insert(input);
			input.setId(id);
		} else {
			throw new RuntimeException("Employee already exist.");
		}

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(
				ServletUriComponentsBuilder.fromCurrentRequest().path("/").buildAndExpand(input.getId()).toUri());
		ApiResponse response = JSONResponse.getResponse(input);
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.CREATED);

	}

	/**
	 * Update employee record
	 * @param id
	 * @param input
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	ResponseEntity<?> update(@PathVariable int id, @RequestBody Employee input) {

		Employee employee = getEmployee(input);

		if (employee == null)
			throw new RuntimeException("Employee doesn't exist.");

		
		this.employeeDao.update(input);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(
				ServletUriComponentsBuilder.fromCurrentRequest().path("/").buildAndExpand(input.getId()).toUri());
		ApiResponse response = JSONResponse.getResponse(input);
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);

	}


	
	
	private Employee getEmployee(Employee input) {
		Employee employee = this.employeeDao.getById(input.getId().intValue());

		return employee;

	}
	
}