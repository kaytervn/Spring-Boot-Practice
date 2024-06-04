package vn.kayter.sample_code.utils;

public class Payload {
	int status;
	String message;
	Object data;

	public Payload(int status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public Payload(int status, String message, Object data) {
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

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
