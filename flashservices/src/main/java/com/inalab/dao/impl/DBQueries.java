package com.inalab.dao.impl;

import java.util.ResourceBundle;

public class DBQueries {

	private static ResourceBundle resource;
	
	static {
		resource = ResourceBundle.getBundle(DBQueries.class.getName());
	}
	
	public static String getQuery(String key) {
		return resource.getString(key);
	}
}
