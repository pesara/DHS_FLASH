package com.inalab.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2565050467638119401L;

	public UserNotFoundException(String userId) {
		super("could not find user '" + userId + "'.");
	}
}