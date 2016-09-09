package com.inalab.dao;

import java.util.List;

public interface CommonDao<T> {

	long insert(T record, String sql);

	T getById(long id, String sql);

	boolean update(T record, String sql);
	
	List<T> getListById(long id, String sql);
	

}
