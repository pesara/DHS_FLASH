package com.inalab.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.inalab.dao.LoginDao;

public class ApplicationLoader {

	public static void load() {
		ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");

		LoginDao loginDao = (LoginDao) appContext.getBean("loginDao");
	}

	public static void main(String[] args) {
		ApplicationLoader.load();
	}
}
