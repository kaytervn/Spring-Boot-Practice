package vn.kayter.sample_code.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import vn.kayter.sample_code.utils.Payload;

public class ResponseSuccess extends ResponseEntity<Payload> {

	// PUT, PATCH, DELETE
	public ResponseSuccess(HttpStatus status, String message) {
		super(new Payload(status.value(), message), HttpStatus.OK);
	}

	// GET, POST
	public ResponseSuccess(HttpStatus status, String message, Object data) {
		super(new Payload(status.value(), message, data), HttpStatus.OK);
	}
}
