package com.inalab.util;

import java.util.Random;

public class PasswordUtil {

	public static String generatePassword(String email) {

		Random generator = new Random(19580427);

		String emailInit = email.substring(0, email.indexOf("@"));

		int random = generator.nextInt();

		String password = emailInit + new Integer(random).toString();

		return password;

	}
}
