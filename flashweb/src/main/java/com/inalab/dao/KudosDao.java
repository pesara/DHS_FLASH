package com.inalab.dao;

import java.util.List;

import com.inalab.model.Kudos;

public interface KudosDao {

	Kudos getByFromEmployee(int employeeId);
	
	Kudos getByToEmployee(int employeeId);
	
	List<Kudos> getLatestKudos(String date); 
	
	int insert(Kudos record);
}
