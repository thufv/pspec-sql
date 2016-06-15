package edu.thu.ss.spec.lang.parser;

public class ParseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParseException(String msg) {
		super(msg);
	}

	public ParseException(Throwable cause) {
		super(cause);
	}

	public ParseException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
