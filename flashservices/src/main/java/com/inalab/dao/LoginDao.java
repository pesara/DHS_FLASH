package com.inalab.dao;

import com.inalab.model.Login;

public interface LoginDao {
	
	/**
	 * Create login with encrypted password
	 * @param record
	 * @return
	 */
	long insert(Login record);
	
	/**
	 * Get login by user id
	 * @param id
	 * @return
	 */
	Login getById(long id);
	
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
