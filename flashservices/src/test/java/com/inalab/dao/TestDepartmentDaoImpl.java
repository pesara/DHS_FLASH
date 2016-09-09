package com.inalab.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.inalab.DBHelper;
import com.inalab.TestBase;
import com.inalab.model.Department;

public class TestDepartmentDaoImpl extends TestBase {

	private static final Logger LOG = LoggerFactory.getLogger(TestDepartmentDaoImpl.class);

	@Autowired
	DepartmentDao departmentDao;

	@Autowired
	DBHelper dbHelper;

	/**
	 * Test create login method
	 * @throws Exception
	 */
	@Test
	public void testDepartmentDao() throws Exception {

		dbHelper.deleteTestData();

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

		if (id == -1)
			Assert.fail("Error creating department");

		/*
		 * Test find by ID
		 */
		Department department = departmentDao.getById(id);

		assertNotNull(department);

		/*
		 * Test find by username
		 */
		List<Department> departmentList = departmentDao.getDepartmentList();

		assertNotNull(departmentList);
		
		/*
		 * Test Update password
		 */
		department.setDepartmentname("UT1_Department");
		boolean retVal = departmentDao.update(department);
		department = departmentDao.getById(id);
		assertEquals("UT1_Department", department.getDepartmentname());
	}
}
