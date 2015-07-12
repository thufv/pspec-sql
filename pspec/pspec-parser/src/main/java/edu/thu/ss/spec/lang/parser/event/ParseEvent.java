package edu.thu.ss.spec.lang.parser.event;

import java.net.URI;

public abstract class ParseEvent {

	public URI uri;
	public int type;

	public Object[] data;

	public ParseEvent(int type, URI uri, Object... data) {
		this.type = type;
		this.uri = uri;
		this.data = data;
	}

}
