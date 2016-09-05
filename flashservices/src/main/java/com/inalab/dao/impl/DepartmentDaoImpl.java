package com.inalab.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.inalab.dao.DepartmentDao;
import com.inalab.model.Department;

public class DepartmentDaoImpl extends CommonDaoImpl<Department>implements DepartmentDao {

	private BeanPropertyRowMapper<Department> departmentRowMapper = new BeanPropertyRowMapper<Department>(
			Department.class);

	private static final Logger LOG = LoggerFactory.getLogger(DepartmentDaoImpl.class);

	@Override
	public List<Department> getDepartmentList() {

		String sql = DBQueries.getQuery("department.getAll");

		List<Department> recordList = null;

		try {
			recordList = getJdbcTemplate().query(sql, departmentRowMapper);
		} catch (EmptyResultDataAccessException ex) {
			LOG.error("No Record Found for " + sql);
		}

		return recordList;
	}

	@Override
	public Department getById(int id) {
		String sql = DBQueries.getQuery("department.getById");
		return super.getById(id, sql);
	}

	
	@Override
	public int insert(Department record) {
		String sql = DBQueries.getQuery("department.insert");
		return super.insert(record, sql);
	}

	@Override
	public boolean update(Department record) {
		String sql = DBQueries.getQuery("department.update");
		return super.update(record, sql);
	}

	@Override
	public Department getByName(String departmentName) {
		String sql = DBQueries.getQuery("department.getByName");
		
		Department record = null;

		try {
			record = getJdbcTemplate().queryForObject(sql, new Object[] { departmentName }, departmentRowMapper);
		} catch (EmptyResultDataAccessException ex) {
			LOG.error("No Record Found for " + departmentName + " " + sql);
		}

		return record;
		
	}
	
}
