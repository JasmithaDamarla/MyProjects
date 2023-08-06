package com.epam.restapiexceptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.epam.customexceptions.FunctionalityException;
import com.epam.customexceptions.InvalidIdInput;
import com.epam.customexceptions.InvalidInputException;
import com.epam.customexceptions.QuestionLibException;
import com.epam.customexceptions.QuizLibException;
import com.epam.customexceptions.SignUpFailedException;
import com.epam.customexceptions.UserNotFoundException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class RestControllerExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException func, WebRequest req) {
		List<String> inpErrors = new ArrayList<>();
		func.getAllErrors()
			.forEach(error -> 
				inpErrors.add(error.getDefaultMessage()));
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date().toString(),
				HttpStatus.BAD_REQUEST.name(), inpErrors.toString(), req.getDescription(false));
		log.info("Method arugument not valid exception raised : {}",inpErrors.toString());
		return new ResponseEntity<>(exceptionResponse,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(FunctionalityException.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	public ResponseEntity<ExceptionResponse> handleFunctionalityException(FunctionalityException func, WebRequest req) {
		log.info("Functionality exception has been raised : {}",func.getMessage());
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date().toString(),
				HttpStatus.BAD_REQUEST.name(),func.getMessage(), req.getDescription(false));
		return new ResponseEntity<>(exceptionResponse,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidIdInput.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	public ResponseEntity<ExceptionResponse> handleInvalidIdInputException(InvalidIdInput inp, WebRequest req) {
		log.info("invalid id exception has been raised : {}",inp.getMessage());
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date().toString(),
				HttpStatus.BAD_REQUEST.name(),inp.getMessage(), req.getDescription(false));
		return new ResponseEntity<>(exceptionResponse,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidInputException.class)
	@ResponseStatus(value=HttpStatus.NO_CONTENT)
	public ResponseEntity<ExceptionResponse> handleInvalidInputException(InvalidInputException invalidInp, WebRequest req) {
		log.info("invalid input exception has been raised : {}",invalidInp.getMessage());
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date().toString(),
				HttpStatus.BAD_REQUEST.name(),invalidInp.getMessage(), req.getDescription(false));
		return new ResponseEntity<>(exceptionResponse,HttpStatus.NO_CONTENT);
	}
	
	@ExceptionHandler(QuestionLibException.class)
	public ResponseEntity<ExceptionResponse> handleQuestionNotFoundException(QuestionLibException notFound, WebRequest req) {
		log.info("Question lib exception has been raised : {}",notFound.getMessage());
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date().toString(),
				HttpStatus.BAD_REQUEST.name(),notFound.getMessage(), req.getDescription(false));
		return new ResponseEntity<>(exceptionResponse,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(QuizLibException.class)
	public ResponseEntity<ExceptionResponse> handleQuizNotFoundException(QuizLibException notFound, WebRequest req) {
		log.info("Quiz lib exception has been raised : {}",notFound.getMessage());
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date().toString(),
				HttpStatus.BAD_REQUEST.name(),notFound.getMessage(), req.getDescription(false));
		return new ResponseEntity<>(exceptionResponse,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleUserNotFoundException(UserNotFoundException notFound, WebRequest req) {
		log.info("User Not found exception has been raised : {}",notFound.getMessage());
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date().toString(),
				HttpStatus.BAD_REQUEST.name(),notFound.getMessage(), req.getDescription(false));
		return new ResponseEntity<>(exceptionResponse,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(SignUpFailedException.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	public ResponseEntity<ExceptionResponse> handleSignUpFailedException(SignUpFailedException fail, WebRequest req) {
		log.info("Sign up failed exception has been raised : {}",fail.getMessage());
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date().toString(),
				HttpStatus.BAD_REQUEST.name(),fail.getMessage(), req.getDescription(false));
		return new ResponseEntity<>(exceptionResponse,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	public ResponseEntity<ExceptionResponse> handleSQLIntegerityException(DataIntegrityViolationException fail, WebRequest req) {
		log.info("sql integrity exception has been raised : {}",fail.getMessage());
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date().toString(),
				HttpStatus.BAD_REQUEST.name(),"Could not delete question beacuse it is present in quiz", req.getDescription(false));
		return new ResponseEntity<>(exceptionResponse,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	public ResponseEntity<ExceptionResponse> handleRuntimeException(RuntimeException runtime, WebRequest req) {
		log.info("Run time exception has been raised : {}",runtime.getMessage());
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date().toString(),
				HttpStatus.BAD_REQUEST.name(),runtime.getMessage(), req.getDescription(false));
		return new ResponseEntity<>(exceptionResponse,HttpStatus.BAD_REQUEST);
	}

}
