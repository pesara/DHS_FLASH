package com.inalab.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class KudosNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1890112517073616049L;


	public KudosNotFoundException() {
		
	}
}