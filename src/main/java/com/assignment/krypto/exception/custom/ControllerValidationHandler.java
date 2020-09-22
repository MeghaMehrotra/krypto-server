package com.assignment.krypto.exception.custom;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.assignment.krypto.dto.ErrorDTO;
import com.assignment.krypto.dto.MessageType;




@ControllerAdvice
public class ControllerValidationHandler {


	final static List<String> businessErrorMessage = Arrays.asList("Somthing went wrong..");

	@Autowired
	private MessageSource messageSource;

	/**
	 * Method for handling MethodArgumentNotValidException Exception
	 * 
	 * @param exception
	 * @return {@link ErrorDTO}
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorDTO processValidationError(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		List<FieldError> errors = result.getFieldErrors();

		return processFieldError(errors);
	}

	private ErrorDTO processFieldError(List<FieldError> errors) {
		ErrorDTO message = null;
		List<String> messages = new ArrayList<String>();
		errors.forEach((error) -> {
			if (error != null) {
				Locale currentLocale = LocaleContextHolder.getLocale();
				String msg = messageSource.getMessage(error.getDefaultMessage(), new String[] { error.getField() },
						currentLocale);
				messages.add(msg);
			}
		});
		message = new ErrorDTO(messages, MessageType.ERROR);
		return message;
	}

	@ExceptionHandler(value = MindbowserMessageException.class)
	protected ResponseEntity<ErrorDTO> handleConflict(MindbowserMessageException exception, WebRequest request) {
		/*logger.debug("Inside method: processCustomException() handling exception");
		logger.error("Exception message: " + exception.getMessage());
		logger.debug("Exception: " + exception);*/
		ErrorDTO message = new ErrorDTO();
		message.setMessage(Arrays.asList(exception.getMessage()));
		message.setType(MessageType.ERROR);
		System.out.println(exception.getType());
		return new ResponseEntity<ErrorDTO>(message, exception.getType());
	}

	@ExceptionHandler({HttpMessageNotReadableException.class,SQLException.class,IllegalStateException.class})
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorDTO handleBusinessException(HttpMessageNotReadableException ex) {
		ErrorDTO message = new ErrorDTO(businessErrorMessage, MessageType.ERROR);
		return message;
	}
	
/*	@ExceptionHandler({NullPointerException.class})
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorDTO handleNullPointerException(NullPointerException ex) {
		ErrorDTO message = new ErrorDTO(businessErrorMessage, MessageType.ERROR);
		return message;
	}*/


	
}
