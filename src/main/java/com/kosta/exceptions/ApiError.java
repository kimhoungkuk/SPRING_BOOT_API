package com.kosta.exceptions;

public class ApiError {

	public static final ApiError MISSING_PARAMETER = new ApiError("1000","Messing Require Paramter : ");
	public static final ApiError INVAILD_PARAMETER = new ApiError("1001","Invalid Parameter : ");
	public static final ApiError INVAILE_SEVER_ERROR = new ApiError("9000","Internal Server Error");
	
	private String code;
	private String message;

	// Alt + Shift + S -> O
	public ApiError(String code, String message) {
		this.code = code;
		this.message = message;
	}

	// Alt + Shift + S -> R
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message += message;
	}

}
