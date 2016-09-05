package com.inalab.dao.impl;

import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.inalab.dao.EmployeeDao;
import com.inalab.model.Employee;

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
	public int insert(Employee record) {
		String sql = DBQueries.getQuery("employee.insert");
		return super.insert(record, sql);
	}

	@Override
	public String addEmployeeToDept(int employeeId, int departmentId) {
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
	public List<Employee> findAllEmployeesByDepartment(int departmentId) {

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
	public Employee getEmployeeByDepartmentId(int employeeId, int departmentId) {
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
	public boolean updateDepartmentId(int employeeId, int departmentId) {
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
		// TODO Auto-generated method stub
		return false;
	}

}
