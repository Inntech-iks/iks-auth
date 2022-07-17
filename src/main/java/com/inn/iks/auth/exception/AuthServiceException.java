package com.inn.iks.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthServiceException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4345268718059909657L;
	private String code;
	private String message;
	/**
	 * @param code
	 * @param message
	 */
	public AuthServiceException(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

}
