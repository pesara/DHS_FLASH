package com.inalab.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.inalab.dao.DepartmentDao;
import com.inalab.model.Department;

public class DepartmentDaoImpl extends CommonDaoImpl<Department> implements DepartmentDao {

	private BeanPropertyRowMapper<Department> departmentRowMapper = new BeanPropertyRowMapper<Department>(Department.class);

	private static final Logger LOG = LoggerFactory.getLogger(DepartmentDaoImpl.class);


}
