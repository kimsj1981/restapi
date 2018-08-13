package com.sjkim.exception;

public class CardPointNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 6786801654128053176L;

	public CardPointNotFoundException() {
	}

	public CardPointNotFoundException(String message) {
		super(message);
	}

}
