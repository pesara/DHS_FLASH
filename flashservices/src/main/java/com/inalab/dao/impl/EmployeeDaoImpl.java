package com.inalab.dao.impl;

import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.inalab.dao.EmployeeDao;
import com.inalab.model.Employee;
import com.inalab.model.Kudos;

public class EmployeeDaoImpl extends CommonDaoImpl<Employee>implements EmployeeDao {

	private BeanPropertyRowMapper<Employee> employeeRowMapper = new BeanPropertyRowMapper<Employee>(Employee.class);

	private static final Logger LOG = LoggerFactory.getLogger(EmployeeDaoImpl.class);

	@Override
	public Employee findEmployee(String firstName, String lastName) {

		String getSql = DBQueries.getQuery("employee.getByName");

		Employee record = null;

		try {
			record = getJdbcTemplate().queryForObject(getSql, new Object[] { firstName, lastName }, employeeRowMapper);
		} catch (EmptyResultDataAccessException ex) {
			LOG.error("No Record found for " + firstName + " " + lastName);
		}

		return record;
	}

	@Override
	public List<Employee> getAllEmployees(String matchPattern) {

		List<Employee> recordList = null;

		String getSql = DBQueries.getQuery("employee.getAllEmployee");

		try {
			recordList = getJdbcTemplate().query(getSql,
					new Object[] { '%' + matchPattern + '%', '%' + matchPattern + '%' }, employeeRowMapper);
		} catch (EmptyResultDataAccessException ex) {
			LOG.error("No Record Found for " + matchPattern + " " + getSql);
		}

		return recordList;

	}

	@Override
	public long insert(Employee record) {
		String sql = DBQueries.getQuery("employee.insert");
		return super.insert(record, sql);
	}

	@Override
	public String addEmployeeToDept(long employeeId, long departmentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Employee> findAllEmployeesJoinedByDate(String date) {
		List<Employee> recordList = null;

		String getSql = DBQueries.getQuery("employee.findEmployeesByDate");

		try {
			recordList = getJdbcTemplate().query(getSql, new Object[] { Calendar.getInstance().getTime() },
					employeeRowMapper);
		} catch (EmptyResultDataAccessException ex) {
			LOG.error("No Record Found for " + date + " " + getSql);
		}

		return recordList;
	}

	@Override
	public List<Employee> findAllEmployeesByDepartment(long departmentId) {

		List<Employee> recordList = null;

		String getSql = DBQueries.getQuery("employee.findEmployeesByDepartmentId");

		try {
			recordList = getJdbcTemplate().query(getSql, new Object[] { departmentId }, employeeRowMapper);
		} catch (EmptyResultDataAccessException ex) {
			LOG.error("No Record Found for " + departmentId + " " + getSql);
		}

		return recordList;

	}

	@Override
	public Employee getEmployeeByDepartmentId(long employeeId, long departmentId) {
		String getSql = DBQueries.getQuery("employee.getEmployeeByDepartmentId");

		Employee record = null;

		try {
			record = getJdbcTemplate().queryForObject(getSql, new Object[] { employeeId, departmentId },
					employeeRowMapper);
		} catch (EmptyResultDataAccessException ex) {
			LOG.error("No Record found for " + employeeId + " " + departmentId);
		}

		return record;
	}

	@Override
	public boolean updateDepartmentId(long employeeId, long departmentId) {
		String getSql = DBQueries.getQuery("employee.addToDepartment");

		int retVal = -1;

		try {
			retVal = getJdbcTemplate().update(getSql, new Object[] { employeeId, departmentId });

		} catch (EmptyResultDataAccessException ex) {
			LOG.error("No Record found for " + employeeId + " " + departmentId);

		}

		if (retVal > 0) {

			if (LOG.isDebugEnabled())
				LOG.debug("Record updated ");

			return true;
		}

		return false;
	}

	

	@Override
	public boolean update(Employee record) {
		String sql = DBQueries.getQuery("employee.update");
		return super.update(record, sql);
	}

	@Override
	public Employee getById(long employeeId) {
		String sql = DBQueries.getQuery("employee.getById");
		return super.getById(employeeId, sql);
	}

	@Override
	public boolean updateActiveIndicator(long employeeId, String status) {
		String getSql = DBQueries.getQuery("employee.setActive");

		int retVal = -1;

		try {
			retVal = getJdbcTemplate().update(getSql, new Object[] { employeeId, status });

		} catch (EmptyResultDataAccessException ex) {
			LOG.error("No Record found for " + employeeId + " " + status);

		}

		if (retVal > 0) {

			if (LOG.isDebugEnabled())
				LOG.debug("Record updated ");

			return true;
		}

		return false;
	}

}
