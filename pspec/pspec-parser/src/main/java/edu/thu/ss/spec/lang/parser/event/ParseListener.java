package edu.thu.ss.spec.lang.parser.event;

public interface ParseListener<T extends ParseEvent> {

	public void handleEvent(T e);

}
