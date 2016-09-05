package com.inalab.dao.impl;

import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.inalab.dao.KudosDao;
import com.inalab.model.Kudos;

public class KudosDaoImpl extends CommonDaoImpl<Kudos> implements KudosDao {

	private BeanPropertyRowMapper<Kudos> kudosRowMapper = new BeanPropertyRowMapper<Kudos>(Kudos.class);

	private static final Logger LOG = LoggerFactory.getLogger(KudosDaoImpl.class);

	@Override
	public Kudos getByFromEmployee(int employeeId) {
		String sql = DBQueries.getQuery("kudos.getByFrom");
		return super.getById(employeeId, sql);
	}

	@Override
	public Kudos getByToEmployee(int employeeId) {
		String sql = DBQueries.getQuery("kudos.getByTo");
		return super.getById(employeeId, sql);
	}

	@Override
	public List<Kudos> getLatestKudos(String date) {
		String sql = DBQueries.getQuery("kudos.getLatest");
		List<Kudos> recordList = null;

		try {
			recordList = getJdbcTemplate().query(sql, new Object[] { Calendar.getInstance().getTime() }, kudosRowMapper);
		} catch (EmptyResultDataAccessException ex) {
			LOG.error("No Record Found for " + date + " " + sql);
		}

		return recordList;
	}

	@Override
	public int insert(Kudos record) {
		String sql = DBQueries.getQuery("kudos.insert");
		return super.insert(record, sql);
	}
	

}
