package com.kosta.response;

public class ApiDataResponse<E> extends ApiResponse {

	private E data;

	public ApiDataResponse(E e) {
		super();
		this.data = e;
	}
	
	public E getData() {
		return data;
	}

	public void setData(E data) {
		this.data = data;
	}

}
