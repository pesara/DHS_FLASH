package com.inalab.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.inalab.dao.EmployeeDao;
import com.inalab.model.Employee;


public class EmployeeDaoImpl extends CommonDaoImpl<Employee> implements EmployeeDao {

	private BeanPropertyRowMapper<Employee> employeeRowMapper = new BeanPropertyRowMapper<Employee>(Employee.class);

	private static final Logger LOG = LoggerFactory.getLogger(EmployeeDaoImpl.class);
	
	@Override
	public Employee findEmployee(String firstName, String lastName) {
		
		String getSql = DBQueries.getQuery("employee.getByName");
		
		Employee record = null;
		
		try {
			record = getJdbcTemplate().queryForObject(getSql, new Object[] { firstName, lastName }, employeeRowMapper);
		}
		catch(EmptyResultDataAccessException ex) {
			LOG.error("No Record found for " + firstName + " " + lastName);
		}
		
		return record;
	}

	@Override
	public List<Employee> getAllEmployees(String matchPattern) {
		
		List<Employee> recordList = null;

		String getSql = DBQueries.getQuery("employee.getAllEmployee");
		
		try {
			recordList = getJdbcTemplate().query(getSql, new Object[] { '%'+matchPattern +'%', '%'+matchPattern +'%'}, employeeRowMapper);
		} catch (EmptyResultDataAccessException ex) {
			LOG.error("No Record Found for " + matchPattern + " " + getSql);
		}

		return recordList;
		
	}

	@Override
	public String addNewEmployee(String firstName, String lastName, String username, String password, String emailId,
			String departmentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addEmployeeToDept(String username, String department) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String giveKudos(String fromEmployee, String toEmployee) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Employee> findAllEmployeesJoinedByDate(String date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Employee> findWhoGotKudosToday() {
		// TODO Auto-generated method stub
		return null;
	}

}
