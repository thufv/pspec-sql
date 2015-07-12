package edu.thu.ss.spec.lang.parser.event;

import java.net.URI;

import edu.thu.ss.spec.lang.pojo.Vocabulary;

public class VocabularyEvent extends ParseEvent {
	public Vocabulary vocabulary;
	
	
	public VocabularyEvent(int type, URI uri, Vocabulary vocabulary, Object... data) {
		super(type, uri, data);
		this.vocabulary = vocabulary;
	}
}
