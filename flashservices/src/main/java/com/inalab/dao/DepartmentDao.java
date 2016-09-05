package com.inalab.dao;

import java.util.List;

import com.inalab.model.Department;

public interface DepartmentDao {

	List<Department> getDepartmentList();
	
	Department getById(int id);
	
	int insert(Department record);
	
	boolean update(Department record);
}
