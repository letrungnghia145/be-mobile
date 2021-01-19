package com.nghiale.api.exception;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.NoSuchElementException;

import org.hibernate.HibernateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class AppExceptionHandler {
	@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ErrorMessage getDuplicateEntryError(Exception exception, WebRequest webRequest) {
		return new ErrorMessage(406, "Duplicate entry");
	}

	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoSuchElementException.class)
	public ErrorMessage getNoSuchElementException(Exception exception, WebRequest webRequest) {
		return new ErrorMessage(404, "Content not found");
	}

	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(HibernateException.class)
	public ErrorMessage getHibernateException(Exception exception, WebRequest webRequest) {
		return new ErrorMessage(500, exception.getCause().toString());
	}

	@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
	@ExceptionHandler(ConfirmationCodeError.class)
	public ErrorMessage getConfirmationCodeError(Exception exception, WebRequest webRequest) {
		return new ErrorMessage(406, exception.getMessage());
	}

	@ResponseStatus(code = HttpStatus.FORBIDDEN)
	@ExceptionHandler(AccessDeniedException.class)
	public ErrorMessage getAccessDeniedException(Exception exception, WebRequest webRequest) {
		return new ErrorMessage(403, exception.getMessage());
	}

	@ResponseStatus(code = HttpStatus.FORBIDDEN)
	@ExceptionHandler(AuthenticationException.class)
	public ErrorMessage getAuthenticationException(Exception exception, WebRequest webRequest) {
		return new ErrorMessage(403, "Authentication error");
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ErrorMessage getBadRequestException(Exception exception, WebRequest webRequest) {
		return new ErrorMessage(400, "Missing request body");
	}

	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ErrorMessage getAllException(Exception exception, WebRequest webRequest) {
		return new ErrorMessage(500, exception.getMessage());
	}

}
