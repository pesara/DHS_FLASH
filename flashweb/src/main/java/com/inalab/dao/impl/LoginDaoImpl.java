package com.inalab.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.inalab.dao.LoginDao;
import com.inalab.model.Login;

public class LoginDaoImpl extends JdbcDaoSupport implements LoginDao {

	private static final Logger LOG = LoggerFactory.getLogger(LoginDaoImpl.class);

	private BeanPropertyRowMapper<Login> loginRowMapper = new BeanPropertyRowMapper<Login>(Login.class);

	@Override
	public int insert(Login record) {

		KeyHolder keyHolder = new GeneratedKeyHolder();

		String insertSql = DBQueries.getQuery("login.insert");

		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(record);

		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(getDataSource());

		if (LOG.isDebugEnabled())
			LOG.debug("createLogin " + insertSql + " " + record.toString());

		int retVal = template.update(insertSql, params, keyHolder, new String[] { "id" });

		if (retVal == -1) {
			LOG.error("error while creating login");
			return -1;
		}
		return keyHolder.getKey().intValue();
	}

	@Override
	public Login getById(int id) {
		String getSql = DBQueries.getQuery("login.getById");
		
		Login record = null;
		
		try {
			record = getJdbcTemplate().queryForObject(getSql, new Object[] { id }, loginRowMapper);
		}
		catch(EmptyResultDataAccessException ex) {
			LOG.error("No Record found for " + id);
		}
		
		return record;
	}

	@Override
	public Login getByUserName(String userName) {
		String getSql = DBQueries.getQuery("login.getByUserName");
		
		Login record = null;
		
		try {
			record = getJdbcTemplate().queryForObject(getSql, new Object[] { userName }, loginRowMapper);
		}
		catch(EmptyResultDataAccessException ex) {
			LOG.error("No Record Found for " + userName);
		}
		
		return record;
	}

	@Override
	public boolean update(Login record) {
		
		String updateSql = DBQueries.getQuery("login.update");
		
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(record);

		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(getDataSource());

		int retVal = template.update(updateSql,params);
		
		if(retVal > 0) {
			
			if(LOG.isDebugEnabled())
				LOG.debug("Record updated for " + record.getUsername());
			
			return true;
		}
		
		return false;
	}
	
	

}
