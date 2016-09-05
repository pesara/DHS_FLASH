package com.inalab.dao;

import java.util.List;

import com.inalab.model.Employee;

public interface EmployeeDao {

	public Employee findEmployee(String firstName, String lastName);
	
	public List<Employee> getAllEmployees(String matchPattern);
	
	public int insert(Employee record);
	
	public String addEmployeeToDept(int employeeId, int departmentId);
	
	public List<Employee> findAllEmployeesJoinedByDate(String date);
	
	public List<Employee> findAllEmployeesByDepartment(int departmentId);
	
	public Employee getEmployeeByDepartmentId(int employeeId, int departmentId);
	
	public boolean updateDepartmentId(int employeeId, int departmentId);
	
	boolean update(Employee record);
}
