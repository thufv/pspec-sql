package edu.thu.ss.spec.lang.parser.event;

import java.net.URI;

import edu.thu.ss.spec.lang.parser.PSpec.PSpecEventType;

public abstract class ParseEvent {

	public URI uri;
	public PSpecEventType type;

	public Object[] data;

	public ParseEvent(PSpecEventType type, URI uri, Object... data) {
		this.type = type;
		this.uri = uri;
		this.data = data;
	}

}
