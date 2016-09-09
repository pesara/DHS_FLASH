package com.inalab.dao.impl;

import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.inalab.dao.KudosDao;
import com.inalab.model.Kudos;

public class KudosDaoImpl extends CommonDaoImpl<Kudos>implements KudosDao {

	private BeanPropertyRowMapper<Kudos> kudosRowMapper = new BeanPropertyRowMapper<Kudos>(Kudos.class);

	private static final Logger LOG = LoggerFactory.getLogger(KudosDaoImpl.class);

	@Override
	public List<Kudos> getByFromEmployee(long employeeId) {
		String sql = DBQueries.getQuery("kudos.getByFrom");
		List<Kudos> recordList = null;

		try {
			recordList = getJdbcTemplate().query(sql, new Object[] { employeeId }, kudosRowMapper);
		} catch (EmptyResultDataAccessException ex) {
			LOG.error("No Record Found for " + employeeId + " " + sql);
		}

		return recordList;
	}

	@Override
	public List<Kudos> getByToEmployee(long employeeId) {
		String sql = DBQueries.getQuery("kudos.getByTo");
		List<Kudos> recordList = null;

		try {
			recordList = getJdbcTemplate().query(sql, new Object[] { employeeId }, kudosRowMapper);
		} catch (EmptyResultDataAccessException ex) {
			LOG.error("No Record Found for " + employeeId + " " + sql);
		}

		return recordList;
	}

	@Override
	public List<Kudos> getLatestKudos(String date) {
		String sql = DBQueries.getQuery("kudos.getLatest");
		List<Kudos> recordList = null;

		try {
			recordList = getJdbcTemplate().query(sql, new Object[] { Calendar.getInstance().getTime() },
					kudosRowMapper);
		} catch (EmptyResultDataAccessException ex) {
			LOG.error("No Record Found for " + date + " " + sql);
		}

		return recordList;
	}

	@Override
	public long insert(Kudos record) {
		String sql = DBQueries.getQuery("kudos.insert");
		return super.insert(record, sql);
	}

	@Override
	public Kudos getFromEmployeeToEmployee(long fromEmployeeId, long toEmployeeId) {
		String sql = DBQueries.getQuery("kudos.getByFromEmployeeToEmployee");
		Kudos record = null;

		try {
			record = getJdbcTemplate().queryForObject(sql, new Object[] { fromEmployeeId, toEmployeeId },
					kudosRowMapper);
		} catch (EmptyResultDataAccessException ex) {
			LOG.error("No Record Found for kudos from " + fromEmployeeId + " to " + toEmployeeId + " " + sql);
		}

		return record;
	}

	@Override
	public boolean update(Kudos record) {
		String sql = DBQueries.getQuery("kudos.update");
		return super.update(record, sql);
	}



}
