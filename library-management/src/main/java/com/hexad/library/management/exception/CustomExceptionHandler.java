package com.hexad.library.management.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.hexad.library.management.common.Constants;
import com.hexad.library.management.model.ErrorResponse;

@SuppressWarnings({ "unchecked", "rawtypes" })
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.name(), details);
		return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(BookNotFoundException.class)
	public final ResponseEntity<Object> handleBookNotFoundException(BookNotFoundException ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.name(), details);
		return new ResponseEntity(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ISBNDoesNotExistsException.class)
	public final ResponseEntity<Object> handleISBNDoesNotExistsException(ISBNDoesNotExistsException ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.name(), details);
		return new ResponseEntity(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NotAllowedToBarrowException.class)
	public final ResponseEntity<Object> handleNotAllowedToBarrowException(NotAllowedToBarrowException ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse(Constants.NOT_ALLOWED_TO_BARROW, details);
		return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(OutOfStockException.class)
	public final ResponseEntity<Object> handleOutOfStockException(OutOfStockException ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse(Constants.OUT_OF_STOCK, details);
		return new ResponseEntity(error, HttpStatus.INSUFFICIENT_STORAGE);
	}
	
	@ExceptionHandler(UserExceededBookCreditLimitException.class)
	public final ResponseEntity<Object> handleUserExceededBookCreditLimitException(UserExceededBookCreditLimitException ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.name(), details);
		return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
	}
	
}