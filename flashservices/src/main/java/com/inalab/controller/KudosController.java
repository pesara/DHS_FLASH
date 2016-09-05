package com.inalab.controller;

import java.math.BigDecimal;
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

import com.inalab.dao.KudosDao;
import com.inalab.model.ApiResponse;
import com.inalab.model.Kudos;
import com.inalab.model.Login;
import com.inalab.util.JSONResponse;
import com.inalab.util.KudosNotFoundException;

@RestController
@RequestMapping(value = "/kudos")
public class KudosController {

	@Autowired
	private KudosDao kudosDao;

	/**
	 * Get Kudos from employee
	 * 
	 * @param input
	 * @return
	 */
	@RequestMapping(value = "/from/{fromId}", method = RequestMethod.GET)
	ResponseEntity<?> getKudosFromEmployee(@PathVariable int fromId) {

		List<Kudos> kudosList = this.kudosDao.getByFromEmployee(fromId);
		HttpHeaders httpHeaders = new HttpHeaders();
		ApiResponse response = JSONResponse.getResponse(kudosList);
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);

	}

	/**
	 * Get Kudos to employee
	 * @param fromId
	 * @return
	 */
	@RequestMapping(value = "/to/{toId}", method = RequestMethod.GET)
	ResponseEntity<?> getKudosToEmployee(@PathVariable int fromId) {

		List<Kudos> kudosList = this.kudosDao.getByToEmployee(fromId);
		HttpHeaders httpHeaders = new HttpHeaders();
		ApiResponse response = JSONResponse.getResponse(kudosList);
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);

	}

	/**
	 * set Kudos from - to employee
	 * @param input
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	ResponseEntity<?> create(@RequestBody Kudos input) {

		Kudos kudos = this.kudosDao.getFromEmployeeToEmployee(input.getFromEid().intValue(),
				input.getToEid().intValue());
		long id = -1;
		if (kudos == null) {
			input.setCount(new BigDecimal(1)); // default
			id = this.kudosDao.insert(input);
			input.setId(id);
		} else {
			// Already exist so updating
			int count = kudos.getCount().intValue();
			input.setCount(new BigDecimal(count + 1));
			this.kudosDao.update(input);
		}

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(
				ServletUriComponentsBuilder.fromCurrentRequest().path("/").buildAndExpand(input.getId()).toUri());
		ApiResponse response = JSONResponse.getResponse(input);
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.CREATED);

	}

	/**
	 * Update kudos
	 * @param id
	 * @param input
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	ResponseEntity<?> update(@PathVariable int id, @RequestBody Kudos input) {

		Kudos kudos = getKudos(input);

		if (kudos == null)
			throw new KudosNotFoundException();

		int count = kudos.getCount().intValue();
		input.setCount(new BigDecimal(count + 1));
		this.kudosDao.update(input);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(
				ServletUriComponentsBuilder.fromCurrentRequest().path("/").buildAndExpand(input.getId()).toUri());
		ApiResponse response = JSONResponse.getResponse(input);
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);

	}

	private Kudos getKudos(Kudos input) {
		Kudos kudos = this.kudosDao.getFromEmployeeToEmployee(input.getFromEid().intValue(),
				input.getToEid().intValue());

		return kudos;

	}

}