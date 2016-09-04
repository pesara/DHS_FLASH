package com.inalab.dao;

import java.util.List;

public interface CommonDao<T> {

	int insert(T record, String sql);

	T getById(int id, String sql);

	boolean update(T record, String sql);
	
	List<T> getListById(int id, String sql);
	

}
