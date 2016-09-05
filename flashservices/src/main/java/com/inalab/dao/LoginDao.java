package com.inalab.dao;

import java.util.List;

import com.inalab.model.Login;

public interface LoginDao {
	
	/**
	 * Create login with encrypted password
	 * @param record
	 * @return
	 */
	int insert(Login record);
	
	/**
	 * Get login by user id
	 * @param id
	 * @return
	 */
	Login getById(int id);
	
	/**
	 * Get login by username
	 * @param userName
	 * @return
	 */
	Login getByUserName(String userName);
	
	/**
	 * Update login data
	 * @param record
	 * @return
	 */
	boolean update(Login record);
	
	
}
