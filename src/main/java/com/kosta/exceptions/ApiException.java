package com.kosta.exceptions;

public class ApiException extends RuntimeException{
	
	private String code;
	private String message;
	
	public ApiException(ApiError apiError, String message){
		this.code = apiError.getCode();
		this.message = apiError.getMessage() + message;
	}
	
	public String getCode(){
		return this.code;
	}
	
	public String getMessage(){
		return this.message;
	}
	
}
