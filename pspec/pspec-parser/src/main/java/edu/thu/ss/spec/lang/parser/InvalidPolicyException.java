package edu.thu.ss.spec.lang.parser;

import java.net.URI;

/**
 * unrecoverable exception
 * @author luochen
 *
 */
public class InvalidPolicyException extends ParseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public URI uri;

	public InvalidPolicyException(URI uri, Throwable cause) {
		super(cause);
		this.uri = uri;
	}
}
