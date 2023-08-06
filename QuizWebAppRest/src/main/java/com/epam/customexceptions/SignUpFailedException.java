package com.epam.customexceptions;

public class SignUpFailedException extends Exception {
	public SignUpFailedException(String msg) {
		super(msg);
	}
}