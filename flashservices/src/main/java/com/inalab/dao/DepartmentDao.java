package com.inalab.dao;

import java.util.List;

import com.inalab.model.Department;

public interface DepartmentDao {

	/**
	 * Get all department list
	 * @return
	 */
	List<Department> getDepartmentList();
	
	/**
	 * Get department by input id
	 * @param id
	 * @return
	 */
	Department getById(int id);
	
	/**
	 * Get department by name
	 * @param departmentName
	 * @return
	 */
	Department getByName(String departmentName);
	
	/**
	 * Insert department detail
	 * @param record
	 * @return
	 */
	int insert(Department record);
	
	boolean update(Department record);
}
