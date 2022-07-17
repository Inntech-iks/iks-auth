package com.inn.iks.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.inn.iks.auth.entitiy.ErrorMessageDTO;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessageDTO handleAllException(Exception ex, WebRequest request) {
        // quá trình kiểm soat lỗi diễn ra ở đây
        return new ErrorMessageDTO("100", ex.getLocalizedMessage());
    }
	@ExceptionHandler(AuthServiceException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessageDTO handleAuthServiceException(AuthServiceException ex, WebRequest request) {
        // quá trình kiểm soat lỗi diễn ra ở đây
        return new ErrorMessageDTO(ex.getCode(), ex.getLocalizedMessage());
    }

}
