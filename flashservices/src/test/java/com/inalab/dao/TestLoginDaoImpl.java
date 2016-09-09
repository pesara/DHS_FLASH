package com.inalab.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.inalab.DBHelper;
import com.inalab.TestBase;
import com.inalab.model.Login;

public class TestLoginDaoImpl extends TestBase {

	private static final Logger LOG = LoggerFactory.getLogger(TestLoginDaoImpl.class);

	@Autowired
	LoginDao loginDao;

	@Autowired
	DBHelper dbHelper;

	/**
	 * Test create login method
	 * @throws Exception
	 */
	@Test
	public void testLoginDao() throws Exception {

		dbHelper.deleteTestData();

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

		if (id == -1)
			Assert.fail("Error creating login");

		/*
		 * Test find by ID
		 */
		Login login = loginDao.getById(id);

		assertNotNull(login);

		/*
		 * Test find by username
		 */
		login = loginDao.getByUserName(record.getUsername());

		assertNotNull(login);
		
		/*
		 * Test Update password
		 */
		login.setPassword("UT1_test");
		boolean retVal = loginDao.update(login);
		login = loginDao.getByUserName(login.getUsername());
		assertEquals("UT1_test", login.getPassword());
	}
}
