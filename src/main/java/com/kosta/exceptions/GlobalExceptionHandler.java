package com.kosta.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosta.response.ApiResponse;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	public ApiResponse runtimeExceptionHandler(RuntimeException e){
		if( e instanceof ApiException){
			ApiException apiE = (ApiException)e;
			ApiError apiError = new ApiError(
								apiE.getCode(),
								apiE.getMessage()
			);
			return new ApiResponse(apiError);
		}
		
		ApiError internalServerError = ApiError.INVAILE_SEVER_ERROR	;
		internalServerError.setMessage(e.getMessage());
		return new ApiResponse(internalServerError);
	}
}	
