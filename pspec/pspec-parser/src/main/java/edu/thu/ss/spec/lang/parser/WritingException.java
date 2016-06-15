package edu.thu.ss.spec.lang.parser;

public class WritingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WritingException(String msg) {
		super(msg);
	}

	public WritingException(Throwable cause) {
		super(cause);
	}

	public WritingException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
