package com.inalab.dao;

import java.util.List;

import com.inalab.model.Employee;

public interface EmployeeDao {

	/**
	 * Find employee based on first and last name
	 * @param firstName
	 * @param lastName
	 * @return
	 */
	Employee findEmployee(String firstName, String lastName);

	/**
	 * Get all the employees for matching first and last name
	 * @param matchPattern
	 * @return
	 */
	List<Employee> getAllEmployees(String matchPattern);

	/**
	 * Insert employee
	 * @param record
	 * @return
	 */
	long insert(Employee record);

	/**
	 * Assign department to employee
	 * @param employeeId
	 * @param departmentId
	 * @return
	 */
	String addEmployeeToDept(long employeeId, long departmentId);

	/**
	 * Find employee joined on given date
	 * @param date
	 * @return
	 */
	List<Employee> findAllEmployeesJoinedByDate(String date);

	/**
	 * find employees for the given department id
	 * @param departmentId
	 * @return
	 */
	List<Employee> findAllEmployeesByDepartment(long departmentId);

	/**
	 * Get employee based on employeeId and departmentId where active indicator is not 'N'
	 * @param employeeId
	 * @param departmentId
	 * @return
	 */
	Employee getEmployeeByDepartmentId(long employeeId, long departmentId);

	/**
	 * Update department id for the given employee
	 * @param employeeId
	 * @param departmentId
	 * @return
	 */
	boolean updateDepartmentId(long employeeId, long departmentId);

	/**
	 * Update employee data
	 * @param record
	 * @return
	 */
	boolean update(Employee record);

	/**
	 * Get employee by employeeId
	 * @param employeeId
	 * @return
	 */
	Employee getById(long employeeId);
    
	/**
	 * Update employee activation 
	 * @param employeeId
	 * @param status
	 * @return
	 */
	boolean updateActiveIndicator(long employeeId, String status );
}
