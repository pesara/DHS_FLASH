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
	int insert(Employee record);

	/**
	 * Assign department to employee
	 * @param employeeId
	 * @param departmentId
	 * @return
	 */
	String addEmployeeToDept(int employeeId, int departmentId);

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
	List<Employee> findAllEmployeesByDepartment(int departmentId);

	/**
	 * Get employee based on employeeId and departmentId where active indicator is not 'N'
	 * @param employeeId
	 * @param departmentId
	 * @return
	 */
	Employee getEmployeeByDepartmentId(int employeeId, int departmentId);

	/**
	 * Update department id for the given employee
	 * @param employeeId
	 * @param departmentId
	 * @return
	 */
	boolean updateDepartmentId(int employeeId, int departmentId);

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
	Employee getById(int employeeId);
    
	/**
	 * Update employee activation 
	 * @param employeeId
	 * @param status
	 * @return
	 */
	boolean updateActiveIndicator(int employeeId, String status );
}
