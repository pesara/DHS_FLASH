package com.inalab.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inalab.model.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONResponse {

	private static final Logger LOG = LoggerFactory.getLogger(JSONResponse.class);
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	public static final int SYSTEM_ERROR = 999;

	public static ApiResponse getResponse(Object o) {
		ApiResponse response = new ApiResponse();
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString;
		try {

			jsonInString = mapper.writeValueAsString(o);
			response.setData(jsonInString);
			response.setStatus(SUCCESS);
			// response.setCode(200);
			response.setMessage("Request successfully completed.");
		} catch (JsonProcessingException e) {
			LOG.error("Error generating response");
			// response.setCode(SYSTEM_ERROR);
			response.setStatus(ERROR);
			response.setMessage("System Error");
		}

		return response;
	}
}
