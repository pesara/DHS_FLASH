package com.inalab.dao;

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
	public void testCreateLogin() throws Exception {

		dbHelper.deleteTestData();

		Login record = new Login();
		record.setUsername("test");
		record.setPassword("test");
		int id = -1;

		try {
			System.out.println(loginDao);
			LOG.debug("LoginDao " + loginDao);
			id = loginDao.insert(record);
		} catch (Exception ex) {
			LOG.error("Error", ex);
			throw ex;
		}

		if (id == -1)
			Assert.fail("Error creating login");

		Login login = loginDao.getById(id);

		assertNotNull(login);

		login = loginDao.getByUserName(record.getUsername());

		assertNotNull(login);
	}
}
