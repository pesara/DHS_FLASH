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

import com.inalab.dao.DepartmentDao;
import com.inalab.model.ApiResponse;
import com.inalab.model.Department;
import com.inalab.util.JSONResponse;

@RestController
@RequestMapping(value = "/departments")
public class DepartmentController {

	@Autowired
	private DepartmentDao departmentDao;

	/**
	 * Get all departments
	 * 
	 * @param input
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	ResponseEntity<?> getDepartmentList() {

		List<Department> departmentList = this.departmentDao.getDepartmentList();
		HttpHeaders httpHeaders = new HttpHeaders();
		ApiResponse response = JSONResponse.getResponse(departmentList);
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);

	}

	/**
	 * Get department by Id
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	ResponseEntity<?> getById(@PathVariable int id) {

		Department department = this.departmentDao.getById(id);
		HttpHeaders httpHeaders = new HttpHeaders();
		ApiResponse response = JSONResponse.getResponse(department);
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);

	}
	
	/**
	 * Get department by name
	 * @param departmentName
	 * @return
	 */
	@RequestMapping(value = "/name/{departmentName}", method = RequestMethod.GET)
	ResponseEntity<?> getByName(@PathVariable String departmentName) {

		Department department = this.departmentDao.getByName(departmentName);
		HttpHeaders httpHeaders = new HttpHeaders();
		ApiResponse response = JSONResponse.getResponse(department);
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);

	}
	

	/**
	 * Create department
	 * @param input
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	ResponseEntity<?> create(@RequestBody Department input) {

		Department department = this.departmentDao.getByName(input.getDepartmentname());
		
		long id = -1;
		if (department == null) {
			
			id = this.departmentDao.insert(input);
			input.setId(id);
		} else {
			throw new RuntimeException("Department already exist.");
		}

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(
				ServletUriComponentsBuilder.fromCurrentRequest().path("/").buildAndExpand(input.getId()).toUri());
		ApiResponse response = JSONResponse.getResponse(input);
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.CREATED);

	}

	/**
	 * Update department
	 * @param id
	 * @param input
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	ResponseEntity<?> update(@PathVariable int id, @RequestBody Department input) {

		Department department = getDepartment(input);

		if (department == null)
			throw new RuntimeException("Department doens't exist.");

		
		this.departmentDao.update(input);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(
				ServletUriComponentsBuilder.fromCurrentRequest().path("/").buildAndExpand(input.getId()).toUri());
		ApiResponse response = JSONResponse.getResponse(input);
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);

	}

	private Department getDepartment(Department input) {
		Department department = this.departmentDao.getByName(input.getDepartmentname());

		return department;

	}
}