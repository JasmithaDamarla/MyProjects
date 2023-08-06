package com.epam.customexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class QuestionLibException extends Exception {
	public QuestionLibException(String msg) {
		super(msg);
	}

}