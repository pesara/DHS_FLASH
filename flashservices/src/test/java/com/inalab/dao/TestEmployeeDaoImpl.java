package com.inalab.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.inalab.DBHelper;
import com.inalab.TestBase;
import com.inalab.model.Department;
import com.inalab.model.Employee;
import com.inalab.model.Login;

public class TestEmployeeDaoImpl extends TestBase {

	private static final Logger LOG = LoggerFactory.getLogger(TestEmployeeDaoImpl.class);

	@Autowired
	EmployeeDao employeeDao;

	@Autowired
	LoginDao loginDao;
	
	@Autowired
	DepartmentDao departmentDao;
	
	@Autowired
	DBHelper dbHelper;

	/**
	 * Test create login method
	 * @throws Exception
	 */
	@Test
	public void testEmployeeDao() throws Exception {

		dbHelper.deleteTestData();
		long userId = getLoginId();
		long departmentId = getDepartmentId();
		
		Employee record = new Employee();
		record.setEmail("UT@test.com");
		record.setFirstname("UT_Test");
		record.setLastname("UT_test1");
		record.setDepartmentid(new BigDecimal(departmentId));
		record.setUserid(new BigDecimal(userId));
		
		long id = -1;

		/*
		 * TEST insert method
		 */
		try {
			
			LOG.debug("Employee " + record);
			id = employeeDao.insert(record);
		} catch (Exception ex) {
			LOG.error("Error", ex);
			throw ex;
		}

		if (id == -1)
			Assert.fail("Error creating department");

		/*
		 * Test find by ID
		 */
		Employee employee = employeeDao.getEmployeeByDepartmentId(id, departmentId);

		assertNotNull(employee);

		/*
		 * Test find by username
		 */
		List<Employee> employeeList = employeeDao.getAllEmployees("UT");

		assertNotNull(employeeList);
		

	}
	
	private long getDepartmentId() {
		Department record = new Department();
		record.setDepartmentname("UT_TestDepartment");
		
		long id = -1;

		/*
		 * TEST insert method
		 */
		try {
			
			LOG.debug("Department " + record);
			id = departmentDao.insert(record);
		} catch (Exception ex) {
			LOG.error("Error", ex);
			throw ex;
		}
		
		return id;
	}
	
	private long getLoginId() {
		Login record = new Login();
		record.setUsername("UT_test");
		record.setPassword("UT_test");
		long id = -1;

		/*
		 * TEST insert method
		 */
		try {
			
			LOG.debug("LoginDao " + loginDao);
			id = loginDao.insert(record);
		} catch (Exception ex) {
			LOG.error("Error", ex);
			throw ex;
		}
		
		return id;
	}
}
