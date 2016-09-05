package com.inalab.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.inalab.dao.CommonDao;

public  abstract class CommonDaoImpl<T> extends JdbcDaoSupport implements CommonDao<T> {

	private static final Logger LOG = LoggerFactory.getLogger(CommonDaoImpl.class);

	private BeanPropertyRowMapper<T> rowMapper = null;

	private Class<T> type;

	public CommonDaoImpl() {
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		type = (Class) pt.getActualTypeArguments()[0];
		rowMapper = new BeanPropertyRowMapper<T>(type);
	}

	@Override
	public int insert(T record, String sql) {

		KeyHolder keyHolder = new GeneratedKeyHolder();

		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(record);

		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(getDataSource());

		if (LOG.isDebugEnabled())
			LOG.debug("create " + sql + " " + record.toString());

		int retVal = template.update(sql, params, keyHolder, new String[] { "id" });

		if (retVal == -1) {
			LOG.error("error while creating ");
			return -1;
		}
		return keyHolder.getKey().intValue();
	}

	@Override
	public T getById(int id, String sql) {

		T record = null;

		try {
			record = getJdbcTemplate().queryForObject(sql, new Object[] { id }, rowMapper);
		} catch (EmptyResultDataAccessException ex) {
			LOG.error("No Record Found for " + id + " " + sql);
		}

		return record;
	}

	@Override
	public boolean update(T record, String sql) {

		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(record);

		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(getDataSource());

		int retVal = template.update(sql, params);

		if (retVal > 0) {

			if (LOG.isDebugEnabled())
				LOG.debug("Record updated ");

			return true;
		}

		return false;
	}

	@Override
	public List<T> getListById(int id, String sql) {
		List<T> recordList = null;

		try {
			recordList = getJdbcTemplate().query(sql, new Object[] { id }, rowMapper);
		} catch (EmptyResultDataAccessException ex) {
			LOG.error("No Record Found for " + id + " " + sql);
		}

		return recordList;
	}


}
