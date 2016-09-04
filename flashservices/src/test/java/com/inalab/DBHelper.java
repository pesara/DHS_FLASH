package com.inalab;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DBHelper {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/**
	 * Clean up test data
	 * @return
	 * @throws Exception
	 */
	public boolean deleteTestData() throws Exception {

		int retVal = jdbcTemplate.update("delete from public.user where username = ?", new Object[] { "test" });

		if (retVal == -1)
			return false;

		return true;

	}
}
