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
	
	Login getById(int id);
	
	Login getByUserName(String userName);
	
	boolean update(Login record);
	
	
}
