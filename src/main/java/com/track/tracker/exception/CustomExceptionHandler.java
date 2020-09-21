package com.track.tracker.exception;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.track.tracker.constants.ResponseConstants;
import com.track.tracker.response.ErrorResponse;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	private static final Logger LOG = LoggerFactory.getLogger(CustomExceptionHandler.class);

	@ExceptionHandler(UnsupportedFileException.class)
	public ResponseEntity<Object> handleExceptions(UnsupportedFileException ex, WebRequest request) {
		LOG.error(ex.getLocalizedMessage());
		ErrorResponse errorResponse = new ErrorResponse(ResponseConstants.UNSUPPORTED_FILE_CODE,
				ResponseConstants.UNSUPPORTED_FILE_MESSAGE);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(JsonMappingException.class)
	public ResponseEntity<Object> handleExceptions(JsonMappingException ex, WebRequest request) {
		LOG.error(ex.getLocalizedMessage());
		List<Reference> reference = ex.getPath();
		Set<String> returnMessage = new HashSet<>(1);
		if (reference.size() > 0)
			returnMessage.add(reference.get(reference.size() - 1).getFieldName());
		ErrorResponse errorResponse = new ErrorResponse(ResponseConstants.ERROR_MAPPING_FILE_CODE,
				ResponseConstants.ERROR_MAPPING_FILE_MESSAGE, returnMessage);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(IOException.class)
	public ResponseEntity<Object> handleExceptions(IOException ex, WebRequest request) {
		LOG.error(ex.getLocalizedMessage());
		ErrorResponse errorResponse = new ErrorResponse(ResponseConstants.ERROR_BAD_FILE_CODE,
				ResponseConstants.ERROR_BAD_FILE_MESSAGE);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<Object> handleExceptions(NoSuchElementException ex, WebRequest request) {
		LOG.error(ex.getLocalizedMessage());
		ErrorResponse errorResponse = new ErrorResponse(ResponseConstants.NOT_FOUND_CODE,
				ResponseConstants.NOT_FOUND_MESSAGE);
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object> handleExceptions(IllegalArgumentException ex, WebRequest request) {
		LOG.error(ex.getLocalizedMessage());
		Set<String> returnMessage = new HashSet<String>();
		returnMessage.add(ex.getLocalizedMessage());
		ErrorResponse errorResponse = new ErrorResponse(ResponseConstants.ILLEGAL_ARGUMENT_CODE,
				ResponseConstants.ILLEGAL_ARGUMENT_MESSAGE, returnMessage);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleExceptions(ConstraintViolationException ex, WebRequest request) {
		LOG.error(ex.getLocalizedMessage());
		Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
		Set<String> returnMessage = new HashSet<>(constraintViolations.size());
		returnMessage
				.addAll(constraintViolations
						.stream().map(constraintViolation -> String.format("%s: %s",
								constraintViolation.getPropertyPath(), constraintViolation.getMessage()))
						.collect(Collectors.toList()));
		ErrorResponse errorResponse = new ErrorResponse(ResponseConstants.CONSTRAINT_VIOLATION_CODE,
				ResponseConstants.CONSTRAINT_VIOLATION_MESSAGE, returnMessage);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<Object> handleExceptions(Exception ex, WebRequest request) {
		LOG.error(ex.getLocalizedMessage());
		ErrorResponse errorResponse = new ErrorResponse(ResponseConstants.UNKNOW_ERROR_CODE,
				ResponseConstants.UNKNOW_ERROR_MESSAGE);
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
