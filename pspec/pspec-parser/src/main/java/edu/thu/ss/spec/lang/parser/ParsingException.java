package edu.thu.ss.spec.lang.parser;

public class ParsingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParsingException(String msg) {
		super(msg);
	}

	public ParsingException(Throwable cause) {
		super(cause);
	}

	public ParsingException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
