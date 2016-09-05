package com.inalab.dao;

import java.util.List;

import com.inalab.model.Kudos;

public interface KudosDao {

	/**
	 * Get Kudos detail given by input employeeId
	 * @param employeeId
	 * @return
	 */
	List<Kudos> getByFromEmployee(int employeeId);
	
	/**
	 * Get Kudos give to input employeeId
	 * @param employeeId
	 * @return
	 */
	List<Kudos> getByToEmployee(int employeeId);
	
	/**
	 * Get all the latest kudos detail for given date
	 * @param date
	 * @return
	 */
	List<Kudos> getLatestKudos(String date); 
	
	/**
	 * Insert kudos detail
	 * @param record
	 * @return
	 */
	int insert(Kudos record);
	
	/**
	 * Get Kudos given to employee from specific employee
	 * @param fromEmployeeId
	 * @param toEmployeeId
	 * @return
	 */
	Kudos getFromEmployeeToEmployee(int fromEmployeeId, int toEmployeeId);
	
	/**
	 * Update kudos detail
	 * @param record
	 * @return
	 */
	boolean update(Kudos record);
}
