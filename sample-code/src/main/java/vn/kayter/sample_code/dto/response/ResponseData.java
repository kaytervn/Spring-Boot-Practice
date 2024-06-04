package vn.kayter.sample_code.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ResponseData<T> {

	int status;
	String message;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	T data;

	// PUT, PATCH, DELETE
	public ResponseData(int status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	// POST, GET
	public ResponseData(int status, String message, T data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
