package vn.kayter.sample_code.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({ MethodArgumentNotValidException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleValidationException(Exception e, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse();
		System.out.print("========> Handle Validation Exception");
		errorResponse.setTimestamp(new Date());
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
		errorResponse.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
		String message = e.getMessage();
		if (e instanceof MethodArgumentNotValidException) {
			int start = message.lastIndexOf("[");
			int end = message.lastIndexOf("]");
			message = message.substring(start + 1, end - 1);
		}
		errorResponse.setMessage(message);
		return errorResponse;
	}
}
