package com.inalab.dao;

import java.util.List;

import com.inalab.model.Kudos;

public interface KudosDao {

	/**
	 * Get Kudos detail given by input employeeId
	 * @param employeeId
	 * @return
	 */
	List<Kudos> getByFromEmployee(long employeeId);
	
	/**
	 * Get Kudos give to input employeeId
	 * @param employeeId
	 * @return
	 */
	List<Kudos> getByToEmployee(long employeeId);
	
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
	long insert(Kudos record);
	
	/**
	 * Get Kudos given to employee from specific employee
	 * @param fromEmployeeId
	 * @param toEmployeeId
	 * @return
	 */
	Kudos getFromEmployeeToEmployee(long fromEmployeeId, long toEmployeeId);
	
	/**
	 * Update kudos detail
	 * @param record
	 * @return
	 */
	boolean update(Kudos record);
}
