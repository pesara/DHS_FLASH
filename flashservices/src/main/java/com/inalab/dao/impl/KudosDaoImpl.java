package com.inalab.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.inalab.dao.KudosDao;
import com.inalab.model.Kudos;

public class KudosDaoImpl extends CommonDaoImpl<Kudos> implements KudosDao {

	private BeanPropertyRowMapper<Kudos> kudosRowMapper = new BeanPropertyRowMapper<Kudos>(Kudos.class);

	private static final Logger LOG = LoggerFactory.getLogger(KudosDaoImpl.class);

	@Override
	public Kudos getByFromEmployee(int employeeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Kudos getByToEmployee(int employeeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Kudos> getLatestKudos(String date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(Kudos record) {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
