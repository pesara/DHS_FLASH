package com.inalab.dao;

import java.util.List;

import com.inalab.model.Employee;

public interface EmployeeDao {

	public Employee findEmployee(String firstName, String lastName);
	
	public List<Employee> getAllEmployees(String matchPattern);
	
	public String addNewEmployee(String firstName, String lastName, String username, String password, String emailId, String departmentId);
	
	public String addEmployeeToDept(String username, String department);
	
	public String giveKudos(String fromEmployee, String toEmployee);
	
	public List<Employee> findAllEmployeesJoinedByDate(String date);
	
	public List<Employee> findWhoGotKudosToday();
	
}
