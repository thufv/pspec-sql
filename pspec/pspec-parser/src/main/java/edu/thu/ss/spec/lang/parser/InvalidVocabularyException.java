package edu.thu.ss.spec.lang.parser;

import java.net.URI;

/**
 * unrecoverable exception
 * @author luochen
 *
 */
public class InvalidVocabularyException extends ParseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public URI uri;

	public InvalidVocabularyException(URI uri, Throwable cause) {
		super(cause);
		this.uri = uri;
	}
}
