package com.inalab.dao;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.inalab.DBHelper;
import com.inalab.TestBase;
import com.inalab.model.Department;
import com.inalab.model.Employee;
import com.inalab.model.Kudos;
import com.inalab.model.Login;

public class TestKudosDaoImpl extends TestBase {

	private static final Logger LOG = LoggerFactory.getLogger(TestKudosDaoImpl.class);

	@Autowired
	EmployeeDao employeeDao;

	@Autowired
	LoginDao loginDao;

	@Autowired
	DepartmentDao departmentDao;

	@Autowired
	DBHelper dbHelper;

	@Autowired
	KudosDao kudosDao;

	/**
	 * Test create login method
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDepartmentDao() throws Exception {

		dbHelper.deleteTestData();
		int employeeOne = getEmployeeId(1);
		int employeeTwo = getEmployeeId(2);

		Kudos record = new Kudos();
		record.setFromEid(new BigDecimal(employeeOne));
		record.setToEid(new BigDecimal(employeeTwo));

		int id = -1;

		/*
		 * TEST insert method
		 */
		try {

			LOG.debug("Kudos " + record);
			id = kudosDao.insert(record);
		} catch (Exception ex) {
			LOG.error("Error", ex);
			throw ex;
		}

		if (id == -1)
			Assert.fail("Error creating kudos");

		/*
		 * Test find by ID
		 */
		Kudos kudos = kudosDao.getByToEmployee(employeeTwo);

		assertNotNull(kudos);

		kudos = kudosDao.getByFromEmployee(employeeOne);

		assertNotNull(kudos);

	}

	private int getDepartmentId() {
		Department record = new Department();
		record.setDepartmentname("UT_TestDepartment");

		int id = -1;

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

	private int getLoginId() {
		Login record = new Login();
		record.setUsername("UT_test");
		record.setPassword("UT_test");
		int id = -1;

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

	private int getEmployeeId(int count) {

		int userId = getLoginId();
		int departmentId = getDepartmentId();

		Employee record = new Employee();
		record.setEmail("UT@test.com");
		record.setFirstname("UT_Test_" + count);
		record.setLastname("UT_test1_" + count);
		record.setDepartmentid(new BigDecimal(departmentId));
		record.setUserid(new BigDecimal(userId));

		int id = -1;

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

		return id;
	}
}
