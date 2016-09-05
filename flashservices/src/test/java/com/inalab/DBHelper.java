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

		/*
		 * DELETE user test data
		 */
		int retVal = jdbcTemplate.update("delete from public.user where username like '%UT%'");

		/*
		 * DELETE employee test data
		 */
		retVal = jdbcTemplate.update("delete from public.employee where firstname like '%UT%' or lastname like '%UT%'");

		/*
		 * DELETE department test data
		 */
		retVal = jdbcTemplate.update("delete from public.department where departmentname like '%UT%'");

		
		/*
		 * DELETE kudos test data
		 */
		retVal = jdbcTemplate.update("delete from public.kudos where from_eid in ( select id from public.employee where firstname like '%UT%' or lastname like '%UT%')");

		retVal = jdbcTemplate.update("delete from public.kudos where to_eid in ( select id from public.employee where firstname like '%UT%' or lastname like '%UT%')");

		
		if (retVal == -1)
			return false;

		return true;

	}
	

}
