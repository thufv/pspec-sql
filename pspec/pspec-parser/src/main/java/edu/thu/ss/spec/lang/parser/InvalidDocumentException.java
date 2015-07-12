package edu.thu.ss.spec.lang.parser;

import java.net.URI;

/**
 * unrecoverable exception
 * @author luochen
 *
 */
public class InvalidDocumentException extends ParseException {

	public URI file;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidDocumentException(URI file) {
		super("");
		this.file = file;
	}

	public InvalidDocumentException(URI file, Throwable cause) {
		super(cause);
		this.file = file;
	}

	public InvalidDocumentException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
