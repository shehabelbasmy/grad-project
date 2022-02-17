package com.mediacare.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

public class MediaCareException extends RuntimeException {

	@Setter
	@Getter
	private HttpStatus status;

	public MediaCareException(String message, Throwable cause) {
		super(message, cause);
	}

	public MediaCareException(String message) {
		super(message);
	}
}
