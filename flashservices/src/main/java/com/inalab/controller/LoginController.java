package com.inalab.controller;

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

import com.inalab.dao.LoginDao;
import com.inalab.model.ApiResponse;
import com.inalab.model.Login;
import com.inalab.util.UserNotFoundException;
import com.inalab.util.JSONResponse;

@RestController
@RequestMapping(value = "/users")
public class LoginController {

	@Autowired
	private LoginDao loginDao;

	/**
	 * Create new login.
	 * 
	 * @param input
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	ResponseEntity<?> create(@RequestBody Login input) {

		Login login = this.loginDao.getByUserName(input.getUsername());
		if (login == null) {
			long id = this.loginDao.insert(input);
			login = this.loginDao.getById(id);
		}
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(
				ServletUriComponentsBuilder.fromCurrentRequest().path("/").buildAndExpand(login.getId()).toUri());
		ApiResponse response = JSONResponse.getResponse(login);
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.CREATED);

	}

	/**
	 * Update user login detail
	 * 
	 * @param input
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	ResponseEntity<?> update(@PathVariable int id, @RequestBody Login input) {

		this.validateUser(id);

		Login login = this.loginDao.getById(id);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(
				ServletUriComponentsBuilder.fromCurrentRequest().path("/").buildAndExpand(login.getId()).toUri());
		ApiResponse response = JSONResponse.getResponse(login);
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);

	}

	/**
	 * Get user by ID
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	ResponseEntity<?> getLogin(@PathVariable int id) {
		this.validateUser(id);
		Login login = this.loginDao.getById(id);
		HttpHeaders httpHeaders = new HttpHeaders();
		ApiResponse response = JSONResponse.getResponse(login);
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);

	}

	/**
	 * Get user by username
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/name/{userName}", method = RequestMethod.GET)
	ResponseEntity<?> getLoginByName(@PathVariable String userName) {
		this.validateUser(userName);
		Login login = this.loginDao.getByUserName(userName);
		HttpHeaders httpHeaders = new HttpHeaders();
		ApiResponse response = JSONResponse.getResponse(login);
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);

	}

	/**
	 * Validate user
	 * 
	 * @param id
	 */
	private void validateUser(int id) {
		Login login = this.loginDao.getById(id);

		if (login == null)
			throw new UserNotFoundException(Integer.valueOf(id).toString());
	}

	/**
	 * Validate user by name
	 * 
	 * @param userId
	 */
	private void validateUser(String userId) {
		Login login = this.loginDao.getByUserName(userId);

		if (login == null)
			throw new UserNotFoundException(userId);
	}
}
